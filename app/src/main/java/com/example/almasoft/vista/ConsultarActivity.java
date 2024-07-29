package com.example.almasoft.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.almasoft.R;
import com.example.almasoft.controlador.UsuarioAdapter;
import com.example.almasoft.database.AdminBD;

public class ConsultarActivity extends AppCompatActivity {
    private ListView listaUsuarios;
    private Button btnConsultar;
    private EditText etID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaUsuarios = (ListView) findViewById(R.id.list_usuarios);
        btnConsultar = (Button) findViewById(R.id.btn_consultar_consultar);
        etID = (EditText) findViewById(R.id.ct_id_consultar);

        AdminBD adminBD = new AdminBD(getApplicationContext());
        listaUsuarios.setAdapter(new UsuarioAdapter(getApplicationContext(), adminBD.getAllUsuarios(), 0));

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaUsuarios.setAdapter(new UsuarioAdapter(
                        getApplicationContext(),
                        adminBD.getUsuarioById(etID.getText().toString()),
                        0));
            }
        });

    }
}