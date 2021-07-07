package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Chaoqun Cheng
 * @date 2021-07-2021/7/7-17:33
 */
@Data
public class MergeVo {

    //整单Id
    private Long purchaseId;
    //合并项集合
    private List<Long> items;
}
