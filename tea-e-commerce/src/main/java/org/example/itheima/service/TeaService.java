package org.example.itheima.service;

import org.example.itheima.pojo.PageBean;
import org.example.itheima.pojo.Tea;

import java.util.List;

public interface TeaService {
    List<Tea> getTeaList();

    Tea getTeaById(Integer id);

    PageBean<Tea> teaList(Integer pageNum, Integer pageSize, String categoryName);

    void deleteTea(Integer id);

    void addTea(Tea tea);

    void updateTea(Tea tea);

    List<Tea> search(String name);
}
