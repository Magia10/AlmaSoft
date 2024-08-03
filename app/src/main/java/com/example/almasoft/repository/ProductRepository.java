package com.example.almasoft.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.almasoft.model.Product;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> allProduct;
    private ExecutorService executorService;

    public ProductRepository(Application application) {
        database db = database.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
        allProduct = productDao.getAllProduct();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Product product) {
        executorService.execute(() -> productDao.insert(product));
    }

    public void update(Product product) {
        executorService.execute(() -> productDao.update(product));
    }

    public void delete(Product product) {
        new DeleteProductAsyncTask(productDao).execute(product);
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<List<Product>> getAllProduct() {
        return allProduct;
    }

    public LiveData<Product> getProductById(int id) {
        return productDao.getProductById(id);
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private InsertProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private UpdateProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.update(products[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private DeleteProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.delete(products[0]);
            return null;
        }
    }

}
