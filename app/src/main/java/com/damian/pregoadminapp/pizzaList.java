package com.damian.pregoadminapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.damian.pregoadminapp.Adapters.customItemClickListner;
import com.damian.pregoadminapp.Adapters.pizzaAdapterView;
import com.damian.pregoadminapp.Adapters.toppingAdapterView;
import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Pizza;
import com.damian.pregoadminapp.Models.Topping;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class pizzaList extends AppCompatActivity {
    PregoAdminAPI prego;
    RecyclerView pizza;
    RecyclerView.Adapter adapter;
    long pizzaId;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.pizzaListHeading);
        heading.setText("Pizza Menu");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(pizzaList.this , addPizza.class);
                startActivity(I);
            }
        });
        prego = new PregoAdminAPI();
        pizza = findViewById(R.id.pizzaRecyclerView);
        pizza.setHasFixedSize(true);

        pizza.setLayoutManager(new LinearLayoutManager(this));

        setAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pizza_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId())
        {
            case R.id.toppingListMenu : startActivity(new Intent(this,toppingList.class));
                break;
            case R.id.orderListMenu : startActivity(new Intent(this, orderList.class));
            break;
            case R.id.customerOrderList:startActivity(new Intent(this,customerOrder.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void setAdapter(){
        adapter = new pizzaAdapterView(prego.getPizzaIndex(), this, new customItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                pizzaId=prego.getPizzaIndex().get(position).getId();
                Intent myIntent = new Intent(pizzaList.this,updatePizza.class).putExtra("ID",pizzaId);
                startActivity(myIntent);

            }
        });

        pizza.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();

    }
}
