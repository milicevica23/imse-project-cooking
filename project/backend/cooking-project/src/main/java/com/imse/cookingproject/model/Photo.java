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

        String DROP_PHOTO_IF_EXISTS_QUERY = "DROP TABLE photo";
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
        String[] linkLists = new String[] {"https://img.chefkoch-cdn.de/rezepte/1491131254215808/bilder/1021900/crop-960x540/spaghetti-carbonara.jpg",
                "https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/DF841126-374E-41E5-804D-ADF1A7942454/Derivates/40B4FE9D-7202-452B-B45A-7B4E572715CC.jpg",
                "https://img.chefkoch-cdn.de/rezepte/259781101566295/bilder/1330750/crop-960x720/kuerbissuppe-mit-ingwer-und-kokosmilch.jpg",
                "https://ais.kochbar.de/vms/5ced0e371d90da128862f2c2/1200x1200/burger.jpg",
                "https://images.eatsmarter.de/sites/default/files/styles/max_size/public/risotto-mit-pilzen-32235.jpg",
                "https://images.eatsmarter.de/sites/default/files/styles/1600x1200/public/pizza-mit-salami-und-kaese-424770.jpg",
                "https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/98C7A5E2-47D4-4495-B6B7-E97446BACD45/Derivates/0CD46940-3985-4FC7-82D3-08B56D53E842.jpg",
                "https://www.seasonsandsuppers.ca/wp-content/uploads/2013/01/mac-cheese-3.jpg"
        };


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