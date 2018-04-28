package com.damian.pregoadminapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.damian.pregoadminapp.Controllers.PregoAdminAPI;
import com.damian.pregoadminapp.Models.Pizza;
import com.damian.pregoadminapp.Models.Topping;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class updatePizza extends AppCompatActivity {
    PregoAdminAPI prego;
    EditText updateName;
    EditText updatePrice;
    ToggleButton updateSize;
    Button updatePizza;
    long pizzaId;
    int pizzaID;
    String pizzaSize;
    Button deletePizza;
    Button toppingSelector;
    ImageButton imagebutton;
    TextView heading;
    String[] toppings;
    List<Integer> toppingsselected;
    List<Topping>toppingSelected = new ArrayList<>();
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReference1;
    private FirebaseDatabase mDataBase;
    private static final int GALLERY_REQUEST = 1;
    private Uri imageURI;
    private StorageReference mStorageRef;
    boolean [] checkedItems;
    ArrayList<Boolean> isChecked = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pizza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        heading=findViewById(R.id.updatePizzaHeading);
        heading.setText("Updating Pizza");
        prego = new PregoAdminAPI();
        mDataBase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDataBase.getReference().child("Pizza");
        mDatabaseReference.keepSynced(true);
        mDatabaseReference1 = mDataBase.getReference().child("menu");
        updateName=findViewById(R.id.updatePizzaNameEditText);
        updatePrice=findViewById(R.id.updatePizzaPriceEditText);
        updateSize=findViewById(R.id.updatePizzaSizeToggleButton);
        updatePizza=findViewById(R.id.updatePizzaUpdateButton);
        deletePizza=findViewById(R.id.deleteUpdatePizza);
        imagebutton=findViewById(R.id.updatePizzaImage);
        toppingSelector=findViewById(R.id.updatePizzaToppingSelector);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        pizzaId = getIntent().getLongExtra("ID",pizzaId);
        pizzaID=(int)pizzaId ;
        checkedItems = new boolean[prego.getPizzaIndex().get(pizzaID).getIschecked().size()];



        updateName.setText(prego.getPizzaIndex().get(pizzaID).getName());
        updatePrice.setText(Double.toString(prego.getPizzaIndex().get(pizzaID).getPrice()));
        pizzaSize = prego.getPizzaIndex().get(pizzaID).getSize();
        if (pizzaSize == "16"){
            updateSize.setChecked(false);
        }else{
            updateSize.setChecked(true);
        }
        imageURI = Uri.parse(prego.getPizzaIndex().get(pizzaID).getImage());
        Picasso.get().load(prego.getPizzaIndex().get(pizzaID).getImage()).fit().into(imagebutton);
        for(int x = 0 ; x < prego.getPizzaIndex().get(pizzaID).getIschecked().size();x++) {
            checkedItems[x] = prego.getPizzaIndex().get(pizzaID).getIschecked().get(x).booleanValue();
        }
    }

    public void updateOrder(View view) {

        boolean state = updateSize.isChecked();
        String Size;
        if (state) {
            Size = "12";
        } else {
            Size = "16";
        }
        final Pizza pizza = prego.getPizzaIndex().get(pizzaID);
        String image = pizza.getImage();

        if (updateName.getText().toString().isEmpty() | updatePrice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Missing Details.Please Fill them out", Toast.LENGTH_SHORT).show();
        } else {
            pizza.setName(updateName.getText().toString());
            pizza.setSize(Size);
            pizza.setPrice(Double.valueOf(updatePrice.getText().toString()));
            pizza.setImage(image);
            pizza.setIschecked(isChecked);

            StorageReference filepath = mStorageRef.child("Images").child(imageURI.getLastPathSegment());

            if(Uri.parse(prego.getPizzaIndex().get(pizzaID).getImage()).equals(imageURI)){

                mDatabaseReference.child(String.valueOf(pizzaID)).setValue(pizza);
                Intent I = new Intent(updatePizza.this, pizzaList.class);
                startActivity(I);
                finish();

            }else {

                filepath.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadurl = taskSnapshot.getDownloadUrl();
                        pizza.setImage(downloadurl.toString());

                        mDatabaseReference.child(String.valueOf(pizzaID)).setValue(pizza).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });


                    }
                });
                Intent I = new Intent(updatePizza.this, pizzaList.class);
                startActivity(I);
                finish();

            }

        }
    }

    public void deletePizza(View view) {
        Toast.makeText(this, "You Have Deleted "+prego.getPizzaIndex().get(pizzaID).getName(), Toast.LENGTH_SHORT).show();
        prego.getPizzaIndex().remove(pizzaID);
        mDatabaseReference.child(String.valueOf(pizzaID)).removeValue();
        mDatabaseReference1.child(String.valueOf(pizzaID)).removeValue();
        Intent I = new Intent(updatePizza.this, pizzaList.class);
        for (int y = 0; y < prego.getPizzaIndex().size(); y++) {
            prego.getPizzaIndex().get(y).setId(Long.valueOf(y));
            prego.getPizzaIndex().get(y).setCounter(Long.valueOf(y+1));
            Log.i("INFO", "onClick: " + prego.getPizzaIndex().get(y).getName() + " " + prego.getPizzaIndex().get(y).getId());
            Log.i("INFO","onCounter: " + prego.getPizzaIndex().get(y).getName()+" "+ prego.getPizzaIndex().get(y).getCounter());
        }
            //        Log.i("INFO", "onClick: "+prego.getPizzaIndex().get(prego.getPizzaIndex().size()).getCounter());

            startActivity(I);
        finish();

        }

        public void updateImage(View view){

            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_REQUEST);


        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            imageURI = data.getData();

            imagebutton.setImageURI(imageURI);

        }
    }
    public void toppingFIller(View view){
        toppings = new String[prego.getToppingsIndex().size()];
        for(int i =0; i<toppings.length;i++){
            toppings[i] = prego.getToppingsIndex().get(i).getName();
        }

        toppingsselected = new ArrayList<>();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(updatePizza.this);
        mBuilder.setTitle("Topping Selection")
                .setMultiChoiceItems(toppings, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
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
                for(int v =0 ; v< checkedItems.length;v++){
                    isChecked.add(v,checkedItems[v]);
                }

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }

    }
