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
public class UserRecipeRating implements Dto<UserRecipeRating>{
    private Integer ratingId;
    private Integer userId;
    private Integer recipeId;
    private String date;
    private Integer rating;

    public UserRecipeRating(Integer userId, Integer ratingId, Integer recipeId, String date, Integer rating) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.recipeId = recipeId;
        this.date = date;
        this.rating = rating;
    }

    @Override
    public void createTable() {
        String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS user_recipe_rating(rating_id int primary key, user_id int, recipe_id int, " +
                "date varchar, rating int, " +
                "CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(user_id)," +
                "CONSTRAINT fk_recipe FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id))";
        DatabaseSession.executeUpdate(CREATE_TABLE_QUERY);
    }

    @Override
    public void dropTable() {
        log.info("drop tables UserRecipeRating");
        String DROP_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS user_recipe_rating";
        DatabaseSession.executeUpdate(DROP_IF_EXISTS_QUERY);
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO user_recipe_rating(rating_id, user_id, recipe_id, date, rating) VALUES " +
                "(" + this.ratingId + ", " + this.userId + ", " + this.recipeId + ", '" + this.date + "', " + this.rating + ")";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deleteRating(int id) {
        String deleteQuery = "DELETE FROM ingredient WHERE rating_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<UserRecipeRating> returnList() {
        String selectQuery = "SELECT * FROM user_recipe_rating";
        List<UserRecipeRating> userRecipeRatingList = new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return userRecipeRatingList;
            while (resultSet.next()) {
                userRecipeRatingList.add(new UserRecipeRating(resultSet.getInt("rating_id"), resultSet.getInt("user_id"),
                        resultSet.getInt("recipe_id"), resultSet.getString("date"), resultSet.getInt("rating")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRecipeRatingList;
    }

    public static void generateData() {
        String[] date = new String[] {"12-3-21", "15-10-20", "18-10-20", "4-4-21", "24-1-21", "12-5-21"};
        Integer[] rating = new Integer[] {1,2,3,4,5};

        for(int i = 0; i< CookingSiteProperties.getRatingAmount(); ++i) {
            String RANDOM_QUERY = "INSERT INTO user_recipe_rating(rating_id, user_id, recipe_id, date, rating) " +
                    "VALUES(" + i + ", " +
                    ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getUserAmount()) + ", " +
                    ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getRecipeAmount()) + ", '" +
                    date[ThreadLocalRandom.current().nextInt(0, date.length)] + "', " +
                    rating[ThreadLocalRandom.current().nextInt(0, rating.length)]+ ")" ;
            DatabaseSession.executeUpdate(RANDOM_QUERY);
        }
    }
}
