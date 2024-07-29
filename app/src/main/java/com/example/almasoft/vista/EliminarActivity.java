package com.example.almasoft.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.almasoft.R;
import com.example.almasoft.controlador.UsuarioAdapter;
import com.example.almasoft.database.AdminBD;

public class EliminarActivity extends AppCompatActivity {

    private  ListView listaUsuariosE;
    private EditText UsuarioE;
    private Button btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaUsuariosE = (ListView) findViewById(R.id.list_usuarios_eliminar);
        btnEliminar = (Button) findViewById(R.id.btn_eliminar_usuario);
        UsuarioE = (EditText) findViewById(R.id.ct_id_eliminar);

        AdminBD adminBD = new AdminBD(getApplicationContext());
        listaUsuariosE.setAdapter(new UsuarioAdapter(getApplicationContext(), adminBD.getAllUsuarios(), 0));

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminBD.eliminarUsuario(UsuarioE.getText().toString());
                listaUsuariosE.setAdapter(new UsuarioAdapter(getApplicationContext(), adminBD.getAllUsuarios(), 0));

                Toast.makeText(
                                getApplicationContext(),
                                "El usuario con el id: " +UsuarioE.getText().toString()+ " ha sido eliminado.",
                                Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}