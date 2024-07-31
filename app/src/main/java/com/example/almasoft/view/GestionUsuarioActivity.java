package com.example.almasoft.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.almasoft.R;

public class GestionUsuarioActivity extends AppCompatActivity {

    private Button btnRegistrar, btnConsultar, btnEliminar, btnActualizar, btnRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRegistrar = findViewById(R.id.btnActualizar_Act);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnRegresar = findViewById(R.id.btnRegresar_Act);
    }

    //Metodo para el btnConsulta
    public void enviarConsulta(View view){
        Intent i = new Intent(this, ConsultarActivity.class);
        startActivity(i);
    }

    //Metodo para el btnEliminar
    public void enviarEliminar(View view){
        Intent i = new Intent(this, EliminarActivity.class);
        startActivity(i);
    }

    //Metodo para el btnActualizar
    public void enviarActualizar(View view){
        Intent i = new Intent(this, ActualizarActivity.class);
        startActivity(i);
    }
    //Metodo para el btnRegistrar
    public void enviarRegistarUsuario(View view){
        Intent i = new Intent(this, RegistrarUsuarioActivity.class);
        startActivity(i);
    }

    //Metodo para el btnRegresar
    public void enviarLogin(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}