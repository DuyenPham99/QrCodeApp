package vn.hust.edu.qrcodeapp.model;

public class User {
    private String username;
    private String phoneNumber;
    private String id;
    private String fullName;
    private String address;
    private String gender;
    private String email;
    private String password;
    private String roles;

    public User(String username, String phoneNumber, String id, String fullName, String address, String gender, String email, String password, String roles) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
