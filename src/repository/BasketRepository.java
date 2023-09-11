package repository;

import pojo.Basket;

import java.util.HashMap;
import java.util.Map;

//wordt gebruik om een basket aan te maken en updaten
public class BasketRepository {

    private final Map<String, Basket> datastore = new HashMap<>();

    public void createBasket(Basket basket) {
        this.datastore.put(basket.getId(), basket);
    }

    public Basket retrieveBasket(String id) {
        return this.datastore.get(id);
    }

    public void updateBasket(Basket basket) {
        this.datastore.put(basket.getId(), basket);
    }

    public void deleteBasket(String id) {
        this.datastore.remove(id);
    }

}
