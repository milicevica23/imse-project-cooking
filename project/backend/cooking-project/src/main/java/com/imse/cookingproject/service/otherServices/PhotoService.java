package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final Photo photo = new Photo();

    public void saveToDatabase(Photo photo) {
        photo.saveInsertToDatabase();
    }

    public void dropTable() {
        photo.dropTable();
    }

    public List<Photo> getPhotosList() {
        return Photo.returnList();
    }

    public void deletePhoto(int photo_id) {
        Photo.deletePhoto(photo_id);
    }

    public void generateData() {
        Photo.generateData();
    }
}
