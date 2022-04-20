package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class LoginWithTrainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_with_trainer);

        List<String> confirmation = Arrays.asList("","Yes","No");
        Spinner confirmationSpinner = findViewById(R.id.dumpTrainerSpinner);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer,confirmation);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        confirmationSpinner.setAdapter(adapter);

        confirmationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confirmation.set(0,"Are you sure ?");
                if(adapterView.getItemAtPosition(i).equals("Yes")){
                    confirmation.set(0,"");
                    confirmationSpinner.setSelection(0);

                }
                if(adapterView.getItemAtPosition(i).equals("No")){
                    confirmation.set(0,"");
                    confirmationSpinner.setSelection(0);


                }

            } @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                confirmation.set(0,"");
            }
        });
    }
}