package com.xgg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.constants.SystemConstants;
import com.xgg.domain.entity.Menu;
import com.xgg.domain.vo.MenuVo;
import com.xgg.mapper.MenuMapper;
import com.xgg.mapper.UserMapper;
import com.xgg.service.MenuService;
import com.xgg.utils.BeanCopyUtils;
import com.xgg.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-09-10 18:01:52
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectParmByUserId(Long id) {

        if (id == 1L) {
            LambdaQueryWrapper<Menu> eq = new LambdaQueryWrapper<Menu>()
                    .in(Menu::getMenuType, "C", "F")
                    .eq(Menu::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
            List<Menu> menus = list(eq);
            List<String> permissions = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return permissions;
        }
        return userMapper.getUserAllPermissions(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long id) {
        if (SecurityUtils.isAdmin()) {
//            List<Menu> menus = menuMapper.selectAllRouterMenu();
//            List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
            List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list(), MenuVo.class);
            return setChildren(menuVos);
        }

        List<Menu> menus = menuMapper.selectRouterMenuTreeByUserId(id);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return builderMenuTree(menuVos, 0L);
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menus, long rootId) {
        List<MenuVo> menuVos = menus.stream()
                .filter(item -> item.getParentId().equals(rootId))
                .map(item1 -> item1.setChildren(getChildren(item1, menus)))
                .collect(Collectors.toList());
        return menuVos;
    }

    private List<MenuVo> getChildren(MenuVo item1, List<MenuVo> menus) {
        List<MenuVo> collect = menus.stream()
                .filter(item -> item.getParentId()
                        .equals(item1.getId()))
                .map(item -> item.setChildren(getChildren(item, menus)))
                .collect(Collectors.toList());
        return collect;
    }

    public List<MenuVo> setChildren(List<MenuVo> all) {

        List<MenuVo> collect1 = all.stream().map(item -> {
                    List<MenuVo> collect = all.stream()
                            .filter(item1 ->
                                    item.getId().equals(item1.getParentId())
                                            && ("C".equals(item1.getMenuType()) || "M".equals(item1.getMenuType())))
                            .collect(Collectors.toList());
                    item.setChildren(collect);
                    return item;
                }).filter(item -> item.getParentId().equals(0L))
                .sorted(Comparator.comparingInt(menu -> (menu.getOrderNum() == null ? 0 : menu.getOrderNum())))
                .collect(Collectors.toList());


//        List<MenuVo> children = all.stream().filter(menuVo -> {
//                    return menuVo.getParentId().equals(root.getId());
//                }).map(categoryEntity -> {
//                    //1、找到子菜单(递归)
//                    categoryEntity.setChildren(setChildren(categoryEntity, all));
//                    return categoryEntity;
//                }).sorted(Comparator.comparingInt(menu -> (menu.getOrderNum() == null ? 0 : menu.getOrderNum())))
//                .collect(Collectors.toList());

        return collect1;
    }
}
