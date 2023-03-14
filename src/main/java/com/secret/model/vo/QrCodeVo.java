package com.secret.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: card-voucher_new
 * @description:
 * @author: 陈迪
 * @create: 2022/11/07 10:13
 */
@Data
@ApiModel("QrCodeVo")
public class QrCodeVo implements Serializable {
    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("序列")
    private String sequence;

    /**
     * 获取key
     * @return
     */
    public String  getKey(){
        return  orderId+sequence;
    }

}
