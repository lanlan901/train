package com.lan.train.member.req;

import com.lan.train.common.req.PageReq;
import lombok.Data;

@Data
public class PassengerQueryReq extends PageReq {

    private Long memberId;

}