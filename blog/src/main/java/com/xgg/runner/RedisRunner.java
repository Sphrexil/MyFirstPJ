//package com.xgg.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xgg.domain.entity.Article;
import com.xgg.mapper.ArticleMapper;
import com.xgg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//import java.util.List;
//
//@Component
//public class RedisRunner implements CommandLineRunner {
//
//    @Autowired
//    private RedisCache redisCache;
//    @Autowired
//    private ArticleMapper articleMapper;
//    @Override
//    public void run(String... args) throws Exception {
//        //从数据库中查询到浏览次数
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        List<Article> articleList = articleMapper.selectList(queryWrapper);
//        //将查到的数据存入到redis当中
////        articleList.stream().map(article -> {redisCache.setCacheObject("articleId:"+article.getId(),article.getViewCount());
////        return article;});
//        for (Article article : articleList) {
//            redisCache.setCacheObject("articleId:"+article.getId(),article.getViewCount());
//        }
//
////        System.out.println((String) redisCache.getCacheObject("articleId:"+1));
//    }
//}
