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
public class Instruction implements Dto<Instruction> {
    private Integer instructionId;
    private String stepNumber;
    private String content;
    private Integer recipeId;

    public Instruction(Integer instructionId, String stepNumber, String content, Integer recipeId) {
        this.instructionId = instructionId;
        this.stepNumber = stepNumber;
        this.content = content;
        this.recipeId = recipeId;
    }

    @Override
    public void createTable() {
        String CREATE_INSTRUCTION_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS instruction(instruction_id int primary key, step_number varchar, content varchar, recipe_id int)";
        DatabaseSession.executeUpdate(CREATE_INSTRUCTION_TABLE_QUERY);
    }

    @Override
    public void dropTable() {
        String DROP_INSTRUCTION_IF_EXISTS_QUERY = "DROP TABLE IF EXISTS instruction";
        DatabaseSession.executeUpdate(DROP_INSTRUCTION_IF_EXISTS_QUERY);
        log.info("drop tables Instruction");
    }

    @Override
    public void saveInsertToDatabase() {
        String insertQuery = "INSERT INTO instruction(instruction_id, step_number, content, recipe_id) VALUES " +
                "(" + this.instructionId + ", " + this.stepNumber + ", '" + this.content + ", " + this.recipeId + ")";
        DatabaseSession.executeUpdate(insertQuery);
    }

    public static void deleteInstruction(int id) {
        String deleteQuery = "DELETE FROM instruction WHERE instruction_id = " + id;
        DatabaseSession.executeUpdate(deleteQuery);
    }

    public static List<Instruction> returnList() {
        String selectQuery = "SELECT * FROM instruction";
        List<Instruction> instructionList= new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return instructionList;
            while (resultSet.next()) {
                instructionList.add(new Instruction(resultSet.getInt("instruction_id"), resultSet.getString("step_number"),
                        resultSet.getString("content"), resultSet.getInt("recipe_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructionList;
    }

    public static void generateData() {
        String[] content = new String[]{"wash the vegetables", "cut the onions", "boil water", "mash bananas", "put vegetables in the pan"};
        String[] number = new String[] {"1", "2", "3", "4", "5"};

        for(int i = 0; i<CookingSiteProperties.getInstructionAmount(); ++i) {
            String RANDOM_INGREDIENT_QUERY = "INSERT INTO instruction(instruction_id, step_number, content, recipe_id) " +
                    "VALUES(" + i + ", 'Step #" +
                    number[ThreadLocalRandom.current().nextInt(0, number.length)] + "', '" +
                    content[ThreadLocalRandom.current().nextInt(0, content.length)]+ "', " +
                    ThreadLocalRandom.current().nextInt(0, CookingSiteProperties.getRecipeAmount()) + ")" ;

            DatabaseSession.executeUpdate(RANDOM_INGREDIENT_QUERY);
        }
    }
}
