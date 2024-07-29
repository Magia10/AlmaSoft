package com.example.almasoft.vista;

import android.content.Intent;
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

public class ActualizarActivity extends AppCompatActivity {
    private EditText id_Actualizar,txtNombre,txtApellidoP,txtApellidoM,txtUsuario,txtPassword;
    private Button btnActualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        id_Actualizar = (EditText) findViewById(R.id.txtID_Act);
        txtNombre = (EditText) findViewById(R.id.txtNombre_Act);
        txtApellidoP= (EditText) findViewById(R.id.txtApellidoP_Act);
        txtApellidoM = (EditText) findViewById(R.id.txtApellidoM_Act);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario_Login);
        txtPassword = (EditText) findViewById(R.id.txtPassword_Act);
        btnActualizar =(Button) findViewById(R.id.btnActualizar_Act);

        AdminBD adminBD = new AdminBD(getApplicationContext());

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminBD.actualizarUsuario(id_Actualizar.getText().toString(),
                        txtNombre.getText().toString(),
                        txtApellidoP.getText().toString(),
                        txtApellidoM.getText().toString(),
                        txtUsuario.getText().toString(),
                        txtPassword.getText().toString());
                Toast.makeText(getApplicationContext(), "Se ha actualizado el registro!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Metodo para el btnRegresar
    public void enviarGestionUsuario(View view){
        Intent i = new Intent(this, GestionUsuarioActivity.class);
        startActivity(i);
    }
}