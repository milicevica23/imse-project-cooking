package com.imse.cookingproject.model;

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
public class UserRecipeComment implements Dto<UserRecipeComment>{
    private Integer comment_id;
    private Integer userId;
    private Integer recipeId;
    private String date;
    private String content;

    public UserRecipeComment(Integer comment_id, Integer userId, Integer recipeId, String date, String content) {
        this.comment_id = comment_id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.date = date;
        this.content = content;
    }
    @Override
    public void createTable() {
        String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS user_recipe_comment(comment_id int primary key, user_id int, recipe_id int, " +
                "date varchar, content varchar, " +
                "CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(user_id)," +
                "CONSTRAINT fk_recipe FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id))";
        DatabaseSession.executeUpdate(CREATE_TABLE_QUERY);
    }

    @Override
    public void dropTable() {
        String DROP_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS user_recipe_comment";
        DatabaseSession.executeUpdate(DROP_IF_EXISTS_QUERY);
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO user_recipe_comment(comment_id, user_id, recipe_id, date, content) VALUES " +
                "(" + this.comment_id + ", " + this.userId + ", " + this.recipeId + ", '" + this.date + "', '" + this.content + "')";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deleteComment(int id) {
        String deleteQuery = "DELETE FROM ingredient WHERE comment_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<UserRecipeComment> returnList() {
        String selectQuery = "SELECT * FROM user_recipe_comment";
        List<UserRecipeComment> UserRecipeCommentList = new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return UserRecipeCommentList;
            while (resultSet.next()) {
                UserRecipeCommentList.add(new UserRecipeComment(resultSet.getInt("comment_id"), resultSet.getInt("user_id"),
                        resultSet.getInt("recipe_id"), resultSet.getString("date"), resultSet.getString("content")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserRecipeCommentList;
    }

    public static void generateData() {
        String[] date = new String[] {"12-3-21", "15-10-20", "18-10-20", "4-4-21", "24-1-21", "12-5-21"};
        String[] content = new String[]{"best recipe", "liked this", "too salty", "could be more sweet"};

        for(int i = 0; i<50; ++i) {
            String RANDOM_QUERY = "INSERT INTO user_recipe_comment(comment_id, user_id, recipe_id, date, content) VALUES(" + i + ", " + i + ", " + i + ", '" +
                    date[ThreadLocalRandom.current().nextInt(0, date.length)] + "', '" +
                    content[ThreadLocalRandom.current().nextInt(0, content.length)]+ "')" ;
            DatabaseSession.executeUpdate(RANDOM_QUERY);
        }
    }
}
