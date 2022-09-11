package com.xgg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.constants.SystemConstants;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.dto.ArticleListDto;
import com.xgg.domain.entity.Article;
import com.xgg.domain.entity.Category;
import com.xgg.domain.entity.User;
import com.xgg.domain.vo.*;
import com.xgg.enums.AppHttpCodeEnum;
import com.xgg.handler.exception.SystemException;
import com.xgg.mapper.ArticleMapper;
import com.xgg.mapper.CategoryMapper;
import com.xgg.mapper.UserMapper;
import com.xgg.service.ArticleService;
import com.xgg.utils.BeanCopyUtils;
import com.xgg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).eq(Article::getDelFlag, SystemConstants.ARTICLE_DEL_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).eq(Article::getDelFlag, SystemConstants.ARTICLE_DEL_STATUS_NORMAL);
        ;
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream().map(article -> article.setCategoryName(
                categoryMapper.selectById(article.getCategoryId()).getName())).collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }


        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        for (ArticleListVo articleListVo : articleListVos) {
            Long userId = articleListVo.getCreateBy();
            User user = userMapper.selectById(userId);
            articleListVo.setAvatar(user.getAvatar());
            articleListVo.setNickName(user.getNickName());
        }

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {


        //根据id查询文章
        Article article = getById(id);
        if (!article.getDelFlag().equals(SystemConstants.ARTICLE_DEL_STATUS_NORMAL)) {
            throw new SystemException(AppHttpCodeEnum.Article_NOT_EXIST);
        }
        //从redis中获取
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        Article article = getById(id);
        if (!article.getDelFlag().equals(SystemConstants.ARTICLE_DEL_STATUS_NORMAL)) {
            throw new SystemException(AppHttpCodeEnum.Article_NOT_EXIST);
        }
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        //封装返回
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult commitArticle(ArticleVo article) {

        if (Objects.isNull(article)) {
            throw new RuntimeException();
        }
        Long categoryId = article.getCategoryId();
        Category category = categoryMapper.selectById(categoryId);
        Article relArticle = BeanCopyUtils.copyBean(article, Article.class);
        relArticle.setCategoryName(category.getName());
        relArticle.setDelFlag(1);
        //DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        relArticle.setCreateTime(new Date());
        this.baseMapper.insert(relArticle);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult queryMyArticles(Long useId) {

        //查redis

        // 判空
        if (Objects.isNull(useId)) throw new RuntimeException("请传入用户id");
        User user = userMapper.selectById(useId);
        if (Objects.isNull(user)) throw new SystemException(AppHttpCodeEnum.USER_NOT_EXIST);

        // 查询
        List<Article> articleList = this.baseMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getCreateBy, useId)
                // 查询出未被删除的
                .eq(Article::getDelFlag, SystemConstants.ARTICLE_DEL_STATUS_NORMAL));

        // 封装
        List<Article> list = articleList.stream().map(article -> {
            Long categoryId = article.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);
            String name = category.getName();
            article.setCategoryName(name);
            return article;
        }).collect(Collectors.toList());

        List<ArticleUserVo> articleUserVos = BeanCopyUtils.copyBeanList(list, ArticleUserVo.class);


        return ResponseResult.okResult(articleUserVos);
    }

    @Override
    @Transactional
    public ResponseResult deleteMyArticle(Long articleId) {

        if (Objects.isNull(articleId)) throw new RuntimeException("请传入文章");
        Article article = this.baseMapper.selectById(articleId);
        if (Objects.isNull(article)) throw new SystemException(AppHttpCodeEnum.Article_NOT_EXIST);

        article.setDelFlag(0);
        this.updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<Article>().eq(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle()).eq(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());
        Page<Article> page = page(new Page<Article>(pageNum, pageSize), wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }
}
