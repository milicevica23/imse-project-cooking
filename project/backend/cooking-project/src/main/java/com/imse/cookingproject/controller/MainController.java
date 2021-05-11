package com.imse.cookingproject.controller;

import com.imse.cookingproject.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("main")
public class MainController {
    @Autowired
    private MainService mainService;

    @PostMapping("/createSQLTables")
    public void createAllTables() {
        mainService.dropAllTables();
        mainService.createAllTables();
    }

    @PostMapping("/dropSQLTables")
    public void dropAllTables() {
        mainService.dropAllTables();
    }

    /**/@PostMapping("/generateData")
    public void generateData() {
        mainService.generateData();
    }

}
