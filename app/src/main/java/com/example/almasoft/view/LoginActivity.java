package com.example.almasoft.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;

public class LoginActivity extends AppCompatActivity {
    private TextView lblRegistrarse;
    private EditText txtUsuarioL,txtClaveL;
    private Button btnIngresarL;
    private String usuarioL, passwordL;

    private AdminBD dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ejecucionBD();
        lblRegistrarse = findViewById(R.id.lblRegistrarse);
        txtUsuarioL = findViewById(R.id.txtUsuario_Login);
        txtClaveL = findViewById(R.id.txtClave_Login);
        btnIngresarL = findViewById(R.id.btnIngresar);
        dbHelper = new AdminBD(this);

        btnIngresarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioL = txtUsuarioL.getText().toString();
                passwordL = txtClaveL.getText().toString();

               if(!usuarioL.isEmpty() && !passwordL.isEmpty()){
                    if (dbHelper.validateUser(usuarioL,passwordL)){
                        Toast.makeText(getApplicationContext(), "Login Exitoso!", Toast.LENGTH_SHORT).show();
                        enviarMain();
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña no existe!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //Ejecucion de BD
    public void ejecucionBD(){
        AdminBD adminBD = new AdminBD(LoginActivity.this);
        SQLiteDatabase database = adminBD.getWritableDatabase();
    }

    //Metodo para el lblRegistrarse
    public void enviarGestionUsuario(View view){
        Intent i = new Intent(this, GestionUsuarioActivity.class);
        startActivity(i);
    }

    public void enviarMain(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}