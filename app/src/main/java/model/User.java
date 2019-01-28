package model;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String type;
    public String email;
    public String password;
    public String image;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User(String name, String type, String email, String password, String image) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.password = password;
        this.image = image;
    }
}
