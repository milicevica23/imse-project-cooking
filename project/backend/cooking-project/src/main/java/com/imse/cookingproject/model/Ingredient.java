package com.imse.cookingproject.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@RequiredArgsConstructor
public class Ingredient implements Dto<Ingredient>{
    private Integer ingredientId;
    private String ingredientName;
    private String type;
    private Integer recipeId;

    public Ingredient(Integer ingredientId, String ingredientName, String type, Integer recipeId) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.type = type;
        this.recipeId = recipeId;
    }

    @Override
    public void createTable() {
        String CREATE_INGREDIENT_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS ingredient(ingredient_id int primary key, ingredient_name varchar, type varchar, recipe_id int)";
        DatabaseSession.executeUpdate(CREATE_INGREDIENT_TABLE_QUERY);
    }
    @Override
    public void dropTable() {
        String DROP_INGREDIENT_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS ingredient";
        DatabaseSession.executeUpdate(DROP_INGREDIENT_IF_EXISTS_QUERY);
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO ingredient(ingredient_id, ingredient_name, type, recipe_id) VALUES " +
                "(" + this.ingredientId + ", '" + this.ingredientName + "', '" + this.type + "', " + this.recipeId + ")";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deleteIngredient(int id) {
        String deleteQuery = "DELETE FROM ingredient WHERE ingredient_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<Ingredient> returnList() {
        String selectQuery = "SELECT * FROM ingredient";
        List<Ingredient> ingredientList= new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return ingredientList;
            while (resultSet.next()) {
                ingredientList.add(new Ingredient(resultSet.getInt("ingredient_id"), resultSet.getString("ingredient_name"),
                        resultSet.getString("type"), resultSet.getInt("recipe_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientList;
    }

    public static void generateData() {
        /*DatabaseSession.executeUpdate("INSERT INTO ingredient(ingredient_id, ingredient_name, type) VALUES(0, 'carrot', 'veg')");
        DatabaseSession.executeUpdate("INSERT INTO ingredient(ingredient_id, ingredient_name, type) VALUES(1, 'banana', 'fruit')");
        DatabaseSession.executeUpdate("INSERT INTO ingredient(ingredient_id, ingredient_name, type) VALUES(2, 'milk', 'dairy')");*/

        String[] ingredientName = new String[]{"milk", "carrot", "salami", "banana", "sugar", "pasta"};
        String[] type = new String[]{"dairy", "vegetable", "fruit", "meat", "fat"};

        for(int i = 0; i<50; ++i) {
            String RANDOM_INGREDIENT_QUERY = "INSERT INTO ingredient(ingredient_id, ingredient_name, type, recipe_id) VALUES(" + i + ", '" +
                    ingredientName[ThreadLocalRandom.current().nextInt(0, ingredientName.length)] + "', '" +
                    type[ThreadLocalRandom.current().nextInt(0, type.length)]+ "', " + i + ")" ;
            DatabaseSession.executeUpdate(RANDOM_INGREDIENT_QUERY);
        }
    }
}
