package com.example.test001.Impl;

import com.example.test001.model.User;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

public interface UImpl {
    //用户自己注册，更新信息
    int insert(User record) throws DataAccessException, SQLException;
    int updateByPrimaryKey(User record);

    //管理员
    List<User> UserAll();
    User getAdmin(String sid);
    int deleteByPrimaryKey(String sid);
    User selectByPrimaryKey(String sid);
}
