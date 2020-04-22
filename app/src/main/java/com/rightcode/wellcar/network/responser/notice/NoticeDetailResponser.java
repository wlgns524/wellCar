package com.rightcode.wellcar.network.responser.notice;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.notice.Notice;
import com.rightcode.wellcar.network.model.response.notice.NoticeDetail;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeDetailResponser extends CommonResult {

    @JsonField
    NoticeDetail notice;
}
