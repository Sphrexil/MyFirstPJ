package com.xgg.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.domain.entity.Role;
import com.xgg.mapper.RoleMapper;
import com.xgg.mapper.UserMapper;
import com.xgg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2022-09-10 18:09:23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        if (id == 1) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("admin");
            return strings;
        }
        return userMapper.getUserAllRoleKeys(id);
    }
}
