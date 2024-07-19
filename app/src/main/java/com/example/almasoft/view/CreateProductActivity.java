package com.example.almasoft.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;

public class CreateProductActivity extends AppCompatActivity {


    private EditText editTextName, editTextBrand, editTextQuantity, editTextPurchasePrice, editTextPublicPrice;
    private Spinner spinnerLocation;
    private Button buttonAdd;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_product);
        editTextName = findViewById(R.id.edit_text_name);
        editTextBrand = findViewById(R.id.edit_text_brand);
        editTextQuantity = findViewById(R.id.edit_text_quantity);
        editTextPurchasePrice = findViewById(R.id.edit_text_purchase_price);
        editTextPublicPrice = findViewById(R.id.edit_text_public_price);
        spinnerLocation = findViewById(R.id.spinner_location);
        buttonAdd = findViewById(R.id.button_add);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes manejar el clic del botón AGREGAR
                String name = editTextName.getText().toString();
                String brand = editTextBrand.getText().toString();
                String quantity = editTextQuantity.getText().toString();
                String purchasePrice = editTextPurchasePrice.getText().toString();
                String salePrice = editTextPublicPrice.getText().toString();
                String location = spinnerLocation.getSelectedItem().toString();

                productViewModel.insert(new Product(name,Double.parseDouble(salePrice),
                        Double.parseDouble(purchasePrice),brand,location,Integer.parseInt(quantity)));

                // Ejemplo de uso de Toast para mostrar la entrada
                Toast.makeText(CreateProductActivity.this, "Producto agregado: " + name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}