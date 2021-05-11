package com.imse.cookingproject.model;

import com.imse.cookingproject.CookingSiteProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Data
@RequiredArgsConstructor
public class Users implements Dto<Users>{

    private Integer userId;
    private String username;
    private String password;
    private String name;
    private String email;

    public Users(Integer userId, String username, String password, String name, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    @Override
    public void createTable() {
        log.info("create tables users");
        String CREATE_USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS users(user_id int primary key, username varchar, password varchar, name varchar, email varchar)";
        DatabaseSession.executeUpdate(CREATE_USERS_TABLE_QUERY);
    }
    @Override
    public void dropTable() {
        log.info("drop tables Users");
        String DROP_USERS_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS users";
        DatabaseSession.executeUpdate(DROP_USERS_IF_EXISTS_QUERY);
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO users(user_id, username, password, name, email) VALUES " +
                "(" + this.userId + ", '" + this.username + "', '" + this.password + "', '" + this.name +"' , '" + this.email + "')";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deleteUser(int id) {
        String deleteQuery = "DELETE FROM users WHERE user_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<Users> returnList() {
        String selectQuery = "SELECT * FROM users";
        List<Users> usersList= new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return usersList;
            while (resultSet.next()) {
                usersList.add(new Users(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("name"), resultSet.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public static Users getSelected(int id) {
        String selectQuery = "SELECT * FROM users WHERE user_id = " + id;
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);

        try{
            if (!resultSet.wasNull()) {
                log.info(resultSet.getString("username"));
                return new Users(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("name"), resultSet.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Users( 404,"this user does not exist", "not exist", "not exist", "not exist");
    }

    public static void generateData() {
        String[] name = new String[]{"Burcu", "Aleks", "Martin", "Luca", "Dejan", "Julia"};
        String[] password = new String[] {"123434", "0789", "13434", "23483", "210923"};
        String[] email = new String[]{"@gmail.com", "@hotmail.com", "@outlook.com"};

        for(int i = 0; i< CookingSiteProperties.getUserAmount() ; ++i) {
            Integer currentName = ThreadLocalRandom.current().nextInt(0, name.length);

            String RANDOM_USER_QUERY = "INSERT INTO users(user_id, username, password, name, email) VALUES(" + i + ", '" + name[currentName] + i + "', '" +
                    password[ThreadLocalRandom.current().nextInt(0, password.length)]+ "', '" + name[currentName] + "', '" +
                    name[currentName] + email[ThreadLocalRandom.current().nextInt(0, email.length)] + "')" ;
            DatabaseSession.executeUpdate(RANDOM_USER_QUERY);
        }
    }

}
