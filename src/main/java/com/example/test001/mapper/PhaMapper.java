package com.example.test001.mapper;

import com.example.test001.model.Pha;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PhaMapper {
    int deleteByPrimaryKey(String onum);

    int insert(Pha record);

    Pha selectByPrimaryKey(String onum);

    //更新状态和配送员
    int updateAcSt(Pha record);

    //修改订单信息
    int update(Pha record);

    //获取全部订单
    List<Pha> getPha();

    //获取指定订单
    List<Pha> getPcol(String pid);

    //获取订单信息
    Pha getPid(String onum);

    //修改支付状态
    int updateMs(Pha record);
}