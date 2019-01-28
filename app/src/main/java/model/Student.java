package model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Hussnain 30/12/2018.
 */
@IgnoreExtraProperties
public class Student {
    public String name;
    public String type;
    public String email;
    public String password;
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Student(){}

    public Student(String image,String name,String type,String email, String password) {
       this.image=image;
        this.name=name;
        this.type=type;
        this.email = email;
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
