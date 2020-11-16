package com.model.mapper;

import com.model.pojo.Deviclist;
import com.model.pojo.DeviclistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DeviclistMapper {
    int countByExample(DeviclistExample example);

    int deleteByExample(DeviclistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Deviclist record);

    int insertSelective(Deviclist record);

    List<Deviclist> selectByExample(DeviclistExample example);

    Deviclist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Deviclist record, @Param("example") DeviclistExample example);

    int updateByExample(@Param("record") Deviclist record, @Param("example") DeviclistExample example);

    int updateByPrimaryKeySelective(Deviclist record);

    int updateByPrimaryKey(Deviclist record);

    int decdeleteTable();
}