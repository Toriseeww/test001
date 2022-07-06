package com.example.test001.Impl;

import com.example.test001.model.Pha;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

public interface PhaImpl {
    //管理员
    int deleteByPrimaryKey(String onum);

    //用户
    int insert(Pha record) throws SQLException;

    //配送员
    Pha selectByPrimaryKey(String onum);

    //更新状态和配送员
    int updateAcSt(Pha record);

    //获取全部订单
    List<Pha> getPha();

    //获取指定订单
    List<Pha> getPcol(String pid);

    //修改订单信息
    int update(Pha record);

    //获取订单信息
    Pha getPid(String onum);

    //修改支付状态
    int updateMs(Pha record);
}
