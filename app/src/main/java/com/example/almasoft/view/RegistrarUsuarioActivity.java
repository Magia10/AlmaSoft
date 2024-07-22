package com.example.almasoft.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.model.Usuario;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    private Button btnRegistrar;
    private EditText txt_Nombre, txt_ApellidoP, txt_ApellidoM, txt_CodUsuario, txt_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        // Referencias a los elementos de la interfaz
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txt_Nombre = findViewById(R.id.txtNombre);
        txt_ApellidoP = findViewById(R.id.txtApellidoP);
        txt_ApellidoM = findViewById(R.id.txtApellidoM);
        txt_CodUsuario = findViewById(R.id.txtUsuario);
        txt_Password = findViewById(R.id.txtPassword);

        // Configuración del botón registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validación de campos
                if (validarCampos()) {
                    // Obtener instancia de la base de datos
                    AdminBD adminBD = new AdminBD(getApplicationContext());
                    SQLiteDatabase database = adminBD.getWritableDatabase();

                    // Guardar usuario en la base de datos
                    long resultado = adminBD.guardarUsuario(new Usuario(
                            txt_Nombre.getText().toString(),
                            txt_ApellidoP.getText().toString(),
                            txt_ApellidoM.getText().toString(),
                            txt_CodUsuario.getText().toString(),
                            txt_Password.getText().toString()));

                    // Comprobar si se insertó correctamente
                    if (resultado != -1) {
                        Toast.makeText(getApplicationContext(), "Se agregó correctamente el registro", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al guardar el usuario", Toast.LENGTH_SHORT).show();
                    }

                    // Cerrar la base de datos
                    adminBD.close();
                }
            }
        });
    }

    // Método para limpiar los campos después de registrar un usuario
    private void limpiarCampos() {
        txt_Nombre.setText("");
        txt_ApellidoP.setText("");
        txt_ApellidoM.setText("");
        txt_CodUsuario.setText("");
        txt_Password.setText("");
        txt_Nombre.requestFocus();
    }

    // Método para validar que todos los campos estén llenos
    private boolean validarCampos() {
        String nombre = txt_Nombre.getText().toString().trim();
        String apellidoP = txt_ApellidoP.getText().toString().trim();
        String apellidoM = txt_ApellidoM.getText().toString().trim();
        String codUsuario = txt_CodUsuario.getText().toString().trim();
        String password = txt_Password.getText().toString().trim();

        if (nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || codUsuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Método para regresar al LoginActivity
    public void enviarLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish(); // Terminar la actividad actual para evitar volver atrás
    }
}
