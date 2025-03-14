package org.example.itheima.controller;

import org.example.itheima.pojo.Result;
import org.example.itheima.pojo.Tea;
import org.example.itheima.service.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeaController {
    @Autowired
    private TeaService teaService;
    @GetMapping("/teas")
    public Result<List<Tea>> getTeaList() {
        List<Tea> teas = teaService.getTeaList();
        return Result.success(teas);
    }

    @GetMapping("/tea")
    public Result<Tea> getTeaById(Integer id) {
        Tea tea = teaService.getTeaById(id);
        return Result.success(tea);
    }
}
