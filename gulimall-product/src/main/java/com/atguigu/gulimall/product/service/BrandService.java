package com.atguigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.BrandEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 品牌
 *
 * @author chengchaoqun
 * @email chengchaoqun@gmail.com
 * @date 2021-06-10 16:04:24
 */
public interface BrandService extends IService<BrandEntity> {



    PageUtils queryPage(Map<String, Object> params);
}

