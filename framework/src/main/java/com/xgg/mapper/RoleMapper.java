package com.xgg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgg.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-10 18:09:23
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
