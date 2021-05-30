package com.imse.cookingproject.service.NoSQL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class RegistrationNoSQLService {
    public HashMap<String, Object> checkLogin(String userName, String userPassword) {
        return new HashMap<>();
    }

    public HashMap<String, Object> registerUser(HashMap<String, Object> payload) {
        return new HashMap<>();
    }
}
