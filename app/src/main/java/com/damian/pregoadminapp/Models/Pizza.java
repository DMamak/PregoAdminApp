package com.damian.pregoadminapp.Models;

import android.net.Uri;
import android.widget.ArrayAdapter;

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
    private List<Topping> toppings;
    private String image;
    private int quantity;
    private String toppings1;
    private String menuId;

    public Pizza(){}

    public Pizza(String name, String size ,double price,List<Topping> toppings,String image) {
        this.id = counter++;
        this.name = name;
        this.size = size;
        this.price = price;
        this.toppings=toppings;
        this.image = image;

    }

    public Pizza(String image,String name,double price,String toppings){
        this.image = image;
        this.name = name;
        this.price = price;
        this.toppings1 = toppings;
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

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getToppings1() {
        return toppings1;
    }

    public void setToppings1(String toppings1) {
        this.toppings1 = toppings1;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
