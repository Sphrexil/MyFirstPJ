package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.entity.Link;


/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 * @since 2022-04-05 15:15:00
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}


