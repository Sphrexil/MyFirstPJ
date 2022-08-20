package com.xgg.runner;

import com.xgg.domain.entity.Article;
import com.xgg.mapper.ArticleMapper;
import com.xgg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 主要是id和viewCount

        List<Article> articleList = articleMapper.selectList(null);

        Map<String, Integer> viewCountMap = articleList.stream().collect(
                Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存储到redis当中

        redisCache.setCacheMap("article:viewCount",viewCountMap);


    }
}
