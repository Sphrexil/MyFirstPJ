package com.xgg.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  文章删除状态
     */
    public static final int ARTICLE_DEL_STATUS_NORMAL = 0;

    public static final int ARTICLE_DEL_STATUS_NOT_NORMAL = 1;

    public static final String  STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    public static final Long COMMENT_ROOT = -1l;
    public static final String ADMIN = "1";
}
