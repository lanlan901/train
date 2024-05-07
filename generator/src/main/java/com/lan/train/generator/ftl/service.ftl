package com.lan.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lan.train.common.context.LoginMemberContext;
import com.lan.train.common.resp.PageResp;
import com.lan.train.common.util.SnowUtil;
import com.lan.train.member.domain.${Domain};
import com.lan.train.member.domain.${Domain}Example;
import com.lan.train.member.mapper.${Domain}Mapper;
import com.lan.train.member.req.${Domain}QueryReq;
import com.lan.train.member.req.${Domain}SaveReq;
import com.lan.train.member.resp.${Domain}QueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ${Domain}Service {

    @Resource
    private ${Domain}Mapper ${Domain}Mapper;

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);

    public void save(${Domain}SaveReq req) {
        DateTime now = DateTime.now();
        ${Domain} ${Domain} = BeanUtil.copyProperties(req, ${Domain}.class);
        if (ObjectUtil.isNull(${Domain}.getId())) {
            LOG.info("insert ${Domain}");
            ${Domain}.setMemberId(LoginMemberContext.getId());
            ${Domain}.setId(SnowUtil.getSnowflakeNextId());
            ${Domain}.setCreateTime(now);
            ${Domain}.setUpdateTime(now);
            ${Domain}Mapper.insert(${Domain});
        } else {
            LOG.info("update ${Domain}");
            ${Domain}.setUpdateTime(now);
            ${Domain}Mapper.updateByPrimaryKey(${Domain});
        }
    }


    public PageResp<${Domain}QueryResp> queryList(${Domain}QueryReq req) {
        ${Domain}Example ${Domain}Example = new ${Domain}Example();
        ${Domain}Example.Criteria criteria = ${Domain}Example.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<${Domain}> ${Domain}ist = ${Domain}Mapper.selectByExample(${Domain}Example);
        PageInfo<${Domain}r> pageInfo = new PageInfo<>(${Domain}List);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<${Domain}QueryResp> list = BeanUtil.copyToList(${Domain}List, ${Domain}QueryResp.class);

        PageResp<${Domain}QueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        ${Domain}Mapper.deleteByPrimaryKey(id);
    }
}
