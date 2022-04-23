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
import android.widget.TextView;
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

public class TrainerSelectClient extends AppCompatActivity {
    GetClientWorkoutAPI getClientWorkoutAPI;
    TextView clientNameBanner;
    int imageCardArray[] = {R.drawable.card_image_1,R.drawable.card_image_2,R.drawable.card_image_3,R.drawable.card_image_4};
    private RecyclerView clientSelectedRecyclerView;
    private WorkoutAdapter workoutAdapter;
    private RecyclerView.LayoutManager workoutLayoutManager;

    ArrayList<ClientWorkout> clientWorkoutsList;
    TrainerClients client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trainer_select_client);

        client = getIntent().getParcelableExtra("Client");
        clientNameBanner = (TextView) findViewById(R.id.clientNameBanner);
        clientNameBanner.setText(client.getClientFullName());

        // Initializing API URL
        String getMyClient_url = "http://10.0.2.2/ZYZZ/get_trainer_client_workouts.php?planID=" + client.getClientPlanID();
        getClientWorkoutAPI = new GetClientWorkoutAPI();
        getClientWorkoutAPI.execute(getMyClient_url);

        // Initializing Options Button
        List<String> genders = Arrays.asList("", "Add Workout Day", "View Client Info");
        Spinner optionsSpinner = findViewById(R.id.spinnerOptionsSelectClient);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer, genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        optionsSpinner.setAdapter(adapter);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Add Workout Day")) {
                    Intent intentAddClient = new Intent(TrainerSelectClient.this, TrainerAddClient.class);
                    optionsSpinner.setSelection(0);
                    startActivity(intentAddClient);

                }
                if (adapterView.getItemAtPosition(i).equals("View Client Info")) {
                    Intent intentViewClientInfo = new Intent(TrainerSelectClient.this, TrainerViewClientInfo.class);
                    intentViewClientInfo.putExtra("Client",client);
                    optionsSpinner.setSelection(0);
                    startActivity(intentViewClientInfo);

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

                // Getting all the info for each client from the database
                JSONArray clientWorkoutJson = new JSONArray(values);

                if (clientWorkoutJson.length() == 0) {

                } else {

                    clientWorkoutsList = new ArrayList<>();

                    for (int i = 0; i < clientWorkoutJson.length(); i++) {
                        // Set a random Image that are in the array to card Image
                        Random r = new Random();
                        int chosenImageIndex = r.nextInt((3 - 0) + 1) + 0;
                        int chosenImage = imageCardArray[chosenImageIndex];
                        JSONObject jsonObject = clientWorkoutJson.getJSONObject(i);
                        ClientWorkout clientWorkout = new ClientWorkout(jsonObject.get("workout_name").toString(),jsonObject.get("workout_id").toString(),jsonObject.get("plan_id").toString(),chosenImage);

                        clientWorkoutsList.add(clientWorkout);

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
        clientSelectedRecyclerView = findViewById(R.id.ClientSelectedRecyclerView);
        workoutLayoutManager = new LinearLayoutManager(TrainerSelectClient.this);
        workoutAdapter = new WorkoutAdapter(clientWorkoutsList);
        clientSelectedRecyclerView.setLayoutManager(workoutLayoutManager);
        clientSelectedRecyclerView.setAdapter(workoutAdapter);

        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                toastMessage("Clicked").show();
            }
        });
    }
    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }

}
