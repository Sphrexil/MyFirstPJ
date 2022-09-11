package com.xgg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgg.domain.entity.Menu;
import com.xgg.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-10 18:01:54
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long id);

}
