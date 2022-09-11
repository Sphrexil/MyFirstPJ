package com.xgg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgg.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-06 16:52:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    List<String> getUserAllPermissions(@Param("userId") Long userId);

    List<String> getUserAllRoleKeys(@Param("userId") Long userId);
}


