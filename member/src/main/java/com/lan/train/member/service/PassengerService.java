package com.lan.train.member.service;

import com.lan.train.member.mapper.PassengerMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;
    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    public static void main(String[] args){

    }
//
//    public void save(PassengerSaveReq req) {
//        DateTime now = DateTime.now();
//        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
//        if (ObjectUtil.isNull(passenger.getId())) {
//            LOG.info("insert passenger");
//            passenger.setMemberId(LoginMemberContext.getId());
//            passenger.setId(SnowUtil.getSnowflakeNextId());
//            passenger.setCreateTime(now);
//            passenger.setUpdateTime(now);
//            passengerMapper.insert(passenger);
//        } else {
//            LOG.info("update passenger");
//            passenger.setUpdateTime(now);
//            passengerMapper.updateByPrimaryKey(passenger);
//        }
//    }
//
//
//    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
//        PassengerExample passengerExample = new PassengerExample();
//        PassengerExample.Criteria criteria = passengerExample.createCriteria();
//        if (ObjectUtil.isNotNull(req.getMemberId())) {
//            criteria.andMemberIdEqualTo(req.getMemberId());
//        }
//
//        LOG.info("查询页码：{}", req.getPage());
//        LOG.info("每页条数：{}", req.getSize());
//        PageHelper.startPage(req.getPage(), req.getSize());
//        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
//
//        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
//        LOG.info("总行数：{}", pageInfo.getTotal());
//        LOG.info("总页数：{}", pageInfo.getPages());
//
//        List<PassengerQueryResp> list = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
//
//        PageResp<PassengerQueryResp> pageResp = new PageResp<>();
//        pageResp.setTotal(pageInfo.getTotal());
//        pageResp.setList(list);
//        return pageResp;
//    }
//
//    public void delete(Long id) {
//        passengerMapper.deleteByPrimaryKey(id);
//    }

}
