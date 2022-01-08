package com.erickdev.getmovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        final Intent i = new Intent (this,MainActivity.class);
        Thread timer =new Thread() {
            public void run () {
                try {
                    sleep(250);
                  startActivity(new Intent(getApplicationContext()
                    , MainActivity.class));
                  finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
            timer.start();
    }
}
