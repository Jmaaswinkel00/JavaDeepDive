package pojo;

import java.util.ArrayList;

public class Basket {
    private String id;
    private ArrayList<Photo> items;
    //basket class
    public Basket(String id, ArrayList<Photo> items) {
        this.id = id;
        this.items = items;
    }

    public Basket(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Photo> getItems() {
        return items;
    }

    public void setItems(Photo photo) {
        items.add(photo);
    }

    public void deleteItem(Photo photo) {
        items.remove(photo);
    }
}
