package com.imse.cookingproject.controller.registration;


import com.imse.cookingproject.service.main.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.HasControls;
import java.util.HashMap;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("user")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;


    @GetMapping("/login")
    public HashMap<String, Object> loginUser(@RequestParam String userName, @RequestParam String userPassword, @RequestParam String dbType){
        return registrationService.checkUser(userName,userPassword,dbType);
    }

    @PostMapping("/register")
    public HashMap<String, Object> loginUser(@RequestBody HashMap<String,Object> payload, @RequestParam String dbType){
        return registrationService.registerUser(payload,dbType);
    }
}
