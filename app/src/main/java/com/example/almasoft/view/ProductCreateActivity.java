package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;

public class ProductCreateActivity extends AppCompatActivity {


    private EditText editTextName, editTextBrand, editTextQuantity, editTextPurchasePrice, editTextPublicPrice;
    private Spinner spinnerLocation;
    private Button buttonAdd;
    private ProductViewModel productViewModel;
    private Product product;
    private int productId;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create);

        editTextName = findViewById(R.id.edit_text_name);
        editTextBrand = findViewById(R.id.edit_text_brand);
        editTextQuantity = findViewById(R.id.edit_text_quantity);
        editTextPurchasePrice = findViewById(R.id.edit_text_purchase_price);
        editTextPublicPrice = findViewById(R.id.edit_text_public_price);
        spinnerLocation = findViewById(R.id.spinner_location);
        tvTitle = findViewById(R.id.textViewProducto);
        buttonAdd = findViewById(R.id.button_add);

        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", -1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        if(productId != -1){
            tvTitle.setText("Actualizar Producto");
            buttonAdd.setText("Actualizar");
            productViewModel.getProductById(productId).observe(this, new Observer<Product>() {
                @Override
                public void onChanged(Product _product) {
                    product = _product;
                    editTextName.setText(product.getName());
                    editTextBrand.setText(product.getBrand());
                    editTextPublicPrice.setText(String.valueOf(product.getSalePrice()));
                    editTextPurchasePrice.setText(String.valueOf(product.getPurchasePrice()));
                    editTextQuantity.setText(String.valueOf(product.getQuantity()));
                }
            });
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String brand = editTextBrand.getText().toString();
                String quantity = editTextQuantity.getText().toString();
                String purchasePrice = editTextPurchasePrice.getText().toString();
                String salePrice = editTextPublicPrice.getText().toString();
                String location = spinnerLocation.getSelectedItem().toString();

                if(name.isEmpty() || brand.isEmpty() || quantity.isEmpty() || purchasePrice.isEmpty()
                        || salePrice.isEmpty() || location.isEmpty()){
                    Toast.makeText(ProductCreateActivity.this, "Ingresar datos faltantes", Toast.LENGTH_SHORT).show();
                }else{
                    if(productId != -1){
                        product.setName(name);
                        product.setBrand(brand);
                        product.setQuantity(Integer.parseInt(quantity));
                        product.setSalePrice(Double.parseDouble(salePrice));
                        product.setPurchasePrice(Double.parseDouble(purchasePrice));
                        product.setAddress(location);
                        productViewModel.update(product);

                        Toast.makeText(ProductCreateActivity.this, "Producto actualizado: " + name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProductCreateActivity.this, ProductRecordActivity.class);
                        startActivity(intent);
                    }else{
                        productViewModel.insert(new Product(name,Double.parseDouble(salePrice),
                                Double.parseDouble(purchasePrice),brand,location,Integer.parseInt(quantity)));
                        Toast.makeText(ProductCreateActivity.this, "Producto agregado: " + name, Toast.LENGTH_SHORT).show();
                    }

                    cleanInputs();
                }
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(this, ProductRecordActivity.class);
        startActivity(intent);
        finish();
    }

    private void cleanInputs(){
        editTextName.setText("");
        editTextBrand.setText("");
        editTextPublicPrice.setText("");
        editTextPurchasePrice.setText("");
        editTextQuantity.setText("");
    }
}