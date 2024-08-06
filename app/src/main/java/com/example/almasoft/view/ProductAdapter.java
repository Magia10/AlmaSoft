package com.example.almasoft.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.model.Proveedor;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Proveedor> proveedores = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Proveedor currentProveedor = proveedores.get(position);

        // Convertir el logo de byte[] a Bitmap
        if (currentProveedor.getLogo() != null && currentProveedor.getLogo().length > 0) {
            Bitmap logoBitmap = BitmapFactory.decodeByteArray(currentProveedor.getLogo(), 0, currentProveedor.getLogo().length);
            holder.ivLogo.setImageBitmap(logoBitmap);
        } else {
            holder.ivLogo.setImageResource(R.drawable.user); // Imagen predeterminada si no hay logo
        }

        // Establecer los datos del proveedor
        holder.tvNombre.setText(currentProveedor.getNombre());
        holder.tvRuc.setText(currentProveedor.getRuc());
        holder.tvDireccion.setText(currentProveedor.getDireccion());
        holder.tvCiudad.setText(currentProveedor.getCiudad());

        holder.buttonEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(currentProveedor);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(currentProveedor);
            }
        });

        holder.buttonShowContract.setOnClickListener(v -> {
            if (listener != null) {
                listener.onShowContractClick(currentProveedor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return proveedores.size();
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
        notifyDataSetChanged();
    }

    public void setProducts(List<Product> products) {

    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView ivLogo;
        private TextView tvNombre, tvRuc, tvDireccion, tvCiudad;
        private Button buttonEdit, buttonDelete, buttonShowContract;

        public ProductHolder(View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.imageLogo);
            tvNombre = itemView.findViewById(R.id.TextNombre);
            tvRuc = itemView.findViewById(R.id.TextRuc);
            tvDireccion = itemView.findViewById(R.id.TextDireccion);
            tvCiudad = itemView.findViewById(R.id.TextCiudad);
            buttonEdit = itemView.findViewById(R.id.btnEditar);
            buttonDelete = itemView.findViewById(R.id.btnEliminar);
            buttonShowContract = itemView.findViewById(R.id.btnMostrarContrato);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(proveedores.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Proveedor proveedor);
        void onEditClick(Proveedor proveedor);
        void onDeleteClick(Proveedor proveedor);
        void onShowContractClick(Proveedor proveedor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
