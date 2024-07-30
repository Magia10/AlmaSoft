package com.example.almasoft.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movement_table")
public class Movement {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int action;
    private int productId;
    private int quantity;
    private String date;

    public Movement (int action, int productId, int quantity, String date){
        this.action = action;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
