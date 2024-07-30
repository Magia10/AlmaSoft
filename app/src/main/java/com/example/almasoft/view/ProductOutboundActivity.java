package com.example.almasoft.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.almasoft.R;
import com.example.almasoft.model.Movement;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.MovementViewModel;
import com.example.almasoft.viewmodel.ProductViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProductOutboundActivity extends AppCompatActivity {
    private EditText etStock;
    private Button btnAdd;
    private int productId;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_outbound);

        etStock = findViewById(R.id.editTextStock);
        btnAdd = findViewById(R.id.buttonAdd);
        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", -1);

        MovementViewModel movementViewModel = new ViewModelProvider(this).get(MovementViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getProductById(productId).observe( this, new Observer<Product>() {
            @Override
            public void onChanged(Product _product) {
                if (_product != null) {
                    product = _product;
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = dateFormat.format(calendar.getTime());
                    Movement movement = new Movement(2, productId, Integer.parseInt(etStock.getText().toString()), formattedDate);
                    movementViewModel.insert(movement);

                    if(product.getQuantity() > 0){
                        int stockUpdate = product.getQuantity() - Integer.parseInt(etStock.getText().toString());
                        product.setQuantity(stockUpdate);
                        productViewModel.update(product);
                        Toast.makeText(ProductOutboundActivity.this, "Movimiento registrado", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ProductOutboundActivity.this, "El producto no cuenta con suficiente stock", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }catch (Exception e){
                    Toast.makeText(ProductOutboundActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}