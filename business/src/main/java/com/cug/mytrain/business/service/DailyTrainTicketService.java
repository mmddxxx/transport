package com.cug.mytrain.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cug.mytrain.business.domain.DailyTrain;
import com.cug.mytrain.business.domain.DailyTrainTicket;
import com.cug.mytrain.business.domain.DailyTrainTicketExample;
import com.cug.mytrain.business.domain.TrainStation;
import com.cug.mytrain.business.enums.SeatTypeEnum;
import com.cug.mytrain.business.enums.TrainTypeEnum;
import com.cug.mytrain.business.mapper.DailyTrainTicketMapper;
import com.cug.mytrain.business.req.DailyTrainTicketQueryReq;
import com.cug.mytrain.business.req.DailyTrainTicketSaveReq;
import com.cug.mytrain.business.resp.DailyTrainTicketQueryResp;
import com.cug.mytrain.resp.PageResp;
import com.cug.mytrain.util.SnowUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class DailyTrainTicketService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainTicketService.class);

    @Resource
    private DailyTrainTicketMapper dailyTrainTicketMapper;

    @Resource
    private TrainStationService trainStationService;

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    public void save(DailyTrainTicketSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainTicket dailyTrainTicket = BeanUtil.copyProperties(req, DailyTrainTicket.class);
        if (ObjectUtil.isNull(dailyTrainTicket.getId())) {
            dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainTicket.setCreateTime(now);
            dailyTrainTicket.setUpdateTime(now);
            dailyTrainTicketMapper.insert(dailyTrainTicket);
        } else {
            dailyTrainTicket.setUpdateTime(now);
            dailyTrainTicketMapper.updateByPrimaryKey(dailyTrainTicket);
        }
    }

    public PageResp<DailyTrainTicketQueryResp> queryList(DailyTrainTicketQueryReq req) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.setOrderByClause("`date` desc, start_time asc, train_code asc, `start_index` asc, `end_index` asc");
        DailyTrainTicketExample.Criteria criteria = dailyTrainTicketExample.createCriteria();
        if (ObjUtil.isNotNull(req.getDate())) {
            criteria.andDateEqualTo(req.getDate());
        }
        if (ObjUtil.isNotEmpty(req.getTrainCode())) {
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }
        if (ObjUtil.isNotEmpty(req.getStart())) {
            criteria.andStartEqualTo(req.getStart());
        }
        if (ObjUtil.isNotEmpty(req.getEnd())) {
            criteria.andEndEqualTo(req.getEnd());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainTicket> dailyTrainTicketList = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);

        PageInfo<DailyTrainTicket> pageInfo = new PageInfo<>(dailyTrainTicketList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainTicketQueryResp> list = BeanUtil.copyToList(dailyTrainTicketList, DailyTrainTicketQueryResp.class);

        PageResp<DailyTrainTicketQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainTicketMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void genDaily(DailyTrain dailyTrain, Date date, String trainCode) {
        LOG.info("生成日期【{}】车次【{}】的余票信息开始", DateUtil.formatDate(date), trainCode);

        // 删除某日某车次的座位信息
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);
        dailyTrainTicketMapper.deleteByExample(dailyTrainTicketExample);

        //查询途径车站信息
        // 查出某车次的所有的车站信息
        List<TrainStation> stationList = trainStationService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(stationList)) {
            LOG.info("该车次没有车站基础数据，生成该车次的余票信息结束");
            return;
        }

        DateTime now = DateTime.now();
        //foreach不能保证多线程有序
        for (int i = 0; i < stationList.size(); i++) {
            TrainStation trainStationStart = stationList.get(i);
            BigDecimal sumKM = BigDecimal.ZERO;
            for (int j = i + 1; j < stationList.size(); j++) {
                TrainStation trainStationEnd = stationList.get(j);

                //计算里程和
                sumKM = sumKM.add(trainStationEnd.getKm()) ;

                DailyTrainTicket dailyTrainTicket = new DailyTrainTicket();
                dailyTrainTicket.setId(SnowUtil.getSnowflakeNextId());
                dailyTrainTicket.setCreateTime(now);
                dailyTrainTicket.setUpdateTime(now);
                dailyTrainTicket.setDate(date);
                dailyTrainTicket.setTrainCode(trainCode);
                dailyTrainTicket.setStart(trainStationStart.getName());
                dailyTrainTicket.setStartPinyin(trainStationStart.getNamePinyin());
                dailyTrainTicket.setStartTime(trainStationStart.getOutTime());
                dailyTrainTicket.setStartIndex(trainStationStart.getIndex());
                dailyTrainTicket.setEnd(trainStationEnd.getName());
                dailyTrainTicket.setEndPinyin(trainStationEnd.getNamePinyin());
                dailyTrainTicket.setEndTime(trainStationEnd.getInTime());
                dailyTrainTicket.setEndIndex(trainStationEnd.getIndex());

                String trainType = dailyTrain.getType();
                // 计算票价系数：TrainTypeEnum.priceRate
                BigDecimal priceRate = EnumUtil.getFieldBy(TrainTypeEnum::getPriceRate, TrainTypeEnum::getCode, trainType);
                //票价为里程之和*座位单价*车次类型系数
                dailyTrainTicket.setYdz(dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.YDZ.getCode()));
                dailyTrainTicket.setYdzPrice(sumKM.multiply(SeatTypeEnum.YDZ.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP));  //四舍五入
                dailyTrainTicket.setEdz(dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.EDZ.getCode()));
                dailyTrainTicket.setEdzPrice(sumKM.multiply(SeatTypeEnum.EDZ.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP));
                dailyTrainTicket.setRw(dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.RW.getCode()));
                dailyTrainTicket.setRwPrice(sumKM.multiply(SeatTypeEnum.RW.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP));
                dailyTrainTicket.setYw(dailyTrainSeatService.countSeat(date, trainCode, SeatTypeEnum.YW.getCode()));
                dailyTrainTicket.setYwPrice(sumKM.multiply(SeatTypeEnum.YW.getPrice()).multiply(priceRate).setScale(2, RoundingMode.HALF_UP));

                dailyTrainTicketMapper.insert(dailyTrainTicket);


            }
        }
        LOG.info("生成日期【{}】车次【{}】的余票信息结束", DateUtil.formatDate(date), trainCode);
    }

    public DailyTrainTicket selectByUnique(Date date, String trainCode, String start, String end) {
        DailyTrainTicketExample dailyTrainTicketExample = new DailyTrainTicketExample();
        dailyTrainTicketExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andStartEqualTo(start)
                .andEndEqualTo(end);
        List<DailyTrainTicket> list = dailyTrainTicketMapper.selectByExample(dailyTrainTicketExample);
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
