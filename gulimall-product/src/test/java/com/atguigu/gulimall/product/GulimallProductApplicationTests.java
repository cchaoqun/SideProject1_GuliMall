package com.atguigu.gulimall.product;


import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GulimallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testFindPath(){
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("完整路径 {}", Arrays.asList(catelogPath));
    }


    @Test
    public void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();
        //添加
//        brandEntity.setName("华为");
//        brandService.save(brandEntity);

        //根据id修改
//        brandEntity.setBrandId(2L); //这里因为第一添加配置文件没有设置编码格式, 设置后删除了第一次的数据重新插入所以是2(Long)
//        brandEntity.setDescript("华为");
//        brandService.updateById(brandEntity);


       //查询
        /*
        QueryWrapper 查询条件
            泛型是想要查询出来的类
        .eq 表示哪一列等于什么值的结果
         */
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 2L));
        //输出集合中的元素
        list.forEach((item)->{
            System.out.println(item);
        });


//        System.out.println("保存成功...");

    }

}
