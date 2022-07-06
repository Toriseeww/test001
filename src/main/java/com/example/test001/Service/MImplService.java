package com.example.test001.Service;

import com.example.test001.Impl.MImpl;
import com.example.test001.mapper.MMapper;
import com.example.test001.model.M;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class MImplService implements MImpl {
    @Autowired
    private MMapper mMapper;

    @Override
    @Transactional
    public int insert(M record) throws DataAccessException, SQLException {
        int i =mMapper.insert(record);
        return i;
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(M record) {
        int i =mMapper.updateByPrimaryKey(record);
        return i;
    }

    @Override
    @Transactional
    public List<M> getM() {
        return mMapper.getM();
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(String uid) {
        int i =mMapper.deleteByPrimaryKey(uid);
        return i;
    }

    @Override
    @Transactional
    public M selectByPrimaryKey(String uid) {
        return mMapper.selectByPrimaryKey(uid);
    }

    @Override
    @Transactional
    public M getMid(String uid) {
        return mMapper.getMid(uid);
    }
}
