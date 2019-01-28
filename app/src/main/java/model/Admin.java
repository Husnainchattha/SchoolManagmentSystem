package model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Hussnain on 30/12/2018.
 */
@IgnoreExtraProperties
public class Admin {
    public String name;
    public String type;
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Admin(String type, String email, String password) {
        this.type = type;
        this.email = email;
        this.password = password;
    }

    public String email;
    public String password;

    public Admin(String email) {
        this.email = email;
    }

    public Admin(){

    }


    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin(String image,String name,String type, String email, String password) {
      this.image=image;
       this.name=name;
        this.type=type;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
