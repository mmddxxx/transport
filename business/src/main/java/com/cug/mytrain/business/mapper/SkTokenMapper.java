package com.cug.mytrain.business.mapper;

import com.cug.mytrain.business.domain.SkToken;
import com.cug.mytrain.business.domain.SkTokenExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkTokenMapper {
    long countByExample(SkTokenExample example);

    int deleteByExample(SkTokenExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SkToken record);

    int insertSelective(SkToken record);

    List<SkToken> selectByExample(SkTokenExample example);

    SkToken selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SkToken record, @Param("example") SkTokenExample example);

    int updateByExample(@Param("record") SkToken record, @Param("example") SkTokenExample example);

    int updateByPrimaryKeySelective(SkToken record);

    int updateByPrimaryKey(SkToken record);
}