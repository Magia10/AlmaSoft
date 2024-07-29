package com.example.almasoft.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.model.Proveedor;

import java.util.List;

public class ProveedorAdapter extends BaseAdapter {
    private Context context;
    private List<Proveedor> proveedores;
    private AdminBD adminBD;

    public ProveedorAdapter(Context context, List<Proveedor> proveedores) {
        this.context = context;
        this.proveedores = proveedores;
        this.adminBD = new AdminBD(context);
    }

    @Override
    public int getCount() {
        return proveedores.size();
    }

    @Override
    public Object getItem(int position) {
        return proveedores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return proveedores.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_proveedor, parent, false);
        }

        Proveedor proveedor = proveedores.get(position);

        TextView tvNombre = convertView.findViewById(R.id.TextNombre);
        TextView tvRuc = convertView.findViewById(R.id.TextRuc);
        TextView tvDireccion = convertView.findViewById(R.id.TextDireccion);
        TextView tvCiudad = convertView.findViewById(R.id.TextCiudad);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar);

        tvNombre.setText(proveedor.getNombre());
        tvRuc.setText(proveedor.getRuc());
        tvDireccion.setText(proveedor.getDireccion());
        tvCiudad.setText(proveedor.getCiudad());

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarProveedorActivity.class);
                intent.putExtra("proveedorId", proveedor.getId()); // Corregido para que coincida con el intent extra usado en EditarProveedorActivity
                intent.putExtra("nombre", proveedor.getNombre());
                intent.putExtra("ruc", proveedor.getRuc());
                intent.putExtra("direccion", proveedor.getDireccion());
                intent.putExtra("ciudad", proveedor.getCiudad());
                context.startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProveedor(proveedor.getId(), position);
            }
        });

        return convertView;
    }

    private void eliminarProveedor(final int id, final int position) {
        new AlertDialog.Builder(context)
                .setMessage("¿Está seguro de que desea eliminar este proveedor?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adminBD.eliminarProveedor(id);
                        proveedores.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Proveedor eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
