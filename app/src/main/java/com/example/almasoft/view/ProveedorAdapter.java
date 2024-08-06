package com.example.almasoft.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.example.almasoft.R;
import com.example.almasoft.database.AdminBD;
import com.example.almasoft.model.Proveedor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        ImageView ivLogo = convertView.findViewById(R.id.imageLogo);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar);
        Button btnMostrarContrato = convertView.findViewById(R.id.btnMostrarContrato);

        tvNombre.setText(proveedor.getNombre());
        tvRuc.setText(proveedor.getRuc());
        tvDireccion.setText(proveedor.getDireccion());
        tvCiudad.setText(proveedor.getCiudad());

        // Convertir el logo de byte[] a Bitmap
        if (proveedor.getLogo() != null && proveedor.getLogo().length > 0) {
            Bitmap logoBitmap = BitmapFactory.decodeByteArray(proveedor.getLogo(), 0, proveedor.getLogo().length);
            ivLogo.setImageBitmap(logoBitmap);
        } else {
            ivLogo.setImageResource(R.drawable.user); // Imagen predeterminada si no hay logo
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarProveedorActivity.class);
            intent.putExtra("proveedorId", proveedor.getId());
            intent.putExtra("nombre", proveedor.getNombre());
            intent.putExtra("ruc", proveedor.getRuc());
            intent.putExtra("direccion", proveedor.getDireccion());
            intent.putExtra("ciudad", proveedor.getCiudad());
            intent.putExtra("logo", proveedor.getLogo()); // Asegúrate de que esto sea un byte array
            intent.putExtra("contrato", proveedor.getContrato()); // Asegúrate de que esto también sea un byte array
            context.startActivity(intent);
        });

        btnEliminar.setOnClickListener(v -> eliminarProveedor(proveedor.getId(), position));

        btnMostrarContrato.setOnClickListener(v -> mostrarContrato(proveedor.getContrato()));

        return convertView;
    }

    private void eliminarProveedor(final int id, final int position) {
        new AlertDialog.Builder(context)
                .setMessage("¿Está seguro de que desea eliminar este proveedor?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    adminBD.eliminarProveedor(id);
                    proveedores.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Proveedor eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void mostrarContrato(byte[] contratoFile) {
        if (contratoFile != null && contratoFile.length > 0) {
            try {
                File file = File.createTempFile("contrato", ".pdf", context.getCacheDir());
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(contratoFile);
                }

                Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(fileUri, "application/pdf");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al mostrar el contrato", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No hay contrato disponible", Toast.LENGTH_SHORT).show();
        }
    }
}
