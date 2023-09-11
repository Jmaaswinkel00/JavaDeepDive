package pojo;

import java.util.ArrayList;

public class Order{
    private String id;
    private Customer customer;
    private Basket basket;

    //order class
    public Order(String id, Customer customer, Basket basket) {
        this.id = id;
        this.customer = customer;
        this.basket = basket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Basket getBasket() {
        return basket;
    }
    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
