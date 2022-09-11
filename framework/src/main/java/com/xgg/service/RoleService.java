package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2022-09-10 18:09:23
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
