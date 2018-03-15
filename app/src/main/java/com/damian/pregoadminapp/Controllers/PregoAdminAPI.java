package com.damian.pregoadminapp.Controllers;

import android.content.SharedPreferences;

import com.damian.pregoadminapp.Models.Order;
import com.damian.pregoadminapp.Models.Pizza;
import com.damian.pregoadminapp.Models.Topping;
import com.damian.pregoadminapp.toppingList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by damia on 08/03/2018.
 */

public class PregoAdminAPI
{
    public void PregoAdminAPI(){

    }
    public static ArrayList<Topping>toppingsIndex = new ArrayList<>();
    private static ArrayList<Pizza> pizzaIndex = new ArrayList<>();
    private static ArrayList<Order> orderIndex = new ArrayList<>();


    public Topping addTopping(String name){
        Topping topping = new Topping(name);
        toppingsIndex.add(topping);
        return topping;
    }

    public ArrayList<Topping> getToppingsIndex(){
        return toppingsIndex;
    }

    public Pizza addPizza(String name,String size,double price,List<Topping> toppingList){
        Pizza pizza = new Pizza(name,size,price,toppingList);
        pizzaIndex.add(pizza);
        return pizza;
    }

    public ArrayList<Pizza> getPizzaIndex(){
        return pizzaIndex;
    }

    public Order addOrder(String dateReceived, String timeReceived, String option, String paymentMethod,List<Pizza>pizzas){
        Order order = new Order(dateReceived,timeReceived,option,paymentMethod,pizzas);
        orderIndex.add(order);
        return order;
    }

    public ArrayList<Order> getOrderIndex() {
        return orderIndex;

    }

    public void toppingLoader(){
        toppingsIndex.add(new Topping("Cheese"));
        toppingsIndex.add(new Topping("Tomato"));
        toppingsIndex.add(new Topping("Ham"));
        toppingsIndex.add(new Topping("Chicken"));
        toppingsIndex.add(new Topping("Pepperoni"));
        toppingsIndex.add(new Topping("Onion"));
        toppingsIndex.add(new Topping("Pepper"));
        toppingsIndex.add(new Topping("Pineapple"));
        toppingsIndex.add(new Topping("Mushroom"));
        toppingsIndex.add(new Topping("Garlic"));
        toppingsIndex.add(new Topping("Spinach"));
        toppingsIndex.add(new Topping("Gorgonzola"));
        toppingsIndex.add(new Topping("Blue Cheese"));
        toppingsIndex.add(new Topping("Chillies"));
        toppingsIndex.add(new Topping("Tuna"));
        toppingsIndex.add(new Topping("Anchovies"));
        toppingsIndex.add(new Topping("Salmon"));


    }

    public void pizzaLoader(){
        ArrayList<Topping> pizza= new ArrayList<>();
        pizza.add(toppingsIndex.get(0));
        pizza.add(toppingsIndex.get(1));
        pizzaIndex.add(new Pizza("Margherita","16''",9.00,pizza));
        ArrayList<Topping> pizza1 = new ArrayList<>();
        pizza1.add(toppingsIndex.get(0));
        pizza1.add(toppingsIndex.get(1));
        pizza1.add(toppingsIndex.get(8));
        pizzaIndex.add(new Pizza("Funghi","16''",9.50,pizza1));
        ArrayList<Topping> pizza2 = new ArrayList<>();
        pizza2.add(toppingsIndex.get(0));
        pizza2.add(toppingsIndex.get(1));
        pizza2.add(toppingsIndex.get(2));
        pizzaIndex.add(new Pizza("Venezia","16''",9.50,pizza2));
        ArrayList<Topping> pizza3 = new ArrayList<>();
        pizza3.add(toppingsIndex.get(0));
        pizza3.add(toppingsIndex.get(1));
        pizza3.add(toppingsIndex.get(4));
        pizza3.add(toppingsIndex.get(6));
        pizza3.add(toppingsIndex.get(13));
        pizzaIndex.add(new Pizza("Vesuvio","16''",9.50,pizza3));
        ArrayList<Topping> pizza4 = new ArrayList<>();
        pizza4.add(toppingsIndex.get(0));
        pizza4.add(toppingsIndex.get(1));
        pizza4.add(toppingsIndex.get(2));
        pizza4.add(toppingsIndex.get(7));
        pizzaIndex.add(new Pizza("Tropicana","16''",9.50,pizza4));
        ArrayList<Topping> pizza5 = new ArrayList<>();
        pizza5.add(toppingsIndex.get(0));
        pizza5.add(toppingsIndex.get(1));
        pizza5.add(toppingsIndex.get(5));
        pizza5.add(toppingsIndex.get(14));
        pizza5.add(toppingsIndex.get(15));
        pizzaIndex.add(new Pizza("Tonno","16''",9.50,pizza5));
        ArrayList<Topping> pizza6 = new ArrayList<>();
        pizza6.add(toppingsIndex.get(0));
        pizza6.add(toppingsIndex.get(1));
        pizza6.add(toppingsIndex.get(3));
        pizza6.add(toppingsIndex.get(9));
        pizzaIndex.add(new Pizza("Pollo","16''",9.50,pizza6));
        ArrayList<Topping> pizza7 = new ArrayList<>();
        pizza7.add(toppingsIndex.get(0));
        pizza7.add(toppingsIndex.get(1));
        pizza7.add(toppingsIndex.get(6));
        pizza7.add(toppingsIndex.get(16));
        pizza7.add(toppingsIndex.get(9));
        pizzaIndex.add(new Pizza("Al Salmone","16''",9.50,pizza7));





    }

    public void orderLoader(){
        ArrayList<Pizza>order = new ArrayList<>();
        order.add(pizzaIndex.get(0));
        orderIndex.add(new Order("2018/03/15","15:00","Collection","Cash",order));
        ArrayList<Pizza>order1 = new ArrayList<>();
        order1.add(pizzaIndex.get(2));
        order1.add(pizzaIndex.get(3));
        orderIndex.add(new Order("2018/03/15","14:30","Collection","Card",order1));
        ArrayList<Pizza>order2 = new ArrayList<>();
        order2.add(pizzaIndex.get(0));
        order2.add(pizzaIndex.get(1));
        order2.add(pizzaIndex.get(5));
        orderIndex.add(new Order("2018/03/15","14:02","Delivery","Cash",order2));
        ArrayList<Pizza>order3 = new ArrayList<>();
        order3.add(pizzaIndex.get(5));
        order3.add(pizzaIndex.get(2));
        order3.add(pizzaIndex.get(6));
        order3.add(pizzaIndex.get(4));
        order3.add(pizzaIndex.get(7));
        orderIndex.add(new Order("2018/03/15","13:20","Delivery","Card",order3));
        ArrayList<Pizza>order4 = new ArrayList<>();
        order4.add(pizzaIndex.get(0));
        order4.add(pizzaIndex.get(1));
        order4.add(pizzaIndex.get(2));
        order4.add(pizzaIndex.get(3));
        order4.add(pizzaIndex.get(4));
        order4.add(pizzaIndex.get(5));
        orderIndex.add(new Order("2018/03/15","10:00","Collection","Cash",order4));

    }
}
