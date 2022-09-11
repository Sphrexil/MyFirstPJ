package com.xgg.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * description: AdminUserInfoVo
 * date: 2022/9/10 18:43
 * author: zhenyu
 * version: 1.0
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {
    private List<String> permissions;
    private List<String> roles;
    private UserInfoVo user;
}
