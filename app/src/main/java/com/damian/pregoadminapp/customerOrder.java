package com.damian.pregoadminapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.damian.pregoadminapp.Adapters.customItemClickListner;
import com.damian.pregoadminapp.Adapters.customerAdapterView;
import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Requests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class customerOrder extends AppCompatActivity {
    List<String> status = new ArrayList<>();
    PregoAdminAPI prego;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private DatabaseReference mDatabaseReference;
    private static DatabaseReference mDatabaseReference1;
    TextView heading;
    int id;
    private static String newestStatus ="";
    static String derp =" ";
    List<String> keys ;
    static String derp2 ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        statusLoader();
        heading=findViewById(R.id.customerListHeading);
        heading.setText("Customer Orders");
        prego = new PregoAdminAPI();
        recyclerView = findViewById(R.id.customerRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new customerAdapterView(prego.getCustomerIndex(), this, new customItemClickListner() {
            @Override
            public void onItemClick(View v, int position) {

               id = position ;
                Log.i("INFO", "onItemClick: " + id);
                statusDialog(id);
            }
        });
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }


    public void statusDialog(final int x){
        AlertDialog.Builder builder = new AlertDialog.Builder(customerOrder.this);
        builder.setTitle("Select the Status");
        final ArrayAdapter<String>adapter = new ArrayAdapter<>(customerOrder.this,android.R.layout.select_dialog_item,status);

             builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     newestStatus= String.valueOf(which);
                     prego.getCustomerIndex().get(x).setStatus(newestStatus);
                     final Requests newRequest = prego.getCustomerIndex().get(x);

                     mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
                     mDatabaseReference.addChildEventListener(new ChildEventListener() {
                         @Override
                         public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                             mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child(dataSnapshot.getKey());
                             derp2 = dataSnapshot.getKey();


                             keys= new ArrayList<>();

                             for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                 keys.add(snapshot.getKey());
                             }
                             derp = keys.get(x);
                             Log.i("INFO", derp);
                             Log.i("INFO",derp2);

                            mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                                    .child("orders").child(derp2).child(derp);

                             mDatabaseReference.setValue(newRequest);
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

//                     Log.i("INFO",mDatabaseReference1.getKey());
//                     mDatabaseReference1.child(derp).setValue(newRequest);



                     Intent i = new Intent(customerOrder.this , customerOrder.class);
                     adapter.notifyDataSetChanged();
                     startActivity(i);
                     finish();


                 }
             });
           AlertDialog mDialog =  builder.create();
           mDialog.show();

    }

    public void statusLoader(){
        status.add("Placed");
        status.add("Received");
        status.add("On its Way");
        status.add("Delivered");
    }


}
