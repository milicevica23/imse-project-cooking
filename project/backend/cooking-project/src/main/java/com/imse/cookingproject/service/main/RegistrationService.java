package com.imse.cookingproject.service.main;

import com.imse.cookingproject.service.NoSQL.RegistrationNoSQLService;
import com.imse.cookingproject.service.SQL.RegistrationSQLService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class RegistrationService {

    @Autowired
    private RegistrationSQLService registrationSQLService;

    @Autowired
    private RegistrationNoSQLService registrationNoSQLService;

    public Document checkUser(String userName, String userPassword, String dbType) {
        if(dbType.equals("SQL")){
            return registrationSQLService.checkLogin(userName,userPassword);
        }else{
            return registrationNoSQLService.checkLogin(userName,userPassword);
        }
    }

    public HashMap<String, Object> registerUser(HashMap<String, Object> payload, String dbType) {
        if(dbType.equals("SQL")){
            return registrationSQLService.registerUser(payload);
        }else{
            return registrationNoSQLService.registerUser(payload);
        }
    }
}
