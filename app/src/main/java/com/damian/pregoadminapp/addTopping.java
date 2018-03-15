package com.damian.pregoadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.damian.pregoadminapp.Controllers.PregoAdminAPI;

public class addTopping extends AppCompatActivity {
    PregoAdminAPI prego;
    TextView toppingName;
    Button addNewTopping;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.addToppingHeading);
        heading.setText("Add New Topping");
        prego= new PregoAdminAPI();
        toppingName=findViewById(R.id.addNewToppingEditText);
        addNewTopping=findViewById(R.id.addNewToppingButton);
    }

    public void addNewTopping(View view) {
        String newToppingName = toppingName.getText().toString();
        if (newToppingName.isEmpty()) {
            Toast.makeText(this, "No Details Entered.", Toast.LENGTH_SHORT).show();
        } else {
            prego.addTopping(newToppingName);
            Log.i("info", "addNewTopping: " + prego.getToppingsIndex().size());
            Intent I = new Intent(addTopping.this, toppingList.class);
            startActivity(I);
        }
    }

}
