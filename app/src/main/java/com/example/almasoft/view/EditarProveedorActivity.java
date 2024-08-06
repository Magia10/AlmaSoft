package com.example.almasoft.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.model.Proveedor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditarProveedorActivity extends AppCompatActivity {

    private EditText etNombre, etRuc, etDireccion, etCiudad;
    private ImageView imageViewLogo;
    private Button btnActualizar, btnCancelar, btnCargarLogo, btnCargarContrato, btnMostrarContrato;
    private AdminBD adminBD;
    private int proveedorId;
    private byte[] logoBytes;
    private byte[] contratoBytes;

    private static final int PICK_LOGO_REQUEST = 1;
    private static final int PICK_CONTRATO_REQUEST = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proveedor);

        // Solicitar permisos si es necesario
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        // Inicializar vistas
        etNombre = findViewById(R.id.editTextNombre);
        etRuc = findViewById(R.id.editTextRuc);
        etDireccion = findViewById(R.id.editTextDireccion);
        etCiudad = findViewById(R.id.editTextCiudad);
        imageViewLogo = findViewById(R.id.imageViewLogo);

        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCargarLogo = findViewById(R.id.btnCargarLogo);
        btnCargarContrato = findViewById(R.id.btnCargarContrato);
        btnMostrarContrato = findViewById(R.id.btnVerContrato);

        adminBD = new AdminBD(this);

        // Obtener datos del intent
        Intent intent = getIntent();
        proveedorId = intent.getIntExtra("proveedorId", -1);
        String nombre = intent.getStringExtra("nombre");
        String ruc = intent.getStringExtra("ruc");
        String direccion = intent.getStringExtra("direccion");
        String ciudad = intent.getStringExtra("ciudad");
        logoBytes = intent.getByteArrayExtra("logo"); // Obtener los bytes del logo
        contratoBytes = intent.getByteArrayExtra("contrato"); // Obtener los bytes del contrato

        // Verificar los datos recibidos
        Log.d("EditarProveedorActivity", "Proveedor ID: " + proveedorId);
        Log.d("EditarProveedorActivity", "Nombre: " + nombre);
        Log.d("EditarProveedorActivity", "Logo Bytes Length: " + (logoBytes != null ? logoBytes.length : "null"));
        Log.d("EditarProveedorActivity", "Contrato Bytes Length: " + (contratoBytes != null ? contratoBytes.length : "null"));

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

            if (contratoBytes != null && contratoBytes.length > 0) {
                btnMostrarContrato.setVisibility(View.VISIBLE); // Mostrar botón si hay contrato
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
                Proveedor proveedor = new Proveedor(proveedorId, nombre, ruc, direccion, ciudad, logoBytes, contratoBytes); // Pasar logoBytes y contratoBytes para actualización
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccionar logo"), PICK_LOGO_REQUEST);
            }
        });

        btnCargarContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Seleccionar contrato"), PICK_CONTRATO_REQUEST);
            }
        });

        btnMostrarContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarContrato(contratoBytes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_LOGO_REQUEST) {
                Uri uri = data.getData();
                try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                    if (inputStream != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        if (bitmap != null) {
                            imageViewLogo.setImageBitmap(bitmap);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            logoBytes = baos.toByteArray();
                        } else {
                            Log.e("EditarProveedorActivity", "Error decoding bitmap from InputStream");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al cargar el logo", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == PICK_CONTRATO_REQUEST) {
                Uri uri = data.getData();
                try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                    if (inputStream != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, length);
                        }
                        contratoBytes = baos.toByteArray();
                        btnMostrarContrato.setVisibility(View.VISIBLE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al cargar el contrato", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void mostrarContrato(byte[] contratoFile) {
        if (contratoFile != null && contratoFile.length > 0) {
            try {
                File file = File.createTempFile("contrato", ".pdf", getCacheDir());
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(contratoFile);
                }

                Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(fileUri, "application/pdf");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al mostrar el contrato", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No hay contrato disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                Toast.makeText(this, "Permiso de almacenamiento requerido", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
