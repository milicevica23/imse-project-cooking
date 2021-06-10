package com.imse.cookingproject.controller.configuration;

import com.imse.cookingproject.service.main.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("configuration")
public class ConfigurationController {
    private boolean createDone = false;


    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("/createDB")
    public void createDB() {
        if(!createDone){
            configurationService.create();
            configurationService.generateData();
            createDone = true;
        }
    }

    @GetMapping("/dropTables")
    public void dropDB() {
         configurationService.drop();
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

}
