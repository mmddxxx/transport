package com.cug.mytrain.business.mapper.cust;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface SkTokenMapperCust {

    //decreaseCount表示减少的值
    int decrease(Date date, String trainCode, int decreaseCount);
}
