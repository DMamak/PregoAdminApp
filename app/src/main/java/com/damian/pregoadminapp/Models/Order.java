package com.damian.pregoadminapp.Models;

import java.util.List;

/**
 * Created by damia on 08/03/2018.
 */

public class Order {
    public Long id = 0L;
    public static Long counter =0L;
    private String dateReceived;
    private String timeReceived;
    private String option;
    private String paymentMethod;
    private List<Pizza> pizzas;
    private String status;

    public Order(String dateReceived, String timeReceived, String option, String paymentMethod,List<Pizza>pizzas) {
        this.id = counter++;
        this.dateReceived = dateReceived;
        this.timeReceived = timeReceived;
        this.option = option;
        this.paymentMethod = paymentMethod;
        this.pizzas=pizzas;
        this.status = "Received";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(String timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public static Long getCounter() {
        return counter;
    }

    public static void setCounter(Long counter) {
        Order.counter = counter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
