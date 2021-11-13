package vn.hust.edu.qrcodeapp.adapter;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import vn.hust.edu.qrcodeapp.ItemClickListenerRecycle;
import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.model.Product;

public class HotProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Product> listItem;
    ItemClickListenerRecycle itemClickListenerRecycle;

    public HotProductAdapter(List<Product> listItem, ItemClickListenerRecycle itemClickListenerRecycle) {
        this.listItem = listItem;
        this.itemClickListenerRecycle = itemClickListenerRecycle;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_product_item, parent, false);
        return new ProductViewHolder(view, itemClickListenerRecycle);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        Product item = listItem.get(position);
        viewHolder.txtName.setText(item.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(String.format("%s đ", decimalFormat.format(item.getPrice())));
        Picasso.get().load("http://192.168.85.116:8080/" + item.getImageUrl())
                .placeholder(R.drawable.background)
                .error(R.drawable.logo).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView txtName;
        TextView txtPrice;
        ItemClickListenerRecycle itemClickListenerRecycle;

        public ProductViewHolder(@NonNull final View itemView, ItemClickListenerRecycle itemClickListenerRecycle) {
            super(itemView);
            image = itemView.findViewById(R.id.image_product);
            txtName = itemView.findViewById(R.id.name_product);
            txtPrice = itemView.findViewById(R.id.price_product);
            this.itemClickListenerRecycle = itemClickListenerRecycle;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListenerRecycle.OnItemClick(getAbsoluteAdapterPosition());
        }
    }
}

