package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author chengchaoqun
 * @email chengchaoqun@gmail.com
 * @date 2021-06-10 16:04:24
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    //@Param("别名") 为每个传入的参数起别名 这样在mapper.xml直接使用即可
    void updateCategory(@Param("catId")Long catId, @Param("name")String name);
}
