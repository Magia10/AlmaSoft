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
import com.example.almasoft.model.Proveedor;

public class EditarProveedorActivity extends AppCompatActivity {

    private EditText etNombre, etRuc, etDireccion, etCiudad;
    private AdminBD adminBD;
    private int proveedorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proveedor);

        // Inicializar vistas
        etNombre = findViewById(R.id.editTextNombre);
        etRuc = findViewById(R.id.editTextRuc);
        etDireccion = findViewById(R.id.editTextDireccion);
        etCiudad = findViewById(R.id.editTextCiudad);

        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        adminBD = new AdminBD(this);

        // Obtener datos del intent
        Intent intent = getIntent();
        proveedorId = intent.getIntExtra("proveedorId", -1);
        String nombre = intent.getStringExtra("nombre");
        String ruc = intent.getStringExtra("ruc");
        String direccion = intent.getStringExtra("direccion");
        String ciudad = intent.getStringExtra("ciudad");

        // Poblar los campos con los datos recibidos
        if (proveedorId != -1) {
            etNombre.setText(nombre);
            etRuc.setText(ruc);
            etDireccion.setText(direccion);
            etCiudad.setText(ciudad);
        }

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String ruc = etRuc.getText().toString();
                String direccion = etDireccion.getText().toString();
                String ciudad = etCiudad.getText().toString();

                // Crear objeto Proveedor con datos actualizados
                Proveedor proveedor = new Proveedor(proveedorId, nombre, ruc, direccion, ciudad);

                // Actualizar proveedor en la base de datos
                int filasActualizadas = adminBD.actualizarProveedor(proveedor);

                if (filasActualizadas > 0) {
                    Toast.makeText(EditarProveedorActivity.this, "Proveedor actualizado", Toast.LENGTH_SHORT).show();
                    // Establecer resultado y finalizar la actividad
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditarProveedorActivity.this, "Error al actualizar proveedor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Establecer resultado como cancelado y finalizar la actividad
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
