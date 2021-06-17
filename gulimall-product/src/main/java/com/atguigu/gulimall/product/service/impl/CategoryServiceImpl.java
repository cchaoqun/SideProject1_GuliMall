package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查出所有分类
        /*
        这里的baseMapper来自于当前CategoryServiceImpl继承的ServiceImpl中的泛型<CategoryDao>,
        所以不需要注入categoryDao, 直接使用在ServiceImpl中注入的baseMapper即可调用Dao(Mapper)层的selectList方法
         */
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2.组装成父子树形结构

        //2.1 找到所有的一级分类(父分类id=0)
        //filter过滤所有查询到的CategoryEntity中父分级为0的
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu)->{//对这些经过filter符合要求的查询子节点
            menu.setChildren(getChildrens(menu, entities));
            return menu;
        }).sorted((menu1, menu2)->{//排序, 根据两个菜单排序的结果从小到大
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect((Collectors.toList()));//收集

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1.检查当前删除的菜单是否被其他地方引用
        //批量删除
        //逻辑删除 用一列(show status)来表示是否删除, 而不是真的从物理磁盘上删除
        baseMapper.deleteBatchIds(asList);
    }

    //[2,25,225]
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findParantPath(catelogId, paths);
        Collections.reverse(paths);


        return paths.toArray(new Long[paths.size()]);
    }

    /**
     * 级联更新所有关联的数据, 保证冗余数据的一致性
     * @param category
     */
    @Transactional //事务
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    private void findParantPath(Long catelogId, List<Long> paths){
        //收集当前结点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0) {
            findParantPath(byId.getParentCid(), paths);
        }
    }

    /**
     * 递归查找所有菜单的子菜单
     * @param root 当前菜单
     * @param all 所有的菜单
     * @return 寻找root的所有子级菜单
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(categoryEntity->{
            //在所有的菜单中找到父级菜单id根root菜单id相同的就是root的子菜单
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity->{
            //对找到的每个子菜单继续递归找子菜单并设置
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2)->{
            //对菜单进行排序(需要先判断是否为空, 为空则给默认值0)
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());


        return children;
    }



}