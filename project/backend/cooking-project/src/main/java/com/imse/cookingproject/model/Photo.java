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
public class Photo implements Dto<Photo> {
    private Integer photoId;
    private String description;
    private String date;
    private Integer recipeId;

    public Photo(Integer photoId, String description, String date, Integer recipeId) {
        this.photoId = photoId;
        this.description = description;
        this.date = date;
        this.recipeId = recipeId;
    }

    public static void deleteInstruction(int instruction_id) {
    }

    @Override
    public void createTable() {
        String CREATE_PHOTO_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS photo(photo_id int primary key, description varchar, date varchar, recipe_id int)";
        DatabaseSession.executeUpdate(CREATE_PHOTO_TABLE_QUERY);
    }

    @Override
    public void dropTable() {
        String DROP_PHOTO_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS photo";
        DatabaseSession.executeUpdate(DROP_PHOTO_IF_EXISTS_QUERY);
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO photo(photo_id, description, date, recipe_id) VALUES " +
                "(" + this.photoId + ", '" + this.description + "', '" + this.date + "', " + this.recipeId + ")";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deletePhoto(int id) {
        String deleteQuery = "DELETE FROM photo WHERE photo_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<Photo> returnList() {
        String selectQuery = "SELECT * FROM photo";
        List<Photo> photoList = new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try {
            if (resultSet.wasNull()) return photoList;
            while (resultSet.next()) {
                photoList.add(new Photo(resultSet.getInt("photo_id"), resultSet.getString("description"),
                        resultSet.getString("date"), resultSet.getInt("recipe_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoList;
    }

    public static void generateData() {
       /* DatabaseSession.executeUpdate("INSERT INTO photo(photo_id, description, date) VALUES (0, 'Mac and Cheese photo', '12-3-21')");
        DatabaseSession.executeUpdate("INSERT INTO photo(photo_id, description, date) VALUES (1, 'Pizza photo', '18-10-20')");
        DatabaseSession.executeUpdate("INSERT INTO photo(photo_id, description, date) VALUES (2, 'Burger photo', '15-10-20')");*/

        String[] description = new String[]{"Ingredients Photo", "Preparation Photo", "Done Photo", "Step 1 Photo", "Step 2 Photo", "Step 3 Photo"};
        String[] date = new String[] {"12-3-21", "15-10-20", "18-10-20", "4-4-21", "24-1-21", "12-5-21"};

        for(int i = 0; i<50; ++i) {
            String RANDOM_PHOTO_QUERY = "INSERT INTO photo(photo_id, description, date, recipe_id) VALUES(" + i + ", '" +
                    description[ThreadLocalRandom.current().nextInt(0, description.length)] + "', '" +
                    date[ThreadLocalRandom.current().nextInt(0, date.length)]+ "', " + i +")" ;
            DatabaseSession.executeUpdate(RANDOM_PHOTO_QUERY);
        }
    }
}