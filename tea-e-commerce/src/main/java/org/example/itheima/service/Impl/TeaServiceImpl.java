package org.example.itheima.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.itheima.mapper.TeaMapper;
import org.example.itheima.pojo.PageBean;
import org.example.itheima.pojo.Tea;
import org.example.itheima.service.TeaService;
import org.example.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeaServiceImpl implements TeaService {
    @Autowired
    private TeaMapper teaMapper;
    @Override
    public List<Tea> getTeaList() {
        return teaMapper.getTeaList();
    }

    @Override
    public Tea getTeaById(Integer id) {
        return teaMapper.getTeaById(id);
    }

    @Override
    public PageBean<Tea> teaList(Integer pageNum, Integer pageSize, String categoryName) {
        PageBean<Tea> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Tea> teas = teaMapper.teaList(categoryName);
        Page<Tea> p = (Page<Tea>) teas;
        pageBean.setTotal(p.getTotal());
        pageBean.setRows(p.getResult());
        return pageBean;
    }

    @Override
    public void deleteTea(Integer id) {
        teaMapper.deleteTea(id);
    }

    @Override
    public void addTea(Tea tea) {
        teaMapper.addTea(tea);
    }

    @Override
    public void updateTea(Tea tea) {
        teaMapper.updateTea(tea);
    }

    @Override
    public PageBean<Tea> search(Integer page, Integer pageSize, String name) {
        PageBean<Tea> pageBean = new PageBean<>();
        PageHelper.startPage(page,pageSize);
        List<Tea> teas = teaMapper.search(name);
        Page<Tea> p = (Page<Tea>) teas;
        pageBean.setTotal(p.getTotal());
        pageBean.setRows(p.getResult());
        return pageBean;
    }
}
