package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class TrainerViewClientInfo extends AppCompatActivity {
    TextView ageTextView;
    TextView heightEditText;
    TextView weightEditText;
    TextView pastInjuriesEditText;
    TextView healthIssuesEditText;
    TextView daysWeekEditText;
    TextView hoursDayEditText;
    TextView trainingPreferenceEditText;
    TextView objectiveEditText;
    GetTrainerClientInfo get_clientInfoAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trainer_view_client_info);

        TrainerClients client = getIntent().getParcelableExtra("Client");

        ageTextView = (TextView) findViewById(R.id.AgeInputTrainerClientMyInfo);
        heightEditText = (TextView) findViewById(R.id.heightInputTrainerClientMyInfo);
        weightEditText = (TextView) findViewById(R.id.WeightInputTrainerClientMyInfo);
        pastInjuriesEditText = (TextView) findViewById(R.id.pastInjuriesInputTrainerClientMyInfo);
        healthIssuesEditText = (TextView) findViewById(R.id.healthIssuesInputTrainerClientMyInfo);
        daysWeekEditText = (TextView) findViewById(R.id.dayPerWeekInputTrainerClientMyInfo);
        hoursDayEditText = (TextView) findViewById(R.id.hoursPerDayInputTrainerClientMyInfo);
        trainingPreferenceEditText = (TextView) findViewById(R.id.trainingPreferencesInputTrainerClientMyInfo);
        objectiveEditText = (TextView) findViewById(R.id.objectiveInputTrainerClientMyInfo);



        String getMyClientInfo_url = "http://10.0.2.2/ZYZZ/get_trainer_client_info.php?clientID=" + client.getClientID();
        get_clientInfoAPI = new GetTrainerClientInfo();
        get_clientInfoAPI.execute(getMyClientInfo_url);


    }

    public class GetTrainerClientInfo extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            // URL and HTTP initialization to connect to API 2
            URL url;
            HttpURLConnection http;

            try {
                // Connect to API 2
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                // Retrieve API 2 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 2 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                // Return content from API 2
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String values) {
            super.onPostExecute(values);
            try {
                try {
                    Log.i("Message",values);
                    JSONArray jsonArray = new JSONArray(values);
                    JSONObject json = jsonArray.getJSONObject(0);
                    // Getting client info from api and displaying to the ui
                    ageTextView.setText(json.get("age").toString());
                    heightEditText.setText(json.get("height").toString());
                    weightEditText.setText(json.get("weight").toString());
                    pastInjuriesEditText.setText(json.get("past_injuries").toString());
                    healthIssuesEditText.setText(json.get("health_issues").toString());
                    daysWeekEditText.setText(json.get("days_per_week").toString());
                    hoursDayEditText.setText(json.get("hours_per_day").toString());
                    trainingPreferenceEditText.setText(json.get("training_preference").toString());
                    objectiveEditText.setText(json.get("objectives").toString());

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}