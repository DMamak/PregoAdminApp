package com.damian.pregoadminapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.damian.pregoadminapp.Adapters.customItemClickListner;
import com.damian.pregoadminapp.Adapters.toppingAdapterView;
import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Topping;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class toppingList extends AppCompatActivity {
    PregoAdminAPI prego;
    RecyclerView toppings;
    RecyclerView.Adapter adapter;
    long toppingId;
    TextView heading;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topping_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.toppingListHeading);
        heading.setText("List of Toppings");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(toppingList.this, addTopping.class);
                startActivity(I);
            }
        });

        prego = new PregoAdminAPI();
        mDataBase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDataBase.getReference().child("Toppings");
        mDatabaseReference.keepSynced(true);
        toppings = findViewById(R.id.toppingRecyclerView);
        toppings.setHasFixedSize(true);
        toppings.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topping_list, menu);
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
            case R.id.pizzaListMenu : startActivity(new Intent(this, pizzaList.class));
                break;
            case R.id.orderListMenu : startActivity(new Intent(this, orderList.class));
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setAdapter(){
        adapter = new toppingAdapterView(prego.getToppingsIndex(), this, new customItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {
                toppingId = prego.getToppingsIndex().get(position).getId();
                Log.i("INFO","onItemClick: " + toppingId);
                deleteToppingDialog(toppingId);
            }
        });
        toppings.setAdapter(adapter);
    }

    public void deleteToppingDialog(final long toppingid){
        AlertDialog.Builder builder = new AlertDialog.Builder(toppingList.this);
        builder.setTitle("Topping Removal");
        builder.setMessage("You are about to remove " + prego.getToppingsIndex().get(((int)toppingid)).getName() + "\n"
                            + "Are you sure to continue?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(toppingList.this, "You have Succesfully Deleted " + prego.getToppingsIndex().get(((int)toppingid)).getName(), Toast.LENGTH_SHORT).show();
               prego.getToppingsIndex().remove(((int)toppingid));
               mDatabaseReference.child(String.valueOf(toppingid)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       setAdapter();
                   }
               });


                Log.i("INFO", "onClick: "+prego.getToppingsIndex().get(prego.getPizzaIndex().size()).getCounter());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setAdapter();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }


}
