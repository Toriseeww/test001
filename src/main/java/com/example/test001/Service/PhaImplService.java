package com.example.test001.Service;

import com.example.test001.Impl.PhaImpl;
import com.example.test001.mapper.PhaMapper;
import com.example.test001.model.Pha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class PhaImplService implements PhaImpl {
    @Autowired
    private PhaMapper phaMapper;

    @Override
    @Transactional
    public List<Pha> getPha() {
        return phaMapper.getPha();
    }

    @Override
    @Transactional
    public List<Pha> getPcol(String pid) {
        return phaMapper.getPcol(pid);
    }

    @Override
    @Transactional
    public int update(Pha record) {
        int i=phaMapper.update(record);
        return i;
    }

    @Override
    @Transactional
    public Pha getPid(String onum) {
        return phaMapper.getPid(onum);
    }

    @Override
    @Transactional
    public int updateMs(Pha record) {
        int i=phaMapper.updateMs(record);
        return i;
    }

    @Override
    @Transactional
    public int insert(Pha record) throws DataAccessException, SQLException {
        int i=phaMapper.insert(record);
        return i;
    }

    @Override
    @Transactional
    public Pha selectByPrimaryKey(String onum) {
        return phaMapper.selectByPrimaryKey(onum);
    }

    @Override
    @Transactional
    public int updateAcSt(Pha record) {
        int i=phaMapper.updateAcSt(record);
        return i;
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(String onum) {
        int i=phaMapper.deleteByPrimaryKey(onum);
        return i;
    }
}
