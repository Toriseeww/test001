package com.example.test001.Service;

import com.example.test001.Impl.MonImpl;
import com.example.test001.mapper.MonMapper;
import com.example.test001.model.Mon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonImplService implements MonImpl {

    @Autowired
    private MonMapper monMapper;


    @Override
    @Transactional
    public int insert(Mon record) {
        int i=monMapper.insert(record);
        return i;
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(Mon record) {
        int i=monMapper.updateByPrimaryKey(record);
        return i;
    }

    @Override
    @Transactional
    public Mon getMon(String uid) {
        return monMapper.getMon(uid);
    }
}
