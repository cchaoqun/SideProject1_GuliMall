package com.atguigu.gulimall.member.feign;

/**
 * @author Chaoqun Cheng
 * @date 2021-06-2021/6/10-20:31
 */

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-coupon")//参数为要调用的服务名称(注册中心服务的名称)
public interface CouponFeignService {
    //服务有多个方法,要调用哪个方法, 就把方法的完整前面写在这里
    //访问url要写全
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
