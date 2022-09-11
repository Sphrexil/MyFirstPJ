package com.xgg.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgg.domain.ResponseResult;
import com.xgg.domain.dto.TagListDto;
import com.xgg.domain.entity.Tag;
import com.xgg.domain.vo.PageVo;
import com.xgg.mapper.TagMapper;
import com.xgg.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 标签(com.xgg.domain.entity.Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-09-10 00:41:59
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .eq(StringUtils.hasText(tagListDto.getName()),
                        Tag::getName, tagListDto.getName())
                .eq(StringUtils.hasText(tagListDto.getRemark()),
                        Tag::getRemark, tagListDto.getRemark());


        Page<Tag> page = page(new Page<Tag>(pageNum, pageSize), wrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
