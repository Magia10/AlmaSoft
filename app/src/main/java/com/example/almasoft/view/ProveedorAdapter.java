package com.example.almasoft.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.almasoft.R;
import com.example.almasoft.model.Proveedor;

import java.util.List;

public class ProveedorAdapter extends BaseAdapter {
    private Context context;
    private List<Proveedor> proveedores;

    public ProveedorAdapter(Context context, List<Proveedor> proveedores) {
        this.context = context;
        this.proveedores = proveedores;
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
        /*
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_proveedor, parent, false);
        }

        Proveedor proveedor = proveedores.get(position);

        TextView tvNombre = convertView.findViewById(R.id.TextNombre);
        TextView tvRuc = convertView.findViewById(R.id.TextRuc);
        TextView tvDireccion = convertView.findViewById(R.id.TextDireccion);
        TextView tvCiudad = convertView.findViewById(R.id.TextCiudad);

        tvNombre.setText(proveedor.getNombre());
        tvRuc.setText(proveedor.getRuc());
        tvDireccion.setText(proveedor.getDireccion());
        tvCiudad.setText(proveedor.getCiudad());
        */
        return convertView;
    }
}
