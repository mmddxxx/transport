package com.cug.mytrain.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.cug.mytrain.domain.Passenger;
import com.cug.mytrain.mapper.PassengerMapper;
import com.cug.mytrain.req.PassengerSaveReq;
import com.cug.mytrain.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq passengerSaveReq) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(passengerSaveReq, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}
