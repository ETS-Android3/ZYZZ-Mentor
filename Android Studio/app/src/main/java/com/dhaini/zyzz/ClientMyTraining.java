package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClientMyTraining extends AppCompatActivity {
    GetClientWorkoutAPI getClientWorkoutAPI;
    int imageCardArray[] = {R.drawable.card_image_1,R.drawable.card_image_2,R.drawable.card_image_3,R.drawable.card_image_4};
    private RecyclerView clientMyTrainingRecyclerView;
    private WorkoutAdapter workoutAdapter;
    private RecyclerView.LayoutManager workoutLayoutManager;

    ArrayList<Workout> workoutsList;

    Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_client_my_training);

        client = getIntent().getParcelableExtra("Client");

        // Initializing API URL
        String getMyClient_url = "http://10.0.2.2/ZYZZ/get_trainer_client_workouts.php?planID=" + client.getClientPlanID();
        getClientWorkoutAPI = new GetClientWorkoutAPI();
        getClientWorkoutAPI.execute(getMyClient_url);

        List<String> genders = Arrays.asList("","My Info","Login with Trainer","Logout");
        Spinner optionsSpinner = findViewById(R.id.spinnerOptionClient);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer,genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        optionsSpinner.setAdapter(adapter);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("My Info")){
                    Intent intentClientInfo = new Intent(ClientMyTraining.this,MyInfoClient.class);
                    optionsSpinner.setSelection(0);
                    intentClientInfo.putExtra("ClientUsername",client.getClientUsername());

                    startActivity(intentClientInfo);

                }
                if(adapterView.getItemAtPosition(i).equals("Login with Trainer")){
                    Intent intentClientInfo = new Intent(ClientMyTraining.this,LoginWithTrainer.class);
                    optionsSpinner.setSelection(0);
                    intentClientInfo.putExtra("Client",client);

                    startActivity(intentClientInfo);

                }
                if(adapterView.getItemAtPosition(i).equals("Logout")){
                    Intent intentLogout = new Intent(ClientMyTraining.this,MainActivity.class);
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
    public class GetClientWorkoutAPI extends AsyncTask<String, Void, String> {
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
                Log.i("message",values);
                // Getting all the info for each client from the database
                JSONArray clientWorkoutJson = new JSONArray(values);

                if (clientWorkoutJson.length() == 0) {

                } else {

                    workoutsList = new ArrayList<>();

                    for (int i = 0; i < clientWorkoutJson.length(); i++) {
                        // Set a random Image that are in the array to card Image
                        Random r = new Random();
                        int chosenImageIndex = r.nextInt((3 - 0) + 1) + 0;
                        int chosenImage = imageCardArray[chosenImageIndex];
                        JSONObject jsonObject = clientWorkoutJson.getJSONObject(i);
                        Workout workout = new Workout(jsonObject.get("workout_name").toString(),jsonObject.get("workout_id").toString(),jsonObject.get("plan_id").toString(),chosenImage);

                        workoutsList.add(workout);

                    }
                    buildRecyclerView();

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
    public void buildRecyclerView(){
        // Initializing the recyclerView to display the card of each client
        clientMyTrainingRecyclerView = findViewById(R.id.ClientMyTrainingRecyclerView);
        workoutLayoutManager = new LinearLayoutManager(ClientMyTraining.this);
        workoutAdapter = new WorkoutAdapter(workoutsList);
        clientMyTrainingRecyclerView.setLayoutManager(workoutLayoutManager);
        clientMyTrainingRecyclerView.setAdapter(workoutAdapter);

        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                toastMessage("Clicked").show();
            }

            @Override
            public void onDeleteClick(int position) {

            }
        });
    }
    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }
}