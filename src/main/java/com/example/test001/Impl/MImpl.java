package com.example.test001.Impl;

import com.example.test001.model.M;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

public interface MImpl {
    int insert(M record) throws DataAccessException, SQLException;
    int updateByPrimaryKey(M record);

    List<M> getM();
    int deleteByPrimaryKey(String uid);
    M selectByPrimaryKey(String uid);

    M getMid(String uid);
}
