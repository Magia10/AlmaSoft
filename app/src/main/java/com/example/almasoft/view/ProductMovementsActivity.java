package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProductMovementsActivity extends AppCompatActivity {
    private MovementViewModel movementViewModel;
    private List<Movement> movements;
    private MovementAdapter movementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_movements);

        Button btnBack = findViewById(R.id.btnBack);

        Spinner spinner = findViewById(R.id.spinnerMovimientos);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movimientos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        movementAdapter = new MovementAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(movementAdapter);

        movementViewModel = new ViewModelProvider(this).get(MovementViewModel.class);

        movementViewModel.getAllMovements().observe(this, new Observer<List<Movement>>() {
            @Override
            public void onChanged(List<Movement> _movements) {
                try {
                    movements = _movements;
                    movementAdapter.updateList(movements);
                }catch (Exception e){
                    Toast.makeText(ProductMovementsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                filterMovements(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void filterMovements(String filter) {
        if (movements == null) return;

        List<Movement> filteredMovements = new ArrayList<>();
        for (Movement movement : movements) {
            if (filter.equals("Entradas") && movement.getAction() == 1) {
                filteredMovements.add(movement);
            } else if (filter.equals("Salidas") && movement.getAction() == 2) {
                filteredMovements.add(movement);
            }
        }

        movementAdapter.updateList(filteredMovements);
    }
}