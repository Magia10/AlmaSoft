package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.almasoft.R;

public class ProductHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_home);

        Button btnMovements = findViewById(R.id.btnMovements);
        Button btnRecords = findViewById(R.id.btnRecords);
        Button btnReport = findViewById(R.id.btnReport);

        btnMovements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductHomeActivity.this, ProductMovementsActivity.class);
                startActivity(intent);
            }
        });

        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductHomeActivity.this, ProductRecordActivity.class);
                startActivity(intent);
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductHomeActivity.this, ProductReportActivity.class);
                startActivity(intent);
            }
        });
    }
}