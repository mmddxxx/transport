package com.cug.mytrain.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.cug.mytrain.context.LoginMemberContext;
import com.cug.mytrain.domain.Passenger;
import com.cug.mytrain.domain.PassengerExample;
import com.cug.mytrain.mapper.PassengerMapper;
import com.cug.mytrain.req.PassengerQueryReq;
import com.cug.mytrain.req.PassengerSaveReq;
import com.cug.mytrain.resp.PassengerQueryResp;
import com.cug.mytrain.util.SnowUtil;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq passengerSaveReq) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(passengerSaveReq, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }

    //这里不需要输入参数，但是为了方法通用，后面控台端也可以调用该方法获取所有会员
    public List<PassengerQueryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        //criteria只能create一次
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        //只会对该句下面的第一条select语句起效
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
        return BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
    }
}
