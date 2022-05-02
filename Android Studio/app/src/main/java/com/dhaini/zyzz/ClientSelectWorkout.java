package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ClientSelectWorkout extends AppCompatActivity {
    private TextView workoutSelectedBannerNameTextView;
    private Workout selectedWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_client_select_workout);

        workoutSelectedBannerNameTextView = (TextView) findViewById(R.id.ClientBannerWorkoutName);


        selectedWorkout = getIntent().getParcelableExtra("Workout");

        workoutSelectedBannerNameTextView.setText(selectedWorkout.getWorkoutName());




    }
}