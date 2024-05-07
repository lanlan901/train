package com.lan.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lan.train.common.exception.BusinessException;
import com.lan.train.common.exception.BusinessExceptionEnum;
import com.lan.train.common.util.JwtUtil;
import com.lan.train.common.util.SnowUtil;
import com.lan.train.member.MemberApplication;
import com.lan.train.member.domain.Member;
import com.lan.train.member.domain.MemberExample;
import com.lan.train.member.mapper.MemberMapper;
import com.lan.train.member.req.MemberLoginReq;
import com.lan.train.member.req.MemberSendCodeReq;
import com.lan.train.member.resp.MemberLoginResp;
import com.lan.train.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);
    //统计会员数量
    public int count(){
        return Math.toIntExact(memberMapper.countByExample(null));
    }
    //member register
    public long register (MemberRegisterReq req){
        //确保手机号干净
        String mobile = req.getMobile();
        mobile = mobile.replaceAll("\\s|-", "");
        //判断手机号长度是否符合要求
        if(mobile.length() != 11){
            throw new RuntimeException("该手机号不符合要求");
        }
        //判断手机号是否被注册
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isNotEmpty(list)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXISTS);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

    /**
     * 发送短信验证码
     * @param req
     */
    public void sendCode(MemberSendCodeReq req){
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);

        //如果手机号不存在，则插入一条记录
        if(CollUtil.isEmpty(list)){
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }else{
            LOG.info("手机号存在，不插入记录");
        }
        //生成验证码
        //String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码：{}",code);
        redisTemplate.opsForValue().set("smsCode:" + mobile, code, 5, TimeUnit.MINUTES);

        LOG.info("保存短信记录表");

        LOG.info("对接短信通道");
    }

    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public MemberLoginResp login(MemberLoginReq req){
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectByMobile(mobile);
        //若手机号不存在，则插入一条记录
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        String savedCode = redisTemplate.opsForValue().get("smsCode:" + mobile);
        if (savedCode == null || !savedCode.equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }
        redisTemplate.delete("smsCode:" + mobile);

        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }
}