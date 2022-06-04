package com.example.rohlik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "myOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemInOrder> itemsInOrder;
    private boolean payment;
    private Date creationDate;

    public Order() {
        this.creationDate = new Date();
        this.payment = false;
    }

    public Order(List<ItemInOrder> itemsInOrder) {
        this.itemsInOrder = itemsInOrder;
        this.creationDate = new Date();
        this.payment = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ItemInOrder> getProducts() {
        return itemsInOrder;
    }

    public void setProducts(List<ItemInOrder> itemsInOrder) {
        this.itemsInOrder = itemsInOrder;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


}
