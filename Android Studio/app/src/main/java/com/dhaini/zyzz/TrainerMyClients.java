package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class TrainerMyClients extends AppCompatActivity {
    String trainerUsername;
    private RecyclerView myClientListRecylerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        trainerUsername = getIntent().getStringExtra("trainerUsername");

        // Initializing Options Button
        setContentView(R.layout.activity_trainer_my_clients);
        List<String> genders = Arrays.asList("","Add Client","Logout");
        Spinner optionsSpinner = findViewById(R.id.spinnerOption);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer,genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        optionsSpinner.setAdapter(adapter);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Add Client")){
                    Intent intentAddClient = new Intent(TrainerMyClients.this,TrainerAddClient.class);
                    optionsSpinner.setSelection(0);
                    intentAddClient.putExtra("TrainerUsername",trainerUsername);
                    startActivity(intentAddClient);

                }
                if(adapterView.getItemAtPosition(i).equals("Logout")){
                    Intent intentLogout = new Intent(TrainerMyClients.this,MainActivity.class);
                    // End all previous activities and go back to main page
                    intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentLogout);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}