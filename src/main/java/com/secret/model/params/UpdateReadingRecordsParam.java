package com.secret.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("更新读取聊天记录参数")
@Data
public class UpdateReadingRecordsParam {

    @ApiModelProperty("群聊id")
    @NotNull
    private Integer groupChatId;
}
