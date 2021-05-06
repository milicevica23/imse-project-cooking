package com.imse.cookingproject.service;

import com.imse.cookingproject.model.Users;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Users user = new Users();

    public void saveToDatabase(Users user) {
        user.saveInsertToDatabase();
    }

    public void dropTable() {
        user.dropTable();
    }

    public void deleteUser(int id) {
        Users.deleteUser(id);
    }

    public List<Users> getUsersList() {
        return Users.returnList();
    }

    public Users getSelected(int id) {
        return Users.getSelected(id);
    }

    public void generateData() {
        Users.generateData();
    }
}
