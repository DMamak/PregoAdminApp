package com.damian.pregoadminapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Pizza;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class updateOrder extends AppCompatActivity {
    PregoAdminAPI prego;
    TextView status;
    TextView date;
    TextView time;
    ListView pizzas;
    Button update;
    Button delete;
    Button updateStatus;
    long orderId;
    int orderID;
    String[] states;
    TextView totalPrice;
    TextView heading;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.updateOrderHeading);
        heading.setText("Updating Order");

        prego = new PregoAdminAPI();
        mDataBase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDataBase.getReference().child("OrderAdmin");
        mDatabaseReference.keepSynced(true);
        status = findViewById(R.id.orderUpdateStatus);
        date=findViewById(R.id.updateDateReceived);
        time=findViewById(R.id.updateTimeReceived);
        pizzas=findViewById(R.id.updateOrderListView);
        update=findViewById(R.id.updateUpdateOrder);
        delete=findViewById(R.id.deleteUpdateOrder);
        updateStatus=findViewById(R.id.updateStatusOrderButton);
        orderId = getIntent().getLongExtra("ID",orderId);
        totalPrice=findViewById(R.id.totalPriceUpdateOrder);
        Log.i("INFO", "onCreate: " + orderId);
        orderID=(int)orderId ;
        status.setText(prego.getOrderIndex().get(orderID).getStatus());
        date.setText(prego.getOrderIndex().get(orderID).getDateReceived());
        time.setText(prego.getOrderIndex().get(orderID).getTimeReceived());
        totalPrice.setText(String.valueOf(totalCost()));
        statesLoader();
        ArrayAdapter<Pizza> arrayAdapter = new ArrayAdapter<Pizza>(this,android.R.layout.simple_list_item_1,prego.getOrderIndex().get(orderID).getPizzas());
        pizzas.setAdapter(arrayAdapter);
    }

    public void statesLoader(){
        states= new String[5];
        states[0]="Received";
        states[1]="In Progress";
        states[2]="Ready to be Collected";
        states[3]="Being Delivered";
        states[4]="Completed";
    }

    public void statusUpdater(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(updateOrder.this);
        builder.setTitle("Pick the Current Status");
        builder.setItems(states, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("INFO", "onClick: "+ i);
                if(i==0){
                    prego.getOrderIndex().get(orderID).setStatus(states[0]);
                    status.setText(states[0]);

                }else if (i==1){
                    prego.getOrderIndex().get(orderID).setStatus(states[1]);
                    status.setText(states[1]);

                }else if (i==2){
                    prego.getOrderIndex().get(orderID).setStatus(states[2]);
                    status.setText(states[2]);

                }else if (i==3){
                    prego.getOrderIndex().get(orderID).setStatus(states[3]);
                    status.setText(states[3]);

                }else if (i==4){
                    prego.getOrderIndex().get(orderID).setStatus(states[4]);
                    status.setText(states[4]);

                }

            }
        });

        AlertDialog mDialog  = builder.create();
        mDialog.show();

    }

    public void deleteOrder(View view){
        Toast.makeText(this,"You have Deleted Order no: "+ prego.getOrderIndex().get(orderID).getId().toString(),Toast.LENGTH_SHORT).show();
        prego.getOrderIndex().remove(orderID);
        mDatabaseReference.child(String.valueOf(orderID)).removeValue();
        Intent I = new Intent(updateOrder.this,orderList.class);
        for(int y =0;y < prego.getOrderIndex().size();y++){
            prego.getOrderIndex().get(y).setId(Long.valueOf(y));
            prego.getOrderIndex().get(y).setCounter(Long.valueOf(y+1));
            Log.i("INFO", "deleteOrder: id: "+ prego.getOrderIndex().get(y).getId());
            Log.i("INFO", "deleteOrder: counter"+ prego.getOrderIndex().get(y).getCounter());
        }

        startActivity(I);
        finish();
    }

    public void updateOrder(View view){
        String state="";
        state=status.getText().toString();
        prego.getOrderIndex().get(orderID).setStatus(state);
        mDatabaseReference.child(String.valueOf(orderID)).child("status").setValue(state);
        Intent I = new Intent(updateOrder.this,orderList.class);
        startActivity(I);
        finish();
    }
    public double totalCost() {
        double price = 0;
        for (int x = 0; x < prego.getOrderIndex().get(orderID).getPizzas().size(); x++) {
           price=price + prego.getOrderIndex().get(orderID).getPizzas().get(x).getPrice();

        }
        return price;
    }



}
