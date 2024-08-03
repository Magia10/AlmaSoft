package com.example.almasoft.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.almasoft.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product_table WHERE state = 1 ORDER BY name ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM product_table")
    LiveData<List<Product>> getAllProduct();

    @Query("SELECT * FROM product_table WHERE id = :filter")
    LiveData<Product> getProductById(int filter);
}
