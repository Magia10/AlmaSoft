package com.example.almasoft.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.database.ProveedorContract;
import com.example.almasoft.model.Proveedor;

import java.util.ArrayList;
import java.util.List;

public class ListadoProveedoresActivity extends AppCompatActivity {

    private AdminBD adminBD;
    private ListView listViewProveedores;
    private EditText etBuscarPorId;
    private ProveedorAdapter proveedorAdapter;
    private List<Proveedor> listaProveedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_proveedores);

        adminBD = new AdminBD(this);

        etBuscarPorId = findViewById(R.id.etBuscar);
        listViewProveedores = findViewById(R.id.listViewProveedores);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = etBuscarPorId.getText().toString();
                if (idStr.isEmpty()) {
                    Toast.makeText(ListadoProveedoresActivity.this, "Ingrese un ID para buscar", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idStr);
                Proveedor proveedor = adminBD.obtenerProveedorPorId(id);
                if (proveedor != null) {
                    List<Proveedor> listaUnProveedor = new ArrayList<>();
                    listaUnProveedor.add(proveedor);
                    proveedorAdapter = new ProveedorAdapter(ListadoProveedoresActivity.this, listaUnProveedor);
                    listViewProveedores.setAdapter(proveedorAdapter);
                } else {
                    Toast.makeText(ListadoProveedoresActivity.this, "Proveedor no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoProveedoresActivity.this, RegistrarProveedorActivity.class);
                startActivity(intent);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoProveedoresActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Cargar y mostrar la lista de proveedores
        loadProveedores();

        // Configurar el click largo para modificar o eliminar proveedor
        listViewProveedores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Proveedor proveedorSeleccionado = (Proveedor) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListadoProveedoresActivity.this);
                builder.setTitle("Opciones")
                        .setItems(new String[]{"Modificar", "Eliminar"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // Modificar proveedor
                                    Intent intent = new Intent(ListadoProveedoresActivity.this, EditarProveedorActivity.class);
                                    intent.putExtra("proveedorId", proveedorSeleccionado.getId());
                                    intent.putExtra("nombre", proveedorSeleccionado.getNombre());
                                    intent.putExtra("ruc", proveedorSeleccionado.getRuc());
                                    intent.putExtra("direccion", proveedorSeleccionado.getDireccion());
                                    intent.putExtra("ciudad", proveedorSeleccionado.getCiudad());
                                    startActivityForResult(intent, 1);
                                } else if (which == 1) {
                                    // Eliminar proveedor
                                    eliminarProveedor(proveedorSeleccionado.getId(), position);
                                }
                            }
                        });
                builder.create().show();
                return true;
            }
        });
    }

    private void loadProveedores() {
        listaProveedores = new ArrayList<>();
        Cursor cursor = adminBD.obtenerProveedores();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.NOMBRE));
                String ruc = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.RUC));
                String direccion = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.DIRECCION));
                String ciudad = cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.CIUDAD));

                Proveedor proveedor = new Proveedor(id, nombre, ruc, direccion, ciudad);
                listaProveedores.add(proveedor);
            } while (cursor.moveToNext());
        }
        cursor.close();

        proveedorAdapter = new ProveedorAdapter(this, listaProveedores);
        listViewProveedores.setAdapter(proveedorAdapter);
    }

    private void eliminarProveedor(final int id, final int position) {
        new AlertDialog.Builder(this)
                .setMessage("¿Está seguro de que desea eliminar este proveedor?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adminBD.eliminarProveedor(id);
                        listaProveedores.remove(position);
                        proveedorAdapter.notifyDataSetChanged();
                        Toast.makeText(ListadoProveedoresActivity.this, "Proveedor eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Volver a cargar la lista de proveedores
            loadProveedores();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProveedores(); // Recargar la lista al volver a la actividad
    }
}
