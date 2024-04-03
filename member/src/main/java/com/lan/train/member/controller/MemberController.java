package com.lan.train.member.controller;

import com.lan.train.common.resp.CommonResp;
import com.lan.train.member.req.MemberLoginReq;
import com.lan.train.member.req.MemberSendCodeReq;
import com.lan.train.member.resp.MemberLoginResp;
import com.lan.train.member.service.MemberService;
import com.lan.train.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService memberService;

    /**
     * 会员数量统计
     * @return
     */
    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        CommonResp<Integer> commonResp = new CommonResp<>();
        commonResp.setContent(count);
        return commonResp;
    }

    /**
     * 会员注册
     * @param req
     * @return
     */
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req){
        long register = memberService.register(req);
        CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setContent(register);
        return commonResp;
    }
    /**
     * 发送验证码
     */
    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq req){
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    /**
     * 登陆
     * @param req
     * @return
     */
    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req){
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }

}
