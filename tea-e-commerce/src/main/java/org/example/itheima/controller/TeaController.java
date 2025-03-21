package org.example.itheima.controller;

import org.example.itheima.pojo.PageBean;
import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.Tea;
import org.example.itheima.service.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeaController {
    @Autowired
    private TeaService teaService;
    @GetMapping("/teas")
    public Result<PageBean<Tea>> search(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "6") Integer pageSize,
                                      @RequestParam(required = false) String name) {
        PageBean<Tea> teas = teaService.search(page, pageSize, name);
        return Result.success(teas);
    }
    @GetMapping("/teas/list")
    public Result<List<Tea>> getTeaList() {
        List<Tea> teas = teaService.getTeaList();
        return Result.success(teas);
    }

    @GetMapping("/teas/{id}")
    public Result<Tea> getTeaById(@PathVariable("id")Integer id) {
        Tea tea = teaService.getTeaById(id);
        return Result.success(tea);
    }
    //下面是管理员的功能
    @GetMapping("/admin/teaList")
    public Result<PageBean<Tea>> teaList(Integer pageNum, Integer pageSize, @RequestParam(required = false) String categoryName) {
        PageBean<Tea> teas = teaService.teaList(pageNum, pageSize, categoryName);
        return Result.success(teas);
    }
    @DeleteMapping("/admin/tea/{id}")
    public Result deleteTea(@PathVariable("id") Integer id) {
        teaService.deleteTea(id);
        return Result.success();
    }
    @PostMapping("/admin/tea")
    public Result addTea(@RequestBody Tea tea) {
        System.out.println(tea);
        teaService.addTea(tea);
        return Result.success("上架成功");
    }
    @PutMapping("/admin/tea/{id}")
    public Result updateTea(@PathVariable("id") Integer id, @RequestBody Tea tea) {
        tea.setId(id);
        teaService.updateTea(tea);
        return Result.success("修改成功");
    }
}
