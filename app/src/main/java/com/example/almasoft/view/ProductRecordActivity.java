package com.example.almasoft.view;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;

public class ProductRecordActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_record);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ProductAdapter adapter = new ProductAdapter(ProductRecordActivity.this);
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

    }

    // Método para ir a Registrar
    private void addProduct() {
        Intent i = new Intent(this, ProductCreateActivity.class);
        startActivity(i);
    }

    // Método para regresar a Home
    public void enviarHome(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish(); // Terminar la actividad actual para evitar volver atrás
    }
}