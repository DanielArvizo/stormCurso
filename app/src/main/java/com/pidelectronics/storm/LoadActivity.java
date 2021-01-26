package com.pidelectronics.storm;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class

LoadActivity extends AppCompatActivity {

    Animation animAbajo;
    ImageView imgLogo;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        imgLogo = findViewById(R.id.imgLogo);
        animAbajo = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
        imgLogo.setAnimation(animAbajo);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null)timer.cancel();
        setTimer();
    }

    private void setTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LoadActivity.this,MainActivity.class));
            }
        },3000);
    }



}