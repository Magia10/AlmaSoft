package com.example.almasoft.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RegistrarProveedorActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_PDF_REQUEST = 2;

    private EditText etNombre, etRuc, etDireccion, etCiudad;
    private ImageView ivLogo;
    private AdminBD adminBD;
    private byte[] logoImage = null;
    private byte[] contratoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_proveedor);

        adminBD = new AdminBD(this);

        etNombre = findViewById(R.id.txtNombre);
        etRuc = findViewById(R.id.txtRuc);
        etDireccion = findViewById(R.id.txtDireccion);
        etCiudad = findViewById(R.id.txtCiudad);
        ivLogo = findViewById(R.id.imageViewLogo);

        Button btnRegresar = findViewById(R.id.btnRegresar);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnCargarLogo = findViewById(R.id.btnCargarLogo);
        Button btnCargarContrato = findViewById(R.id.btnGuardarContrato);
        Button btnMostrarContrato = findViewById(R.id.btnMostrarContrato);

        btnRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarProveedorActivity.this, ListadoProveedoresActivity.class);
            startActivity(intent);
        });

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String ruc = etRuc.getText().toString();
            String direccion = etDireccion.getText().toString();
            String ciudad = etCiudad.getText().toString();

            if (nombre.isEmpty() || ruc.isEmpty() || direccion.isEmpty() || ciudad.isEmpty()) {
                Toast.makeText(RegistrarProveedorActivity.this, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = adminBD.guardarProveedor(nombre, ruc, direccion, ciudad, logoImage, contratoFile);
            if (id > 0) {
                Toast.makeText(RegistrarProveedorActivity.this, "Proveedor registrado con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistrarProveedorActivity.this, ListadoProveedoresActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegistrarProveedorActivity.this, "Error al registrar proveedor", Toast.LENGTH_SHORT).show();
            }
        });

        btnCargarLogo.setOnClickListener(v -> openFileChooser(PICK_IMAGE_REQUEST));

        btnCargarContrato.setOnClickListener(v -> openFileChooser(PICK_PDF_REQUEST));

        btnMostrarContrato.setOnClickListener(v -> {
            if (contratoFile != null) {
                // Implementar la visualización del contrato si es necesario
                Toast.makeText(RegistrarProveedorActivity.this, "Contrato cargado y listo para mostrar", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegistrarProveedorActivity.this, "No se ha cargado ningún contrato", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType(requestCode == PICK_IMAGE_REQUEST ? "image/*" : "application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            try {
                if (requestCode == PICK_IMAGE_REQUEST) {
                    // Cargar y mostrar la imagen
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    ivLogo.setImageBitmap(bitmap);
                    logoImage = getBytesFromBitmap(bitmap);
                } else if (requestCode == PICK_PDF_REQUEST) {
                    // Cargar el archivo PDF
                    contratoFile = getBytesFromUri(fileUri);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        File file = new File(uri.getPath());
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        fis.close();
        return baos.toByteArray();
    }
}
