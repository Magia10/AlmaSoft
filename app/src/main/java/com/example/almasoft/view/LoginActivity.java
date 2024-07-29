package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    AdminBD adminBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        adminBD = new AdminBD(this);

        editTextUsername = findViewById(R.id.txtUsuario);
        editTextPassword = findViewById(R.id.txtClave);
    }

    public void ingresar(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (adminBD.validarUsuario(username, password)) {
            // Usuario válido, procede al HomeActivity
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        } else {
            // Mostrar mensaje de error
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviarRegistrarse(View view) {
        Intent i = new Intent(this, RegistrarUsuarioActivity.class);
        startActivity(i);
    }
}
