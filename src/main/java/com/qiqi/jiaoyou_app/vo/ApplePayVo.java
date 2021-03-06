package com.qiqi.jiaoyou_app.vo;

import lombok.Data;

@Data
public class ApplePayVo {

    private String transactionId;
    private String payload;
    private Integer memberId;
    private Integer type;
    private String productId;
}
