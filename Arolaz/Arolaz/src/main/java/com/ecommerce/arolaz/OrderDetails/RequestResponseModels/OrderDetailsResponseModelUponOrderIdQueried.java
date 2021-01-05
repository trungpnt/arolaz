package com.ecommerce.arolaz.OrderDetails.RequestResponseModels;

public class OrderDetailsResponseModelUponOrderIdQueried {
    private String id;

    private String name;

    private Integer price;

    private Integer amount;

    private String size;

    public OrderDetailsResponseModelUponOrderIdQueried(String id, String name, Integer price, Integer amount, String size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.size = size;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
