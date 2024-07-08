package com.example.almasoft.vista;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
    TextView lblRegistrarse;
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
    }
    //Ejecucion de BD
    public void ejecucionBD(){
        AdminBD adminBD = new AdminBD(LoginActivity.this);
        SQLiteDatabase database = adminBD.getWritableDatabase();
    }

    //Metodo para el lblRegistrarse
    public void enviarRegistrarse(View view){
        Intent i = new Intent(this, RegistrarUsuarioActivity.class);
        startActivity(i);
    }
}