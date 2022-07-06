package com.example.test001.mapper;

import com.example.test001.model.M;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface MMapper {
    int deleteByPrimaryKey(String uid);

    int insert(M record) throws SQLException;

    int insertSelective(M record);

    M selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(M record);

    int updateByPrimaryKey(M record);

    List<M> getM();

    @Select("select * from m where uid = #{uid}")
    M getMid(String uid);
}