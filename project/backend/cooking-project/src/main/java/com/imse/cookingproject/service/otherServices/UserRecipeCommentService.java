package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.UserRecipeComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecipeCommentService {
    private final UserRecipeComment comment = new UserRecipeComment();

    public void saveToDatabase(UserRecipeComment comment) {
        comment.saveInsertToDatabase();
    }

    public void dropTable() {
        comment.dropTable();
    }

    public void deleteComment(int id) {
        UserRecipeComment.deleteComment(id);
    }

    public List<UserRecipeComment> getCommentsList() {
        return UserRecipeComment.returnList();
    }

    public void generateData() {
        UserRecipeComment.generateData();
    }
}
