package com.xgg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgg.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * 友链(SgLink)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-05 15:15:01
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}


