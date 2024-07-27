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

        Button btnInbound = findViewById(R.id.btnInbound);
        Button btnOutbound = findViewById(R.id.btnOutbound);
        Button btnNew = findViewById(R.id.btnNew);
        Button btnRecords = findViewById(R.id.btnRecords);

        btnInbound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductHomeActivity.this, ProductInboundActivity.class);
                startActivity(intent);
            }
        });

        btnOutbound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductHomeActivity.this, ProductOutboundActivity.class);
                startActivity(intent);
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductHomeActivity.this, ProductCreateActivity.class);
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

    }
}