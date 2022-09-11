package com.xgg.domain.vo;

import com.xgg.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * description: RouterVo
 * date: 2022/9/10 20:58
 * author: zhenyu
 * version: 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
