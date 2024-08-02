package com.example.almasoft.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.almasoft.R;
import com.example.almasoft.model.Movement;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.MovementViewModel;
import com.example.almasoft.viewmodel.ProductViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.MovementHolder> {
    private List<Product> products;
    private Context context;
    private ProductViewModel productViewModel;
    private MovementViewModel movementViewModel;

    @NonNull
    @Override
    public MovementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movement_item, parent, false);
        return new MovementHolder(itemView);
    }

    public MovementAdapter(Context context, ArrayList<Product> products,
                           MovementViewModel movementViewModel, ProductViewModel productViewModel){
        this.context = context;
        this.products = products;
        this.movementViewModel = movementViewModel;
        this.productViewModel = productViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull MovementHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.tvName.setText(currentProduct.getName());
        holder.tvSalePrice.setText(String.valueOf(currentProduct.getSalePrice()));
        holder.tvPurchasePrice.setText(String.valueOf(currentProduct.getPurchasePrice()));
        holder.tvBrand.setText(currentProduct.getBrand());
        holder.tvLocation.setText(currentProduct.getAddress());
        holder.tvQuantity.setText(String.valueOf(currentProduct.getQuantity()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductDetailDialog(currentProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MovementHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvSalePrice,tvPurchasePrice, tvBrand, tvLocation, tvQuantity;

        public MovementHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSalePrice = itemView.findViewById(R.id.tvSalePrice);
            tvPurchasePrice = itemView.findViewById(R.id.tvPurchasePrice);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Product> newMovements) {
        products = newMovements;
        notifyDataSetChanged();
    }

    private void showProductDetailDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_product_detail, null);
        builder.setView(dialogView);

        TextView tvProductName = dialogView.findViewById(R.id.tvProductName);
        TextView tvQuantity = dialogView.findViewById(R.id.tvProductStock);
        EditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        Button btnIngreso = dialogView.findViewById(R.id.btnIngreso);
        Button btnSalida = dialogView.findViewById(R.id.btnSalida);

        tvProductName.setText(product.getName());
        tvQuantity.setText(String.valueOf(product.getQuantity()));

        AlertDialog dialog = builder.create();

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityStr = etQuantity.getText().toString();
                int quantity = Integer.parseInt(quantityStr);
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(calendar.getTime());
                int updateStock = product.getQuantity() + quantity;
                Movement movement = new Movement(1,product.getId(),quantity,formattedDate);
                movementViewModel.insert(movement);
                product.setQuantity(updateStock);
                productViewModel.update(product);
                dialog.dismiss();
            }
        });

        btnSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityStr = etQuantity.getText().toString();
                int quantity = Integer.parseInt(quantityStr);
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(calendar.getTime());
                int updateStock = product.getQuantity() - quantity;
                Movement movement = new Movement(2,product.getId(),quantity,formattedDate);
                movementViewModel.insert(movement);
                product.setQuantity(updateStock);
                productViewModel.update(product);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

