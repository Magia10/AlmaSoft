package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.almasoft.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnProduct = findViewById(R.id.btnProduct);
        Button btnSupplier = findViewById(R.id.btnSupplier);
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductHomeActivity.class);
                startActivity(intent);
            }
        });

        Button buttonProveedor = findViewById(R.id.buttonProveedor);
        buttonProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListadoProveedoresActivity.class);
                startActivity(intent);
            }
        });
    }
}







