package org.example.itheima.service.Impl;

import org.example.itheima.mapper.TeaMapper;
import org.example.itheima.pojo.Tea;
import org.example.itheima.service.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaServiceImpl implements TeaService {
    @Autowired
    private TeaMapper teaMapper;
    @Override
    public List<Tea> getTeaList() {
        return teaMapper.getTeaList();
    }
}
