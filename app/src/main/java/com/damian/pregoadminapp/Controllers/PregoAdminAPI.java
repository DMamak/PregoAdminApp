package com.damian.pregoadminapp.Controllers;

import android.net.Uri;
import android.util.Log;

import com.damian.pregoadminapp.Models.Order;
import com.damian.pregoadminapp.Models.Pizza;
import com.damian.pregoadminapp.Models.Requests;
import com.damian.pregoadminapp.Models.Topping;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by damia on 08/03/2018.
 */

public class PregoAdminAPI {
    public void PregoAdminAPI() {

    }

    public static ArrayList<Topping> toppingsIndex = new ArrayList<>();
    public static ArrayList<Pizza> pizzaIndex = new ArrayList<>();
    private static ArrayList<Order> orderIndex = new ArrayList<>();
    public static ArrayList<Requests> customerIndex = new ArrayList<>();

    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReference1;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private Uri imageURI;


    public Topping addTopping(String name) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Toppings");
        Topping topping = new Topping(name);
        DatabaseReference newTopping = mDatabaseReference.child(String.valueOf(topping.id));
        newTopping.setValue(topping);
        return topping;
    }

    public ArrayList<Topping> getToppingsIndex() {
        return toppingsIndex;
    }

    public ArrayList<Requests> getCustomerIndex() {
        return customerIndex;
    }

    public Pizza addPizza(String name, String size, double price, List<Topping> toppingList, String image) {
        Pizza pizza = new Pizza(name, size, price, toppingList, image);
        int toppinglength = 0;
        String topping = "";
        toppinglength = pizza.getToppings1().size();
        for (int i = 0; i < toppinglength; i++) {

            topping = topping + " " + pizza.getToppings1().get(i).name;

        }
        Log.i("INFO", topping);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Pizza");

        DatabaseReference newPizza = mDatabaseReference.child(String.valueOf(pizza.id));
        newPizza.setValue(pizza);
        mDatabaseReference1 = FirebaseDatabase.getInstance().getReference().child("menu");
        DatabaseReference newPizza1 = mDatabaseReference1.child(String.valueOf(pizza.id));
        Map<String, String> datatoSave = new HashMap<>();
        datatoSave.put("image", image);
        datatoSave.put("menuId", "01");
        datatoSave.put("name", name);
        datatoSave.put("price", String.valueOf(price));
        datatoSave.put("toppings", topping);
        newPizza1.setValue(datatoSave);

        return pizza;
    }

    public ArrayList<Pizza> getPizzaIndex() {
        return pizzaIndex;
    }

    public Order addOrder(String dateReceived, String timeReceived, String option, String paymentMethod, List<Pizza> pizzas) {
        Order order = new Order(dateReceived, timeReceived, option, paymentMethod, pizzas);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("OrderAdmin");
        DatabaseReference newOrder = mDatabaseReference.child(String.valueOf(order.id));
        newOrder.setValue(order);
        return order;
    }

    public ArrayList<Order> getOrderIndex() {
        return orderIndex;

    }


    public void customerOrderLoader(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders");

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mDatabaseReference1 = FirebaseDatabase.getInstance().getReference().child("orders").child(dataSnapshot.getKey());
                mDatabaseReference1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Requests customerOrder = dataSnapshot.getValue(Requests.class);
                        customerIndex.add(customerOrder);
                        Requests.setId(customerOrder.getId()+1L);




                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });







            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void toppingLoader(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Toppings");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Topping topping = dataSnapshot.getValue(Topping.class);
                toppingsIndex.add(topping);
                Topping.setCounter((topping.id)+1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void pizzaLoader(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Pizza");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pizza pizza = dataSnapshot.getValue(Pizza.class);
                pizzaIndex.add(pizza);
                Pizza.setCounter((pizza.id)+1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }

    public void orderLoader(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("OrderAdmin");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order order = dataSnapshot.getValue(Order.class);
                orderIndex.add(order);
                Order.setCounter((order.id)+1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
