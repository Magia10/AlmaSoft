package com.example.almasoft.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.almasoft.R;
import com.example.almasoft.model.Movement;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.MovementViewModel;
import com.example.almasoft.viewmodel.ProductViewModel;
import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    private List<Movement> movementList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    public ReportAdapter(List<Product> products, List<Movement> movements) {
        this.productList = products;
        this.movementList = movements;
    }

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new ReportHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        Movement currentMovement = movementList.get(position);
        String action = currentMovement.getAction() == 1 ? "Ingreso" : "Salida";
        String productName = "-";
        holder.tvAction.setText(action);
        for (Product product : productList) {
            if (product.getId() == currentMovement.getProductId()) {
                productName = product.getName();
                break;
            }
        }
        holder.tvProductName.setText(productName);
        holder.tvQuantity.setText(String.valueOf(currentMovement.getQuantity()));
        holder.tvDate.setText(currentMovement.getDate());
    }

    @Override
    public int getItemCount() {
        return movementList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovements(List<Movement> movements) {
        this.movementList = movements != null ? movements : new ArrayList<>();
        notifyDataSetChanged();
    }

    class ReportHolder extends RecyclerView.ViewHolder {
        private TextView tvAction, tvProductName, tvQuantity, tvDate;

        public ReportHolder(View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tvAction);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
