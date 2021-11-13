package vn.hust.edu.qrcodeapp.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private Boolean isDeleted;
    private String name;
    private String origin;
    private int mass;
    private String massUnit;
    private double price;
    private String imageUrl;
    private String typeProduct;
    private String _id;
    private List<String> users;
    private List<String> movingLocationInfo;
    private int __v;

    public Product(Boolean isDeleted, String name, String origin, int mass, String massUnit, double price, String imageUrl, String typeProduct, String _id, List<String> users, List<String> movingLocationInfo, int __v) {
        this.isDeleted = isDeleted;
        this.name = name;
        this.origin = origin;
        this.mass = mass;
        this.massUnit = massUnit;
        this.price = price;
        this.imageUrl = imageUrl;
        this.typeProduct = typeProduct;
        this._id = _id;
        this.users = users;
        this.movingLocationInfo = movingLocationInfo;
        this.__v = __v;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getMovingLocationInfo() {
        return movingLocationInfo;
    }

    public void setMovingLocationInfo(List<String> movingLocationInfo) {
        this.movingLocationInfo = movingLocationInfo;
    }

}
