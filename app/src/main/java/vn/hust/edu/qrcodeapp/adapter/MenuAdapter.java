package vn.hust.edu.qrcodeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.model.MenuItem;

public class MenuAdapter extends BaseAdapter {

    List<MenuItem> menuItemList = new ArrayList<>();
    Context context;

    public MenuAdapter(List<MenuItem> menuItemList, Context context) {
        this.menuItemList = menuItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu, null);
            viewHolder.textView = convertView.findViewById(R.id.textview);
            viewHolder.imageView = convertView.findViewById(R.id.imageview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MenuItem menuItem = (MenuItem) getItem(position);
        viewHolder.textView.setText(menuItem.getName());
        Picasso.get().load(menuItem.getIcon())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_focused)
                .into(viewHolder.imageView);

        return convertView;
    }
}
