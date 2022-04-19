package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }
    public void trainerLogin(View view){ //Pop up window to show the PL logo and a small comment

        Intent intent = new Intent(this,TrainerLogin.class);

        startActivity(intent);
    }

    public void ClientLogin(View view) {
        Intent popupmenu = new Intent(this,ClientLogin.class);

        startActivity(popupmenu);
    }
}