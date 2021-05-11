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
public class Photo implements Dto<Photo> {
    private Integer photoId;
    private String description;
    private String date;
    private String link;
    private Integer recipeId;

    public Photo(Integer photoId, String description, String date, String link, Integer recipeId) {
        this.photoId = photoId;
        this.description = description;
        this.date = date;
        this.recipeId = recipeId;
        this.link = link;
    }

    @Override
    public void createTable() {
        String CREATE_PHOTO_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS photo(photo_id int primary key, description varchar, " +
                "date varchar, link varchar, recipe_id int, " +
                "CONSTRAINT fk_recipe FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id))";
        DatabaseSession.executeUpdate(CREATE_PHOTO_TABLE_QUERY);
    }

    @Override
    public void dropTable() {
        log.info("drop table photo");

        String DROP_PHOTO_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS photo";
        DatabaseSession.executeUpdate(DROP_PHOTO_IF_EXISTS_QUERY);
        log.info("drop table completed");
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO photo(photo_id, description, date, links, recipe_id) VALUES " +
                "(" + this.photoId + ", '" + this.description + "', '" +
                this.date + "', " + this.link+ ", " +  this.recipeId + ")";
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
                        resultSet.getString("date"), resultSet.getString("link"),
                        resultSet.getInt("recipe_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoList;
    }

   /* */public static void generateData() {

        String[] date = new String[] {"12-3-21", "15-10-20", "18-10-20", "4-4-21", "24-1-21", "12-5-21"};
        String[] descriptions = new String[] {"Preparation"};
        String[] linkLists = new String[] {"soup", "burger"};


        int photo_id = 0;
        for(int i = 0; i < CookingSiteProperties.getRecipeAmount(); ++i) {
            String RANDOM_PHOTO_QUERY = "INSERT INTO photo(photo_id, description, date, link, recipe_id) " +
                    "VALUES(" + photo_id + ", '" +
                    "Cover Photo" + "', '" +
                    date[ThreadLocalRandom.current().nextInt(0, date.length)]+ "', '" +
                    linkLists[ThreadLocalRandom.current().nextInt(0, linkLists.length)] + "'," +
                    ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getRecipeAmount()) + ")" ;
            DatabaseSession.executeUpdate(RANDOM_PHOTO_QUERY);
            photo_id +=1;
            //log.info(RANDOM_PHOTO_QUERY);

            for(int j = 0; j < CookingSiteProperties.getPhotoAmount(); ++j) {
                RANDOM_PHOTO_QUERY = "INSERT INTO photo(photo_id, description, date, link, recipe_id) " +
                        "VALUES(" + photo_id + ", '" +
                        descriptions[ThreadLocalRandom.current().nextInt(0, descriptions.length)] + "', '" +
                        //"Cover Pho" + "', '" +

                        date[ThreadLocalRandom.current().nextInt(0, date.length)]+ "', '" +
                        linkLists[ThreadLocalRandom.current().nextInt(0, linkLists.length)] + "'," +
                        ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getRecipeAmount()) + ")" ;
                DatabaseSession.executeUpdate(RANDOM_PHOTO_QUERY);
                //log.info(RANDOM_PHOTO_QUERY);
                photo_id +=1;
            }
        }
    }
}