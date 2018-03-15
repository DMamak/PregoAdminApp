package com.damian.pregoadminapp.Models;

/**
 * Created by damia on 08/03/2018.
 */

public class Topping {
    public Long id =0L;
    public static Long counter =0L;
    public String name;

    public Topping(String name) {
        this.id=counter++;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }

    public static void setCounter(Long counter) {
        Topping.counter = counter;
    }

    public static Long getCounter() {
        return counter;
    }
}
