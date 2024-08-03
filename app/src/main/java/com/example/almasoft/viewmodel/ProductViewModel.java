package com.example.almasoft.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.almasoft.model.Product;
import com.example.almasoft.repository.ProductRepository;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> allProduct;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
        allProduct = repository.getAllProduct();
    }
    public void insert(Product product) {
        repository.insert(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<List<Product>> getAllProduct() {
        return allProduct;
    }

    public LiveData<Product> getProductById(int id){return repository.getProductById(id);}
}

