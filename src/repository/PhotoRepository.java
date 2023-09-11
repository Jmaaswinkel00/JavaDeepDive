package repository;

import java.util.HashMap;
import java.util.Map;

import pojo.Photo;

//wordt gebruikt om foto's op te halen en aan te maken
public class PhotoRepository {

    private Map<String, Photo> datastore = new HashMap<>();

    public void createPhoto(Photo photo) {
        this.datastore.put(photo.getId(), photo);
    }

    public Photo retrievePhoto(String id) {
        return this.datastore.get(id);
    }
    public Photo retrievePhotoByName(String name) {
        return this.datastore.get(name);
    }

    public void updatePhoto(Photo photo) {
        this.datastore.put(photo.getId(), photo);
    }

    public void deletePhoto(String id) {
        this.datastore.remove(id);
    }

}