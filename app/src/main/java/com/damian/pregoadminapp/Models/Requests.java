package com.damian.pregoadminapp.Models;

import java.util.ArrayList;
import java.util.List;



public class Requests {
    public static Long id =0L;
    private static Long counter = 0L;
    private String phone;
    private String name;
    private String address;
    private String status;
    private List<Orders> foods;

    public Requests() {
    }

    public Requests(String phone, String name, String address, List<Orders> foods) {
        this.id = counter ++;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.status = "0";
        this.foods = foods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Orders> getFoods() {
        return foods;
    }

    public void setFoods(List<Orders> foods) {
        this.foods = foods;
    }

    public Long getId() {
        return id;
    }

    public static void setId(Long id) {
        Requests.id = id;
    }

    public static Long getCounter() {
        return counter;
    }

    public static void setCounter(Long counter) {
        Requests.counter = counter;
    }

}