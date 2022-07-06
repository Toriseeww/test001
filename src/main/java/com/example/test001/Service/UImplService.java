package com.example.test001.Service;

import com.example.test001.Impl.UImpl;
import com.example.test001.mapper.UserMapper;
import com.example.test001.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class UImplService implements UImpl {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public int insert(User record) throws DataAccessException, SQLException {
        int i=userMapper.insert(record);
        return i;
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(User record) {
        int i=userMapper.updateByPrimaryKey(record);
        return i;
    }

    @Override
    @Transactional
    public List<User> UserAll() {
        return userMapper.UserAll();
    }

    @Override
    @Transactional
    public User getAdmin(String sid) {
        return userMapper.getAdmin(sid);
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(String sid) {
        int i=userMapper.deleteByPrimaryKey(sid);
        return i;
    }

    @Override
    @Transactional
    public User selectByPrimaryKey(String sid) {
        return userMapper.selectByPrimaryKey(sid);
    }
}
