package com.example.almasoft.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;

public class ProductActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private EditText editTextName, editTextPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextName = findViewById(R.id.edit_text_name);
        editTextPrice = findViewById(R.id.edit_text_price);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ProductAdapter adapter = new ProductAdapter(ProductActivity.this);
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                editProduct(product);
            }
        });
    }

    private void addProduct() {
        String name = editTextName.getText().toString().trim();
        String priceText = editTextPrice.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceText)) {
            Toast.makeText(this, "Ingrese Datos del Producto ", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceText);
        Product product = new Product(name, price);
        productViewModel.insert(product);
        Toast.makeText(this, "Producto Agregado", Toast.LENGTH_SHORT).show();
        editTextName.setText("");
        editTextPrice.setText("");
    }

    private void editProduct(Product product) {
        editTextName.setText(product.getName());
        editTextPrice.setText(String.valueOf(product.getPrice()));

        findViewById(R.id.button_add).setVisibility(View.GONE);

        // Implementar funcionalidad para editar producto si se desea
    }
    private void saveProductChanges(Product product) {
        String name = editTextName.getText().toString().trim();
        String priceText = editTextPrice.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceText)) {
            Toast.makeText(this, "Ingrese Datos del Producto", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceText);

        // Actualiza el producto con los nuevos datos
        product.setName(name);
        product.setPrice(price);

        // Llama al ViewModel para actualizar el producto
        productViewModel.update(product);
        Toast.makeText(this, "Producto Actualizado", Toast.LENGTH_SHORT).show();

        // Limpia los campos de texto y ajusta la visibilidad de los botones
        editTextName.setText("");
        editTextPrice.setText("");
        findViewById(R.id.button_add).setVisibility(View.VISIBLE);
    }



}