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
public class Recipe implements Dto<Recipe>{
    private Integer recipeId;
    private String recipeName;
    private String date;
    private Integer preparationTime;
    private Integer cookingTime;
    private String course;
    private String cuisine;
    private Integer userId;

    public Recipe(Integer recipeId, String recipeName, String date, Integer preparationTime, Integer cookingTime, String course, String cuisine, Integer userId) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.date = date;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.course = course;
        this.cuisine = cuisine;
        this.userId = userId;
    }

    @Override
    public void createTable() {
        log.info("create tables recipe");

        String CREATE_RECIPE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS recipe(recipe_id int primary key, recipe_name varchar, date varchar, " +
                "preparation_time int, cooking_time int, course varchar, cuisine varchar, user_id int," +
                "CONSTRAINT fk_users FOREIGN KEY(user_id) REFERENCES users(user_id))";
        DatabaseSession.executeUpdate(CREATE_RECIPE_TABLE_QUERY);
    }

    @Override
    public void dropTable() {
        log.info("drop tables Recipe");
        String DROP_RECIPE_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS recipe";
        DatabaseSession.executeUpdate(DROP_RECIPE_IF_EXISTS_QUERY);
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO recipe(recipe_id, recipe_name, date, preparation_time, cooking_time, course, cuisine, user_id) VALUES " +
                "(" + this.recipeId + ", '" + this.recipeName + "', '" + this.date + "', " + this.preparationTime + ", " +
                this.cookingTime +" , '" + this.course + "' , '" +  this.cuisine +"' , " + this.userId + ")";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deleteRecipe(int id) {
        String deleteQuery = "DELETE FROM recipe WHERE recipe_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<Recipe> returnList() {
        String selectQuery = "SELECT * FROM recipe";
        List<Recipe> recipeList= new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return recipeList;
            while (resultSet.next()) {
                recipeList.add(new Recipe(resultSet.getInt("recipe_id"), resultSet.getString("recipe_name"),
                        resultSet.getString("date"), resultSet.getInt("preparation_time"), resultSet.getInt("cooking_time"),
                        resultSet.getString("course"), resultSet.getString("cuisine"), resultSet.getInt("user_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    public static void generateData() {
        String[] recipeName = new String[]{"Mac and Cheese", "Cheese Burger", "Risotto", "Salami Pizza", "Pudding", "Banana Bread", "Carbonara Pasta", "Kürbis Suppe"};
        String[] date = new String[] {"12-3-21", "15-10-20", "18-10-20", "4-4-21", "24-1-21", "12-5-21"};
        Integer[] time = new Integer[]{5, 10, 15, 20, 30, 40};
        String[] course = new String[]{"breakfast", "lunch", "dinner"};
        String[] cuisine = new String[]{"american", "italian", "austrian", "turkish", "serbian"};

        for(int i = 0; i<CookingSiteProperties.getRecipeAmount(); ++i) {
            String RANDOM_RECIPE_QUERY = "INSERT INTO recipe(recipe_id, recipe_name, date, preparation_time, cooking_time, course, cuisine, user_id) " +
                "VALUES (" + i + ", '" + recipeName[ThreadLocalRandom.current().nextInt(0, recipeName.length)] + "', '" +
                         date[ThreadLocalRandom.current().nextInt(0, date.length)]+ "', " + time[ThreadLocalRandom.current().nextInt(0, time.length)] +
                    ", " + time[ThreadLocalRandom.current().nextInt(0, time.length)] + ", '" + course[ThreadLocalRandom.current().nextInt(0, course.length)] +
                    "', '" + cuisine[ThreadLocalRandom.current().nextInt(0, cuisine.length)] + "', " +
                    ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getUserAmount()) + ")" ;
            DatabaseSession.executeUpdate(RANDOM_RECIPE_QUERY);
        }
    }


}