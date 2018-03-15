package com.damian.pregoadminapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Topping;

import java.util.ArrayList;
import java.util.List;

public class addPizza extends AppCompatActivity {
    PregoAdminAPI prego;
    EditText pizzaName;
    EditText pizzaPrice;
    ToggleButton pizzaSize;
    Button pizzaAdd;
    Button toppingSelection;
    String[] toppings;
    List<Integer> toppingsselected;
    List<Topping>toppingSelected;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pizza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.addPizzaHeading);
        heading.setText("Add New Pizza");
        prego = new PregoAdminAPI();
        pizzaName = findViewById(R.id.updatePizzaNameEditText);
        pizzaPrice = findViewById(R.id.updatePizzaPriceEditText);
        pizzaSize = findViewById(R.id.updatePizzaSizeToggleButton);
        toppingSelection = findViewById(R.id.Toppings);
        pizzaAdd = findViewById(R.id.addPizzaAddButton);
    }

    public void toppingFIller(View view){
        toppings = new String[prego.getToppingsIndex().size()];
        for(int i =0; i<toppings.length;i++){
            toppings[i] = prego.getToppingsIndex().get(i).getName();
        }
        toppingsselected = new ArrayList<>();
        toppingSelected = new ArrayList<>();
        toppingSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(addPizza.this);
                mBuilder.setTitle("Topping Selection")
                        .setMultiChoiceItems(toppings, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if( isChecked ){
                                    toppingsselected.add(i);
                                }else {
                                    toppingsselected.remove(Integer.valueOf(i));
                                }
                            }
                        });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int z =0; z < toppingsselected.size();z++){
                            toppingSelected.add(prego.getToppingsIndex().get(toppingsselected.get(z)));
                        }

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }


    public void createPizza(View view) {
        boolean state = pizzaSize.isChecked();
        String Name = pizzaName.getText().toString();
        String Size;

        if (state) {
            Size = "12";
        } else {
            Size = "16";
        }
        Log.i("INFO", String.valueOf(Name.isEmpty()));
        Log.i("INFO",String.valueOf(pizzaPrice.getText().toString().isEmpty()));
            if(Name.isEmpty() | pizzaPrice.getText().toString().isEmpty()){
                Toast.makeText(this, "Missing Details.Please Fill them out", Toast.LENGTH_SHORT).show();

            }else {
                double price = Double.parseDouble(pizzaPrice.getText().toString());
                prego.addPizza(Name, Size, price, toppingSelected);
                Intent In = new Intent(addPizza.this, pizzaList.class);
                startActivity(In);
            }

    }

}


