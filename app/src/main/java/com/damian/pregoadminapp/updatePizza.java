package com.damian.pregoadminapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
    ImageButton imagebutton;
    TextView heading;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReference1;
    private FirebaseDatabase mDataBase;
    private static final int GALLERY_REQUEST = 1;
    private Uri imageURI;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;



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
        mStorageRef = FirebaseStorage.getInstance().getReference();
        pizzaId = getIntent().getLongExtra("ID",pizzaId);
        pizzaID=(int)pizzaId ;
        mProgress = new ProgressDialog(this);


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

    }

    public void updateOrder(View view) {
        mProgress.setMessage("Updating Pizza...");

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
                   StorageReference filepath = mStorageRef.child("Images").child(imageURI.getLastPathSegment());

                   filepath.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Uri downloadurl = taskSnapshot.getDownloadUrl();
                           pizza.setImage(downloadurl.toString());

                           mDatabaseReference.child(String.valueOf(pizzaID)).setValue(pizza).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   mProgress.dismiss();
                               }
                           });

                           Intent I = new Intent(updatePizza.this, pizzaList.class);
                           startActivity(I);
                       }
                   });

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


    }
