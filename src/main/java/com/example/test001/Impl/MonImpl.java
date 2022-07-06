package com.example.test001.Impl;

import com.example.test001.model.Mon;

public interface MonImpl {

    int insert(Mon record);

    int updateByPrimaryKey(Mon record);

    Mon getMon(String uid);
}
