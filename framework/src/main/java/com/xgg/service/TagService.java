package com.xgg.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.dto.TagListDto;
import com.xgg.domain.entity.Link;
import com.xgg.domain.entity.Tag;

import java.util.List;

/**
 * 标签(com.xgg.domain.entity.Tag)表服务接口
 *
 * @author makejava
 * @since 2022-09-10 00:41:55
 */
public interface TagService extends IService<Tag> {


    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
}
