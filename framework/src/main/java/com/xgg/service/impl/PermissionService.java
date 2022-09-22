package com.xgg.service.impl;

import com.xgg.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description: PermissionService
 * date: 2022/9/22 17:23
 * author: zhenyu
 * version: 1.0
 */
@Service("PermissionService")
public class PermissionService {

   public boolean hasPermissions(String perm) {
       if (SecurityUtils.isAdmin()) {
           return true;
       }
       List<String> perms = SecurityUtils.getLoginUser().getPerms();
       return perms.contains(perms);
   }
}
