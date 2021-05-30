package com.imse.cookingproject.service.SQL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class RegistrationSQLService {

    public HashMap<String, Object> checkLogin(String userName, String userPassword) {
        return UtilsSQL.checkUser(userName,userPassword);
    }

    public HashMap<String, Object> registerUser(HashMap<String, Object> payload) {
        return UtilsSQL.registerUser(payload);
    }
}
