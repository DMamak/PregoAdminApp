package com.damian.pregoadminapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Pizza;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addOrder extends AppCompatActivity
{
    PregoAdminAPI prego;
    RadioGroup paymentMethod;
    RadioGroup pickUpMethod;
    Button pizzaSelector;
    Button addOrder;
    EditText date;
    EditText time;
    String[] pizzas;
    List<Integer> pizzaselected;
    List<Pizza>pizzaSelected = new ArrayList<>();
    TextView heading;
    boolean [] checkedItems;

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

        date = findViewById(R.id.datePicker);
        date.setText(getCurrentDate());

        time = findViewById(R.id.timePicker);
        time.setText(getCurrentTime());
        checkedItems = new boolean[prego.getPizzaIndex().size()];

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

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(addOrder.this);
                mBuilder.setTitle("Topping Selection")
                        .setMultiChoiceItems(pizzas, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
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

    public void addOrder(View view) {
        String method = "";
        String method2 = "";
        int radioID = paymentMethod.getCheckedRadioButtonId();
        int radioId2 = pickUpMethod.getCheckedRadioButtonId();
        if (radioID == R.id.collection) {
            method = "Collection";
        } else {
            method = "Delivery";
        }
        if (radioId2 == R.id.cardPayment) {
            method2 = "Card";
        } else {
            method2 = "Cash";
        }
        String dateReceived = date.getText().toString();
        String timeReceived = time.getText().toString();
        if (dateReceived.isEmpty() | timeReceived.isEmpty() | method.isEmpty() | method2.isEmpty() | pizzaSelected.isEmpty()) {
            Toast.makeText(this, "Missing Details.Please fill them in !", Toast.LENGTH_SHORT).show();
        } else {


            prego.addOrder(dateReceived, timeReceived, method, method2, pizzaSelected);
            Intent In = new Intent(addOrder.this, orderList.class);
            startActivity(In);
            finish();
        }
    }
}
