package com.damian.pregoadminapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.damian.pregoadminapp.Adapters.pizzaQuantityAdapterView;
import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Pizza;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addOrder extends AppCompatActivity {
    PregoAdminAPI prego;
    RadioGroup paymentMethod;
    RadioGroup pickUpMethod;
    Button pizzaSelector;
    Button addOrder;
    EditText date;
    EditText time;
    String[] pizzas;
    List<Integer> pizzaselected;
    List<Pizza>pizzaSelected;
    TextView heading;
    RecyclerView pizzaQuantitySelector;
   RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.addOrderHeading);
        heading.setText("Add New Order");

        prego = new PregoAdminAPI();
        paymentMethod = findViewById(R.id.paymentRadioGroup);
        pickUpMethod= findViewById(R.id.pickOptionRadioGroup);
        pizzaSelector=findViewById(R.id.orderPizzaSelectorButton);
        addOrder=findViewById(R.id.addPizzaAddButton);
        pizzaQuantitySelector=findViewById(R.id.pizzaQuantityRecyclerView);

        date = findViewById(R.id.datePicker);
        date.setText(getCurrentDate());

        time = findViewById(R.id.timePicker);
        time.setText(getCurrentTime());

}

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
        String curdate = mdformat.format(calendar.getTime());
        return curdate;
    }

    public String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String time = mdformat.format(calendar.getTime());
        return time;
    }

    public void pizzaFiller(View view){
        pizzas = new String[prego.getPizzaIndex().size()];
        for(int i =0; i<pizzas.length;i++){
            pizzas[i] = prego.getPizzaIndex().get(i).getName();
        }
        pizzaselected = new ArrayList<>();
        pizzaSelected = new ArrayList<>();
        pizzaSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(addOrder.this);
                mBuilder.setTitle("Topping Selection")
                        .setMultiChoiceItems(pizzas, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if( isChecked ){
                                    pizzaselected.add(i);
                                }else {
                                    pizzaselected.remove(Integer.valueOf(i));
                                }
                            }
                        });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int z =0; z < pizzaselected.size();z++){
                            pizzaSelected.add(prego.getPizzaIndex().get(pizzaselected.get(z)));
                        }

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
            pizzaQuantitySelector.setHasFixedSize(true);
            pizzaQuantitySelector.setLayoutManager(new LinearLayoutManager(this));
            adapter = new pizzaQuantityAdapterView(pizzaSelected,this);
            pizzaQuantitySelector.setAdapter(adapter);
    }

    public void addOrder(View view){
        String method="";
        String method2="";
        int radioID = paymentMethod.getCheckedRadioButtonId();
        int radioId2 =pickUpMethod.getCheckedRadioButtonId();
        if(radioID == R.id.collection){
            method = "Collection";
        }else{
            method="Delivery";
        }
        if (radioId2 == R.id.cardPayment){
            method2 = "Card";
        }else{
            method2 = "Cash";
        }
        String dateReceived = date.getText().toString();
        String timeReceived = time.getText().toString();
        prego.addOrder(dateReceived,timeReceived,method,method2,pizzaSelected);
        Intent In = new Intent(addOrder.this, orderList.class);
        startActivity(In);
    }
}
