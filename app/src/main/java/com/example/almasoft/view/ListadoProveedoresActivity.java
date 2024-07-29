package com.example.almasoft.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.database.ProveedorContract;
import com.example.almasoft.model.Proveedor;

import java.util.ArrayList;
import java.util.List;

public class ListadoProveedoresActivity extends AppCompatActivity {

    private AdminBD adminBD;
    private ListView listViewProveedores;
    private EditText etBuscarPorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_proveedores);

        adminBD = new AdminBD(this);

        etBuscarPorId = findViewById(R.id.etBuscar);
        listViewProveedores = findViewById(R.id.listViewProveedores);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = etBuscarPorId.getText().toString();
                if (idStr.isEmpty()) {
                    Toast.makeText(ListadoProveedoresActivity.this, "Ingrese un ID para buscar", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idStr);
                Proveedor proveedor = adminBD.obtenerProveedorPorId(id);
                if (proveedor != null) {
                    // Muestra los detalles del proveedor aquí, por ejemplo, en un Toast
                    Toast.makeText(ListadoProveedoresActivity.this, "Proveedor encontrado: " + proveedor.getNombre(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ListadoProveedoresActivity.this, "Proveedor no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoProveedoresActivity.this, RegistrarProveedorActivity.class);
                startActivity(intent);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoProveedoresActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Cargar y mostrar la lista de proveedores
        loadProveedores();
    }

    private void loadProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        Cursor cursor = adminBD.obtenerProveedores();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.NOMBRE));
                String ruc = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.RUC));
                String direccion = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.DIRECCION));
                String ciudad = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.CIUDAD));

                Proveedor proveedor = new Proveedor(id, nombre, ruc, direccion, ciudad);
                proveedores.add(proveedor);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ProveedorAdapter adapter = new ProveedorAdapter(this, proveedores);
        listViewProveedores.setAdapter(adapter);
    }
}
