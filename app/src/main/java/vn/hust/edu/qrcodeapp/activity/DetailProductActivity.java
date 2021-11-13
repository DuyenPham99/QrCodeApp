package vn.hust.edu.qrcodeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.model.Product;

public class DetailProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageView;
    TextView name, price, category, origin, motivation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        init();
        actionToolbar();
        getDetailProduct();
    }

    private void getDetailProduct() {
        String name_product = "";
        double price_product = 0;
        String origin_product = "";
        List<String> motivation_product = null;
        String category_product = "";
        float mass_product;
        String unitMass_product;
        String image_product;
        String text = "";

        Product product = (Product) getIntent().getSerializableExtra("detailProduct");
        name_product = product.getName();
        price_product = product.getPrice();
        origin_product = product.getOrigin();
        motivation_product = product.getMovingLocationInfo();
        category_product = product.getTypeProduct();
        mass_product = product.getMass();
        unitMass_product = product.getMassUnit();
        image_product = product.getImageUrl();

        for (String item : motivation_product) {
            text += "- " + item + " \n";
        }

        name.setText(name_product);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        price.setText(decimalFormat.format(price_product) + "đ /" + (int) mass_product + "" + unitMass_product.toLowerCase());
//        category.setText(category_product);
        origin.setText(origin_product);
        if (text.equals(""))
            motivation.setText("Chưa có thông tin về vị trí di chuyển");
        else
            motivation.setText(text);
        Picasso.get().load("http://192.168.85.116:8080/" + image_product).placeholder(R.drawable.background).error(R.drawable.logo).into(imageView);

    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void init() {
        toolbar = findViewById(R.id.toolbarchitietsp);
        imageView = findViewById(R.id.image_detail_product);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
//        category = findViewById(R.id.category);
        origin = findViewById(R.id.origin);
        motivation = findViewById(R.id.movingposition);
    }
}