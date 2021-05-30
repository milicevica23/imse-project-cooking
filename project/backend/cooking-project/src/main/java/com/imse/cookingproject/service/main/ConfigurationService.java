package com.imse.cookingproject.service.main;

import com.imse.cookingproject.CookingSiteProperties;
import com.imse.cookingproject.service.NoSQL.ConfigurationNoSQLService;
import com.imse.cookingproject.service.SQL.ConfigurationSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfigurationService {


    @Autowired
    private ConfigurationSQLService configurationSQLService;
    @Autowired
    private ConfigurationNoSQLService configurationNoSQLService;


    public void create() {
        configurationSQLService.createAllTables();
    }

    public void drop() {
        configurationSQLService.dropAllTables();
    }


    public void generateData() {
        configurationSQLService.generateData();
    }

    public void migrateData() {
        //configurationNoSQLService.createSchema();
        configurationNoSQLService.migrateData();
    }

    public HashMap<String, Object> getConfiguration() {
        return CookingSiteProperties.getConfiguration();
    }
}
