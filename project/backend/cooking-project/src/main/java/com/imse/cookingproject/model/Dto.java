package com.imse.cookingproject.model;

public interface Dto<T> {
    void createTable();
    void dropTable();
    void saveInsertToDatabase();

    //static List<T> returnList();
}
