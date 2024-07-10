package com.example.almasoft.vista;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.modelo.Usuario;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    private Button btnRegistrar;
    private EditText txt_Nombre,txt_ApellidoP,txt_ApellidoM,txt_CodUsuario,txt_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txt_Nombre = findViewById(R.id.txtNombre);
        txt_ApellidoP = findViewById(R.id.txtApellidoP);
        txt_ApellidoM = findViewById(R.id.txtApellidoM);
        txt_CodUsuario = findViewById(R.id.txtUsuario);
        txt_Password = findViewById(R.id.txtPassword);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminBD adminBD = new AdminBD(getApplicationContext());
                SQLiteDatabase database = adminBD.getWritableDatabase();
                adminBD.guardarUsuario(database, new Usuario(
                        txt_Nombre.getText().toString(),
                        txt_ApellidoP.getText().toString(),
                        txt_ApellidoM.getText().toString(),
                        txt_CodUsuario.getText().toString(),
                        txt_Password.getText().toString()));

                Toast.makeText(getApplicationContext(), "Se agrego correctamente el registro!", Toast.LENGTH_SHORT).show();
            }
        });



    }

    //Metodo para el btnRegresar del registro a usuario
    public void enviarLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void insertarUsuario(View view){
        AdminBD adminBD = new AdminBD(getApplicationContext());
        SQLiteDatabase database = adminBD.getWritableDatabase();
        adminBD.guardarUsuario(database, new Usuario(
                txt_Nombre.getText().toString(),
                txt_ApellidoP.getText().toString(),
                txt_ApellidoM.getText().toString(),
                txt_CodUsuario.getText().toString(),
                txt_Password.getText().toString()));

        Toast.makeText(getApplicationContext(), "Se agrego correctamente el registro!", Toast.LENGTH_SHORT).show();
    }


}