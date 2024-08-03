package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasoft.R;
import com.example.almasoft.model.Movement;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.MovementViewModel;
import com.example.almasoft.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductReportActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private MovementViewModel movementViewModel;
    private ReportAdapter reportAdapter;
    private EditText editTextFilter;
    private List<Product> productList = new ArrayList<>();
    private List<Movement> movementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_report);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        movementViewModel = new ViewModelProvider(this).get(MovementViewModel.class);

        Button btnBack = findViewById(R.id.btnBack);
        Button buttonFilter = findViewById(R.id.btnSearch);
        editTextFilter = findViewById(R.id.etSearch);

        reportAdapter = new ReportAdapter(productList, movementList);
        recyclerView.setAdapter(reportAdapter);

        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productList = products;
                reportAdapter = new ReportAdapter(productList, movementList);
                recyclerView.setAdapter(reportAdapter);
            }
        });

        movementViewModel.getAllMovements().observe(this, new Observer<List<Movement>>() {
            @Override
            public void onChanged(List<Movement> movements) {
                movementList = movements;
                filterProducts(movements);
            }
        });

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProducts(movementList);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductReportActivity.this, ProductHomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void filterProducts(List<Movement> movements) {
        String query = editTextFilter.getText().toString().toLowerCase().trim();

        if (query.isEmpty()) {
            reportAdapter.setMovements(movements);
        } else {
            List<Movement> filteredMovements = new ArrayList<>();
            for (Movement movement : movements) {
                String action = movement.getAction() == 1 ? "Ingreso" : "Salida";
                String productName = "-";
                for (Product product : productList) {
                    if (product.getId() == movement.getProductId()) {
                        productName = product.getName();
                        break;
                    }
                }
                boolean matchesAction = action.toLowerCase().contains(query);
                boolean matchesProductName = productName.toLowerCase().contains(query);
                boolean matchesDate = movement.getDate().toLowerCase().contains(query);

                if (matchesAction || matchesProductName || matchesDate) {
                    filteredMovements.add(movement);
                }
            }
            reportAdapter.setMovements(filteredMovements);
        }
    }

}