package com.example.almasoft.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.model.Proveedor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditarProveedorActivity extends AppCompatActivity {

    private EditText etNombre, etRuc, etDireccion, etCiudad;
    private ImageView imageViewLogo;
    private AdminBD adminBD;
    private int proveedorId;
    private byte[] logoBytes;

    private static final int PICK_LOGO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proveedor);

        // Inicializar vistas
        etNombre = findViewById(R.id.editTextNombre);
        etRuc = findViewById(R.id.editTextRuc);
        etDireccion = findViewById(R.id.editTextDireccion);
        etCiudad = findViewById(R.id.editTextCiudad);
        imageViewLogo = findViewById(R.id.imageViewLogo);

        Button btnActualizar = findViewById(R.id.btnActualizar);
        Button btnCancelar = findViewById(R.id.btnCancelar);
        Button btnCargarLogo = findViewById(R.id.btnCargarLogo);

        adminBD = new AdminBD(this);

        // Obtener datos del intent
        Intent intent = getIntent();
        proveedorId = intent.getIntExtra("proveedorId", -1);
        String nombre = intent.getStringExtra("nombre");
        String ruc = intent.getStringExtra("ruc");
        String direccion = intent.getStringExtra("direccion");
        String ciudad = intent.getStringExtra("ciudad");
        logoBytes = intent.getByteArrayExtra("logo"); // Obtener los bytes del logo

        // Verificar los datos recibidos
        Log.d("EditarProveedorActivity", "Proveedor ID: " + proveedorId);
        Log.d("EditarProveedorActivity", "Nombre: " + nombre);
        Log.d("EditarProveedorActivity", "Logo Bytes Length: " + (logoBytes != null ? logoBytes.length : "null"));

        // Poblar los campos con los datos recibidos
        if (proveedorId != -1) {
            etNombre.setText(nombre);
            etRuc.setText(ruc);
            etDireccion.setText(direccion);
            etCiudad.setText(ciudad);

            if (logoBytes != null && logoBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length);
                if (bitmap != null) {
                    imageViewLogo.setImageBitmap(bitmap);
                } else {
                    Log.e("EditarProveedorActivity", "Error decoding bitmap from byte array");
                    imageViewLogo.setImageResource(R.drawable.user); // Imagen predeterminada si hay error
                }
            } else {
                Log.e("EditarProveedorActivity", "Logo bytes are null or empty");
                imageViewLogo.setImageResource(R.drawable.user); // Imagen predeterminada si no hay logo
            }
        }

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String ruc = etRuc.getText().toString();
                String direccion = etDireccion.getText().toString();
                String ciudad = etCiudad.getText().toString();

                // Actualizar proveedor en la base de datos
                Proveedor proveedor = new Proveedor(proveedorId, nombre, ruc, direccion, ciudad, logoBytes, null); // Pasar logoBytes para actualización
                int filasActualizadas = adminBD.actualizarProveedor(proveedor);

                if (filasActualizadas > 0) {
                    Toast.makeText(EditarProveedorActivity.this, "Proveedor actualizado", Toast.LENGTH_SHORT).show();
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
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnCargarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_LOGO_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (requestCode == PICK_LOGO_REQUEST) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    logoBytes = inputStreamToByteArray(inputStream);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length);
                    if (bitmap != null) {
                        imageViewLogo.setImageBitmap(bitmap);
                    } else {
                        Log.e("EditarProveedorActivity", "Error decoding bitmap from byte array");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] inputStreamToByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
