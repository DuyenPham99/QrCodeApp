package vn.hust.edu.qrcodeapp.response;

import java.util.List;

import vn.hust.edu.qrcodeapp.model.Product;

public class ProductResponse {
    private int page;
    private int size;
    private List<Product> Products;

    public ProductResponse(int page, int size, List<Product> Products) {
        this.page = page;
        this.size = size;
        this.Products = Products;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}
