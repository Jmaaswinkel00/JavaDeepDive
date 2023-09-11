package pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Photo {
    private String id;
    private String name;
    private String prepareTime;
    private Integer amount;
    private BigDecimal price;

    //photo class, wordt gebruikt om items in te laden
    public Photo(String id, String name, String prepareTime, Integer amount, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.prepareTime = prepareTime;
        this.amount = amount;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrepareTime() {
        return prepareTime;
    }
    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(amount));
    }
    public int getTotalPrepareTime() {
        return Integer.parseInt(prepareTime.split(":")[0]) * amount;
    }

}


