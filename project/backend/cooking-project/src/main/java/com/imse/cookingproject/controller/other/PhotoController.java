package com.imse.cookingproject.controller.other;

import com.imse.cookingproject.model.Photo;
import com.imse.cookingproject.service.otherServices.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/add")
    public void postPhoto(@RequestBody Photo photo) {
        photoService.saveToDatabase(photo);
    }

    @PostMapping("/dropPhotos")
    public void dropTable() {
        photoService.dropTable();
    }

    @GetMapping("/listPhotos")
    public List<Photo> getRecipes() {
        return photoService.getPhotosList();
    }

    @PutMapping("/deletePhoto/{photo_id}")
    public void deletePhoto(@PathVariable int photo_id) {
        photoService.deletePhoto(photo_id);
    }

    @PostMapping("/generatePhotos")
    public void generateData() {
        photoService.generateData();
    }
}
