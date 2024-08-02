package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.MovementViewModel;
import com.example.almasoft.viewmodel.ProductViewModel;
import java.util.ArrayList;
import java.util.List;

public class ProductMovementsActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private MovementViewModel movementViewModel;
    private List<Product> productList;
    private MovementAdapter movementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_movements);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnSearch = findViewById(R.id.btnSearch);
        EditText etSearch = findViewById(R.id.etSearch);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        movementViewModel = new ViewModelProvider(this).get(MovementViewModel.class);

        movementAdapter = new MovementAdapter(this, new ArrayList<>(), movementViewModel,
                productViewModel);
        recyclerView.setAdapter(movementAdapter);

        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> _productList) {
                try {
                    productList = _productList;
                    movementAdapter.updateList(productList);
                }catch (Exception e){
                    Toast.makeText(ProductMovementsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductMovementsActivity.this, ProductHomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterMovements(etSearch.getText().toString());
            }
        });
    }

    private void filterMovements(String filter) {
        if (productList == null) return;

        if(filter.isEmpty()){
            movementAdapter.updateList(productList);
        } else {
            List<Product> filteredProducts = new ArrayList<>();
            for (Product product : productList) {
                if (product.getName().contains(filter)) {
                    filteredProducts.add(product);
                }
            }
            movementAdapter.updateList(filteredProducts);
        }
    }

}