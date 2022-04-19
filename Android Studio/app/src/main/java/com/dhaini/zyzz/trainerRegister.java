package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class trainerRegister extends AppCompatActivity{
    EditText dobEditText;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trainer_register);

        // Calendar Picker setup
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dobEditText = findViewById(R.id.DOBInput);
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        trainerRegister.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day+" - "+month+" - "+year;
                dobEditText.setText(date);
            }
        };

        //Spinner Initialization
        List<String> genders = Arrays.asList("Male","Female");
        Spinner genderSpinner = findViewById(R.id.genderInput);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.selected_item_spinner,genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner);
        genderSpinner.setAdapter(adapter);

    }


    public void register(View view) {
        Intent popupmenu = new Intent(this,TrainerMyClients.class);
        startActivity(popupmenu);
    }
}