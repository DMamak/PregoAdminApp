package com.damian.pregoadminapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;
import com.damian.pregoadminapp.Controllers.PregoAdminAPI;

public class splashScreen extends AppCompatActivity {
    private PregoAdminAPI prego = new PregoAdminAPI();


    @Override
    protected void onStart() {
        super.onStart();
        if (prego.getToppingsIndex().size() == 0) {
            prego.toppingLoader();
            prego.pizzaLoader();
            prego.orderLoader();
            prego.customerOrderLoader();
            Log.i("INFO",String.valueOf(prego.getCustomerIndex().isEmpty()));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(splashScreen.this)
                .withFullScreen()
                .withTargetActivity(orderList.class)
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#ffffff"))
                .withLogo(R.drawable.prego_pizza_logo_rgb);


        View view = config.create();
        setContentView(view);
    }
}
