package com.example.almasoft.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.example.almasoft.R;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Product> products = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;
    private ProductViewModel productViewModel;

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }
    public ProductAdapter(Context context){
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.tvName.setText(currentProduct.getName());
        holder.tvSalePrice.setText(String.valueOf(currentProduct.getSalePrice()));
        holder.tvPurchasePrice.setText(String.valueOf(currentProduct.getPurchasePrice()));
        holder.tvBrand.setText(currentProduct.getBrand());
        holder.tvLocation.setText(currentProduct.getAddress());
        holder.tvQuantity.setText(String.valueOf(currentProduct.getQuantity()));

        productViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductViewModel.class);

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductCreateActivity.class);
                intent.putExtra("productId", 1);
                context.startActivity(intent);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProduct.setState(0);
                productViewModel.update(currentProduct);
            }
        });

        holder.btnInbound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductInboundActivity.class);
                intent.putExtra("productId", currentProduct.getId());
                context.startActivity(intent);
            }
        });

        holder.btnOutbound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductOutboundActivity.class);
                intent.putExtra("productId", currentProduct.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private ImageButton buttonEdit, buttonDelete, btnInbound, btnOutbound;

        private TextView tvName, tvSalePrice,tvPurchasePrice, tvBrand, tvLocation, tvQuantity;

        public ProductHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSalePrice = itemView.findViewById(R.id.tvSalePrice);
            tvPurchasePrice = itemView.findViewById(R.id.tvPurchasePrice);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);

            buttonEdit = itemView.findViewById(R.id.btnEdit);
            buttonDelete = itemView.findViewById(R.id.btnDelete);
            btnInbound = itemView.findViewById(R.id.btnInbound);
            btnOutbound = itemView.findViewById(R.id.btnOutbound);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(products.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

