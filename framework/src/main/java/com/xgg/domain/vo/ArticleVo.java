package com.xgg.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private String content;
    private Long categoryId;
    private String title;
    private String summary;
    private Long viewCount;
    private Boolean isComment;
    private Long createBy;
}
