package repository;

import pojo.Order;

import java.util.HashMap;
import java.util.Map;
//wordt gebruikt om orders aan te maken en in te zien
public class OrderRepository {
    private final Map<String, Order> datastore = new HashMap<>();

    public void createOrder(Order order) {
        this.datastore.put(order.getId(), order);
    }

    public Order retrieveOrder(String id) {
        return this.datastore.get(id);
    }

    public void updateOrder(Order order) {
        this.datastore.put(order.getId(), order);
    }

    public void deleteOrder(String id) {
        this.datastore.remove(id);
    }
}
