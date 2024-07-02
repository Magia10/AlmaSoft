package com.example.almasoft.vista;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;

public class Login extends AppCompatActivity {

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

        //Ejecucion de BD
        AdminBD adminBD = new AdminBD(Login.this);
        SQLiteDatabase database = adminBD.getWritableDatabase();

        if(database!=null){
            Toast.makeText(this,"Se ha creado la BD correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"No se ha creado la BD correctamente",Toast.LENGTH_SHORT).show();
        }
    }
}