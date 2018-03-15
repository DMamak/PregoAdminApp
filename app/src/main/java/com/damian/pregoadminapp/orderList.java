package com.damian.pregoadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.damian.pregoadminapp.Adapters.customItemClickListner;
import com.damian.pregoadminapp.Adapters.orderAdapterView;
import com.damian.pregoadminapp.Controllers.PregoAdminAPI;

public class orderList extends AppCompatActivity {
    PregoAdminAPI prego;
    RecyclerView orders;
    RecyclerView.Adapter adapter;
    long orderId;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.orderListHeading);
        heading.setText("Order List");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(orderList.this , addOrder.class);
                startActivity(I);
            }
        });

        prego = new PregoAdminAPI();
        if(prego.getPizzaIndex().isEmpty() && prego.getToppingsIndex().isEmpty()) {
            prego.toppingLoader();
            prego.pizzaLoader();
            prego.orderLoader();
        }
        orders =findViewById(R.id.orderRecyclerView);
        orders.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        orders.setLayoutManager(gridLayoutManager);
        orders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new orderAdapterView(prego.getOrderIndex(),this, new customItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                orderId=prego.getOrderIndex().get(position).getId();
                Log.i("INFO", "onItemClick: order List" + orderId);
                Intent myIntent = new Intent(orderList.this,updateOrder.class).putExtra("ID",orderId);
                startActivity(myIntent);
            }
        });
        orders.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_list, menu);
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
            case R.id.pizzaListMenu : startActivity(new Intent(this, pizzaList.class));
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setAdapter(){
        adapter = new orderAdapterView( prego.getOrderIndex(),this, new customItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                orderId=prego.getPizzaIndex().get(position).getId();
                Log.i("INFO", "onItemClick: order List" + orderId);
                Intent myIntent = new Intent(orderList.this,updateOrder.class).putExtra("ID",orderId);
                startActivity(myIntent);
            }
        });
        orders.setAdapter(adapter);
    }
}
