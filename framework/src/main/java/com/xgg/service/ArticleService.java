package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.dto.ArticleListDto;
import com.xgg.domain.entity.Article;
import com.xgg.domain.vo.ArticleVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult commitArticle(ArticleVo article);

    ResponseResult queryMyArticles(Long useId);

    ResponseResult deleteMyArticle(Long articleId);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);
}
