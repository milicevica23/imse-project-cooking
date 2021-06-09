package com.imse.cookingproject.service.SQL;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class RegistrationSQLService {

    public Document checkLogin(String userName, String userPassword) {
        return UtilsSQL.checkUser(userName,userPassword);
    }

    public HashMap<String, Object> registerUser(HashMap<String, Object> payload) {
        return UtilsSQL.registerUser(payload);
    }
}
