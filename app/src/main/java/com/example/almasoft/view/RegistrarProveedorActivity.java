package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;

public class RegistrarProveedorActivity extends AppCompatActivity {

    private EditText etNombre, etRuc, etDireccion, etCiudad;
    private AdminBD adminBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_proveedor);

        adminBD = new AdminBD(this);

        etNombre = findViewById(R.id.txtNombre);
        etRuc = findViewById(R.id.txtRuc);
        etDireccion = findViewById(R.id.txtDireccion);
        etCiudad = findViewById(R.id.txtCiudad);

        Button btnRegresar = findViewById(R.id.btnRegresar);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarProveedorActivity.this, ListadoProveedoresActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String ruc = etRuc.getText().toString();
                String direccion = etDireccion.getText().toString();
                String ciudad = etCiudad.getText().toString();

                if (nombre.isEmpty() || ruc.isEmpty() || direccion.isEmpty() || ciudad.isEmpty()) {
                    Toast.makeText(RegistrarProveedorActivity.this, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show();
                    return;
                }

                long id = adminBD.guardarProveedor(nombre, ruc, direccion, ciudad);
                if (id > 0) {
                    Toast.makeText(RegistrarProveedorActivity.this, "Proveedor registrado con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrarProveedorActivity.this, ListadoProveedoresActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrarProveedorActivity.this, "Error al registrar proveedor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
