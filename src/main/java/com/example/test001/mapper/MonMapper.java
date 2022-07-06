package com.example.test001.mapper;

import com.example.test001.model.Mon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MonMapper {
    int deleteByPrimaryKey(String uid);

    int insert(Mon record);

    Mon selectByPrimaryKey(String uid);

    int updateByPrimaryKey(Mon record);

    Mon getMon(String uid);
}