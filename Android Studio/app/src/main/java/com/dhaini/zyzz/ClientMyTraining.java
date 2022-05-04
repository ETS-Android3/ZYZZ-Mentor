package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import java.util.Collections;
import java.util.List;

public class ClientMyTraining extends AppCompatActivity {
    private GetClientWorkoutAPI getClientWorkoutAPI;

    private int imageCardArray[] = {R.drawable.card_image_1, R.drawable.card_image_2, R.drawable.card_image_3, R.drawable.card_image_4};

    private RecyclerView clientMyTrainingRecyclerView;
    private ClientWorkoutAdapter clientWorkoutAdapter;
    private RecyclerView.LayoutManager workoutLayoutManager;

    private ImageButton clientOptionImgButton;

    private ArrayList<Workout> workoutsList;

    private Client client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_client_my_training);

        client = getIntent().getParcelableExtra("Client");

        // Initializing API URL
        String getMyClient_url = "http://10.0.2.2/ZYZZ/get_trainer_client_workouts.php?planID=" + client.getClientPlanID();
        getClientWorkoutAPI = new GetClientWorkoutAPI();
        getClientWorkoutAPI.execute(getMyClient_url);

        clientOptionImgButton = (ImageButton) findViewById(R.id.clientOptionButton);


        clientOptionImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientOptionImgButton.animate().rotation(clientOptionImgButton.getRotation()-360).setDuration(500).start();
                openClientOptionDialog();
            }
        });

    }

    private void openClientOptionDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.client_options_dialog, null);

        ImageButton myTrainingBtn = (ImageButton) mView.findViewById(R.id.myTrainingImgBtn);
        ImageButton loginWithTrainerBtn = (ImageButton) mView.findViewById(R.id.loginWithTrainerOptionButton);
        ImageButton logoutImgBtn = (ImageButton) mView.findViewById(R.id.logoutOptionBtn);
        ImageButton myInfoBtn = (ImageButton) mView.findViewById(R.id.myInfoOptionButton);


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        myInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentClientInfo = new Intent(ClientMyTraining.this, MyInfoClient.class);
                intentClientInfo.putExtra("ClientUsername", client.getClientUsername());

                startActivity(intentClientInfo);

                dialog.dismiss();
            }
        });

        myTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientOptionImgButton.animate().rotation(clientOptionImgButton.getRotation()-360).setDuration(500).start();
                dialog.dismiss();
            }
        });

        loginWithTrainerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentClientInfo = new Intent(ClientMyTraining.this, LoginWithTrainer.class);
                intentClientInfo.putExtra("Client", client);

                startActivity(intentClientInfo);
                dialog.dismiss();
            }
        });

        logoutImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogout = new Intent(ClientMyTraining.this,MainActivity.class);
                // End all previous activities and go back to main page
                intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogout);
                dialog.dismiss();
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
                Log.i("message", values);
                // Getting all the info for each client from the database


                if (values.equalsIgnoreCase("0")) {
                    toastMessage("Workout none");

                } else {
                    JSONArray clientWorkoutJson = new JSONArray(values);
                    workoutsList = new ArrayList<>();

                    // Choose an image to put it in the card Workout
                    int chosenImageIndex = 0;
                    for (int i = 0; i < clientWorkoutJson.length(); i++) {

                        if (chosenImageIndex > 2) {
                            chosenImageIndex = 0;
                        } else {
                            chosenImageIndex++;
                        }
                        int chosenImage = imageCardArray[chosenImageIndex];

                        JSONObject jsonObject = clientWorkoutJson.getJSONObject(i);

                        Workout workout = new Workout(jsonObject.get("workout_name").toString(), jsonObject.get("workout_id").toString(),
                                jsonObject.get("plan_id").toString(), chosenImage, jsonObject.getInt("position"));

                        workoutsList.add(workout);

                    }
                    Collections.sort(workoutsList, Workout.workoutPosition);


                }
                buildRecyclerView();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void buildRecyclerView() {
        clientMyTrainingRecyclerView = findViewById(R.id.ClientMyTrainingRecyclerView);
        workoutLayoutManager = new LinearLayoutManager(ClientMyTraining.this);
        clientWorkoutAdapter = new ClientWorkoutAdapter(workoutsList);
        clientMyTrainingRecyclerView.setLayoutManager(workoutLayoutManager);
        clientMyTrainingRecyclerView.setAdapter(clientWorkoutAdapter);

        clientWorkoutAdapter.setOnItemClickListener(new ClientWorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ClientMyTraining.this, ClientSelectedWorkout.class);
                intent.putExtra("Workout", workoutsList.get(position));
                intent.putExtra("User", "Client");
                startActivity(intent);
            }
        });
    }

    public Toast toastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        return toast;
    }
}