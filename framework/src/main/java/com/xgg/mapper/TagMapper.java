package com.xgg.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgg.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(com.xgg.domain.entity.Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-10 00:42:03
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
