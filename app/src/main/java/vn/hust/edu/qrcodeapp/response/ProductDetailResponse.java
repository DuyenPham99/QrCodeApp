package vn.hust.edu.qrcodeapp.response;

import java.util.List;

import vn.hust.edu.qrcodeapp.model.Product;

public class ProductDetailResponse {
    private boolean success;
    private String message;
    private List<Product> Product;

    public ProductDetailResponse(boolean success, String message, List<Product> Product) {
        this.success = success;
        this.message = message;
        this.Product = Product;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getProduct() {
        return Product;
    }

    public void setProduct(List<Product> Product) {
        this.Product = Product;
    }
}
