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

    private String nombre,apellidoP,apellidoM,codUsuario,password;
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
        btnRegistrar = findViewById(R.id.btnActualizar_Act);
        txt_Nombre = findViewById(R.id.txtNombre_Act);
        txt_ApellidoP = findViewById(R.id.txtApellidoP_Act);
        txt_ApellidoM = findViewById(R.id.txtApellidoM_Act);
        txt_CodUsuario = findViewById(R.id.txtUsuario_Login);
        txt_Password = findViewById(R.id.txtPassword_Act);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminBD adminBD = new AdminBD(getApplicationContext());
                SQLiteDatabase database = adminBD.getWritableDatabase();
                obtenerDatos();
                if(!nombre.isEmpty() && !apellidoP.isEmpty() && !apellidoM.isEmpty() && !codUsuario.isEmpty() && !password.isEmpty()) {
                    adminBD.guardarUsuario(database, new Usuario(
                            txt_Nombre.getText().toString(),
                            txt_ApellidoP.getText().toString(),
                            txt_ApellidoM.getText().toString(),
                            txt_CodUsuario.getText().toString(),
                            txt_Password.getText().toString()));

                    Toast.makeText(getApplicationContext(), "Se agrego correctamente el registro!", Toast.LENGTH_SHORT).show();
                    limpiar();
                }else{
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    //Metodo para el btnRegresar del registro a usuario
    public void enviarLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void limpiar(){
        txt_Nombre.setText("");
        txt_ApellidoP.setText("");
        txt_ApellidoM.setText("");
        txt_CodUsuario.setText("");
        txt_Password.setText("");
        txt_Nombre.requestFocus();
    }

    public void obtenerDatos(){
        nombre = txt_Nombre.getText().toString();
        apellidoP = txt_ApellidoP.getText().toString();
        apellidoM= txt_ApellidoM.getText().toString();
        codUsuario=txt_CodUsuario.getText().toString();
        password= txt_Password.getText().toString();
    }

    //Metodo para el btnGestion
    public void enviarGestion(View view){
        Intent i = new Intent(this, GestionUsuarioActivity.class);
        startActivity(i);
    }
}