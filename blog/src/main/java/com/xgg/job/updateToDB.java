package com.xgg.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.xgg.domain.entity.Article;
import com.xgg.mapper.ArticleMapper;
import com.xgg.service.ArticleService;
import com.xgg.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class updateToDB {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    //设置好分隔两分钟更新到数据库一次
    @Scheduled(cron = "0/30 * * * * ? " )
    public void update(){
        Map<String,Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles=viewCountMap.entrySet().stream()
                .map(entry->new Article(Long.valueOf(entry.getKey()),entry.getValue().longValue()))
                .collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }
}
