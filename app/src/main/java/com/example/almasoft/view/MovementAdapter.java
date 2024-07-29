package com.example.almasoft.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.example.almasoft.R;
import com.example.almasoft.model.Movement;
import com.example.almasoft.model.Product;
import com.example.almasoft.viewmodel.ProductViewModel;
import java.util.ArrayList;
import java.util.List;

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.MovementHolder> {
    private List<Movement> movements = new ArrayList<>();
    private Context context;
    private ProductViewModel productViewModel;

    public MovementAdapter(Context context, List<Movement> movements){
        this.context = context;
        this.movements = movements;
    }

    @NonNull
    @Override
    public MovementAdapter.MovementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movement_item, parent, false);
        return new MovementAdapter.MovementHolder(itemView);
    }

    class MovementHolder extends RecyclerView.ViewHolder {

        private TextView tvAction, tvProductName,tvQuantity, tvDate;

        public MovementHolder(View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tvAction);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovementAdapter.MovementHolder holder, int position) {
        Movement movement = movements.get(position);

        productViewModel  = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductViewModel.class);

        String action = movement.getAction() == 1 ? "Ingreso" : "Salida";
        holder.tvAction.setText(action);

        productViewModel.getProductById(movement.getProductId()).observe((LifecycleOwner) context, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    holder.tvProductName.setText(product.getName());
                }
            }
        });

        holder.tvQuantity.setText(String.valueOf(movement.getQuantity()));
        holder.tvDate.setText(movement.getDate());
    }

    @Override
    public int getItemCount() {
        return movements.size();
    }

    public void updateList(List<Movement> newMovements) {
        movements = newMovements;
        notifyDataSetChanged();
    }

    class MovementViewHolder extends RecyclerView.ViewHolder {
        // Define your ViewHolder

        public MovementViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Movement movement) {
            // Bind data to your views
        }
    }
}
