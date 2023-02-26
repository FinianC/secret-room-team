package com.secret.model.params;

import com.secret.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bouncycastle.cms.PasswordRecipientId;

@ApiModel("查询票参数")
@Data
public class TicketQueryParam extends BasePageParam {

    @ApiModelProperty("主题id")
    private Integer themeId;

    @ApiModelProperty("名称")
    private String name;


}
