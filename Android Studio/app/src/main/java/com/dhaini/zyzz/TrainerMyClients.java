package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class TrainerMyClients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_trainer_my_clients);
        List<String> genders = Arrays.asList("","Add Client","Logout");
        Spinner genderSpinner = findViewById(R.id.spinnerOption);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer,genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        genderSpinner.setAdapter(adapter);

    }
}