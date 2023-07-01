package com.cug.mytrain.business.mapper;

import com.cug.mytrain.business.domain.DailyTrain;
import com.cug.mytrain.business.domain.DailyTrainExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DailyTrainMapper {
    long countByExample(DailyTrainExample example);

    int deleteByExample(DailyTrainExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DailyTrain record);

    int insertSelective(DailyTrain record);

    List<DailyTrain> selectByExample(DailyTrainExample example);

    DailyTrain selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DailyTrain record, @Param("example") DailyTrainExample example);

    int updateByExample(@Param("record") DailyTrain record, @Param("example") DailyTrainExample example);

    int updateByPrimaryKeySelective(DailyTrain record);

    int updateByPrimaryKey(DailyTrain record);
}