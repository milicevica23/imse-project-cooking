package com.imse.cookingproject.controller;

import com.imse.cookingproject.model.Users;
import com.imse.cookingproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UsersController {
  @Autowired
  private UserService userService;

  @PostMapping("/add")
  public void postUsers(@RequestBody Users user) {
    userService.saveToDatabase(user);
  }

  @PostMapping("/dropUsers")
  public void dropTable() {
    userService.dropTable();
  }

  @GetMapping("/listUsers")
  public List<Users> getUsers() {
    return userService.getUsersList();
  }

  @PutMapping("/deleteUser/{user_id}")
  public void deleteUser(@PathVariable int user_id) {
    userService.deleteUser(user_id);
  }

  @GetMapping("/select/{user_id}")
  public Users selectUser(@PathVariable int user_id) {
    return userService.getSelected(user_id);
  }

  @PostMapping("/generateData")
  public void generateData() {
    userService.generateData();
  }
}
