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

@Data
@Slf4j
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
        String CREATE_INGREDIENT_TABLE_QUERY = "CREATE TABLE ingredient(ingredient_id int primary key, ingredient_name varchar)";
        DatabaseSession.executeUpdate(CREATE_INGREDIENT_TABLE_QUERY);

        String CREATE_INGREDIENT_RECIPE_TABLE_QUERY = "CREATE TABLE recipe_ingredient(\n" +
                "                                recipe_id int,\n" +
                "                                ingredient_id int,\n" +
                "                                amount varchar,\n" +
                "                               CONSTRAINT pk_recipe_ingredient PRIMARY KEY (recipe_id,ingredient_id),\n" +
                "                               CONSTRAINT fk_recipe_ing FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id) ON DELETE CASCADE,\n" +
                "                               CONSTRAINT fk_rec_ingredient FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id) ON DELETE CASCADE\n" +
                "                              );";
        DatabaseSession.executeUpdate(CREATE_INGREDIENT_RECIPE_TABLE_QUERY);
    }
    @Override
    public void dropTable() {
        String DROP_INGREDIENT_RECIPE_IF_EXISTS_QUERY = "DROP TABLE recipe_ingredient";
        DatabaseSession.executeUpdate(DROP_INGREDIENT_RECIPE_IF_EXISTS_QUERY);

        String DROP_INGREDIENT_IF_EXISTS_QUERY = "DROP TABLE ingredient";
        DatabaseSession.executeUpdate(DROP_INGREDIENT_IF_EXISTS_QUERY);
        log.info("drop tables Ingredient");
    }

    @Override
    public void saveInsertToDatabase() {
        throw new RuntimeException("not up to date");
        /*String insertQuery = "INSERT INTO ingredient(ingredient_id, ingredient_name, type, recipe_id) VALUES " +
                "(" + this.ingredientId + ", '" + this.ingredientName + "', '" + this.type + "', " + this.recipeId + ")";
        DatabaseSession.executeUpdate(insertQuery);*/
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
        String[] ingredientName = new String[]{"milk", "carrot", "salami", "banana", "sugar", "pasta"};
        String[] type = new String[]{"dairy", "vegetable", "fruit", "meat", "fat"};
        int combination_id =0;

        for(int i = 0; i<CookingSiteProperties.getIngredientAmount(); ++i) {
            String RANDOM_INGREDIENT_QUERY = "INSERT INTO ingredient(ingredient_id, ingredient_name) " +
                    "VALUES(" + i + ", '" +
                    ingredientName[ThreadLocalRandom.current().nextInt(0, ingredientName.length)]+ "')" ;
            log.info(RANDOM_INGREDIENT_QUERY);
            DatabaseSession.executeUpdate(RANDOM_INGREDIENT_QUERY);
        }

        for(int i= 0; i< CookingSiteProperties.getRecipeAmount();++i){
            int ingredient_num = ThreadLocalRandom.current().nextInt(0, 10);
            ArrayList<Integer> usedIngredient = new ArrayList<>();
            for(int j = 0; j< ingredient_num; ++j){
                Integer ingredientId = ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getIngredientAmount());
                if(!usedIngredient.contains(ingredientId)){
                    usedIngredient.add(ingredientId);
                    String RANDOM_INGREDIENT_QUERY = "insert into recipe_ingredient(recipe_id,ingredient_id,amount) values("+ i + "," + ingredientId +",'" + ThreadLocalRandom.current().nextInt(0, 5)+"');" ;
                    log.info(RANDOM_INGREDIENT_QUERY);
                    DatabaseSession.executeUpdate(RANDOM_INGREDIENT_QUERY);
                }
            }
        }
    }
}
