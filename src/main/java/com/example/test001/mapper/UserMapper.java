package com.example.test001.mapper;

import com.example.test001.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String sid);

    int insert(User record) throws SQLException;

    int insertSelective(User record);

    User selectByPrimaryKey(String sid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> UserAll();

    User getAdmin(String sid);
}