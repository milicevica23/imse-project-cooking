package com.imse.cookingproject.controller.configuration;

import com.imse.cookingproject.service.main.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("configuration")
public class ConfigurationController {



    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("/createDB")
    public void createDB() {
        configurationService.create();
        configurationService.generateData();
    }

    @GetMapping("/dropTables")
    public void dropDB() {
         configurationService.drop("SQL");
    }

    @GetMapping("/getConfiguration")
    public HashMap<String, Object> getConfiguration() {
        return configurationService.getConfiguration();
    }

    @GetMapping("/generateData")
    public void generateData() {
        configurationService.generateData();
    }

    @GetMapping("/migrateData")
    public void migrateData() {
        configurationService.migrateData();
    }

    @GetMapping("dropData")
    public void addRating(@RequestParam String dbType){
        configurationService.drop(dbType);
    }

}
