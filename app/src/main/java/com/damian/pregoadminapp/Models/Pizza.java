package com.damian.pregoadminapp.Models;

import android.net.Uri;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damia on 08/03/2018.
 */

public class Pizza {
    public Long id =0L;
    private static Long counter =0L;
    private String name;
    private String size;
    private double price;
    private List<Topping> toppings1;
    private String image;
    private String toppings;
    private String menuId;
    private ArrayList<Boolean> ischecked;

    public Pizza(){}

    public Pizza(String name, String size ,double price,List<Topping> toppings,String image,ArrayList<Boolean> ischecked) {
        this.id = counter++;
        this.name = name;
        this.size = size;
        this.price = price;
        this.toppings1=toppings;
        this.image = image;
        this.ischecked = ischecked;

    }

    public Pizza(String image,String name,double price,String toppings){
        this.image = image;
        this.name = name;
        this.price = price;
        this.toppings = toppings;
        this.menuId = "01";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Topping> getToppings1() {
        return toppings1;
    }

    public void setToppings1(List<Topping> toppings) {
        this.toppings1 = toppings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Long getCounter() {
        return counter;
    }

    public static void setCounter(Long counter) {
        Pizza.counter = counter;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings1) {
        this.toppings = toppings1;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public ArrayList<Boolean> getIschecked() {
        return ischecked;
    }

    public void setIschecked(ArrayList<Boolean> ischecked) {
        this.ischecked = ischecked;
    }
}
