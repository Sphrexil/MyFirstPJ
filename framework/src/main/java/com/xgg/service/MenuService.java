package com.xgg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgg.domain.entity.Menu;
import com.xgg.domain.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-09-10 18:01:50
 */
public interface MenuService extends IService<Menu> {

    List<String> selectParmByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long id);
}
