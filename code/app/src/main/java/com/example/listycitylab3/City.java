package com.example.listycitylab3;
import java.io.Serializable;

// City represents a city with a name and province
//implement Serializable so we can pass it around
public class City implements Serializable {
    private String name;
    private String province;
    //constructor used when creating a new city
    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }
    public String getName() {
        return name;
    }
    public String getProvince() {
        return province;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

