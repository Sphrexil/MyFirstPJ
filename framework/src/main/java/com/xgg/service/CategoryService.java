package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-04-04 14:26:32
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}


