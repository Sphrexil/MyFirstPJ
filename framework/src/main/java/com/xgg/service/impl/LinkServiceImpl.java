package com.xgg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.constants.SystemConstants;
import com.xgg.domain.ResponseResult;

import com.xgg.domain.entity.Link;
import com.xgg.domain.vo.LinkVo;
import com.xgg.mapper.LinkMapper;
import com.xgg.service.LinkService;
import com.xgg.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(SgLink)表服务实现类
 *
 * @author makejava
 * @since 2022-04-05 15:15:01
 */
@Service("LinkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {

        //查询所有审核通过的的
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        //转换为Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }
}
