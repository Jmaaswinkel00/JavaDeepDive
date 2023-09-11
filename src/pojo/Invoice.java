package pojo;

import java.util.ArrayList;

public class Invoice extends Order{

    private String pickUpDate;
    //invoice class
    public Invoice(String id, Customer customer,  Basket basket, String pickUpDate) {
        super(id, customer, basket);
        this.pickUpDate = pickUpDate;
    }

    public static Order getOrder(Customer customer, Basket basket){
        return new Order("1", customer, basket);
    }
    public String getPickUpDate() {
        return pickUpDate;
    }
    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }
}
