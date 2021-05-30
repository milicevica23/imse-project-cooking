package com.imse.cookingproject.controller.other;

import com.imse.cookingproject.model.UserRecipeComment;
import com.imse.cookingproject.service.otherServices.UserRecipeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("comment")
public class UserRecipeCommentController {
    @Autowired
    private UserRecipeCommentService commentService;

    @PostMapping("/add")
    public void postComments(@RequestBody UserRecipeComment comment) {
        commentService.saveToDatabase(comment);
    }

    @PostMapping("/dropComments")
    public void dropTable() {
        commentService.dropTable();
    }

    @GetMapping("/listComments")
    public List<UserRecipeComment> getComments() {
        return commentService.getCommentsList();
    }

    @PutMapping("/deleteComment/{comment_id}")
    public void deleteComment(@PathVariable int comment_id) {
        commentService.deleteComment(comment_id);
    }

    @PostMapping("/generateData")
    public void generateData() {
        commentService.generateData();
    }
}
