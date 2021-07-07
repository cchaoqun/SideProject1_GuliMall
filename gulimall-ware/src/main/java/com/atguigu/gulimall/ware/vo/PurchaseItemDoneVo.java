package com.atguigu.gulimall.ware.vo;

import lombok.Data;

/**
 * @author Chaoqun Cheng
 * @date 2021-07-2021/7/7-18:35
 */

@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
