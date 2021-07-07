package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Chaoqun Cheng
 * @date 2021-06-2021/6/20-21:36
 */

@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
