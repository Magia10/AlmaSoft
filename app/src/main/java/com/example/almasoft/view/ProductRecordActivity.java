package com.example.almasoft.view;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;

public class ProductRecordActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private ProductAdapter adapter;
    private EditText editTextFilter;
    private Button buttonFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_record);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ProductAdapter(ProductRecordActivity.this);
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        editTextFilter = findViewById(R.id.editTextFilter);
        buttonFilter = findViewById(R.id.buttonFilter);

        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                filterProducts(products);
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productViewModel.getAllProducts().observe(ProductRecordActivity.this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        filterProducts(products);
                    }
                });
            }
        });

        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productViewModel.getAllProducts().observe(ProductRecordActivity.this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        filterProducts(products);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void addProduct() {
        Intent i = new Intent(this, ProductCreateActivity.class);
        startActivity(i);
    }

    private void filterProducts(List<Product> products) {
        String query = editTextFilter.getText().toString().toLowerCase().trim();

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            boolean matchesName = query.isEmpty() || product.getName().toLowerCase().contains(query);
            boolean matchesBrand = query.isEmpty() || product.getBrand().toLowerCase().contains(query);
            boolean matchesWarehouse = query.isEmpty() || product.getAddress().toLowerCase().contains(query);

            if (matchesName || matchesBrand || matchesWarehouse) {
                filteredProducts.add(product);
            }
        }
        adapter.setProducts(filteredProducts);
    }

    public void enviarHome(View view) {
        Intent intent = new Intent(this, ProductHomeActivity.class);
        startActivity(intent);
        finish();
    }
}