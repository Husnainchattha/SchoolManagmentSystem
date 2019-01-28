package model;

public class DateModel {
    public String absent;
    public String present;
    public String name;

    public DateModel(String name) {
      this.name=name;
    }

    public String getName() {

        return name;
    }

    public DateModel(String absent, String present) {
        this.absent = absent;
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public String getPresent() {
        return present;
    }





}
