package com.dhaini.zyzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.Random;

public class TrainerSelectClient extends AppCompatActivity {

    TextView clientNameBanner;

    int imageCardArray[] = {R.drawable.card_image_1, R.drawable.card_image_2, R.drawable.card_image_3, R.drawable.card_image_4};

    private RecyclerView clientSelectedRecyclerView;
    private WorkoutAdapter workoutAdapter;
    private RecyclerView.LayoutManager workoutLayoutManager;

    private String addWorkout_url = "http://10.0.2.2/ZYZZ/workout_register_trainer.php?";

    GetClientWorkoutAPI getClientWorkoutAPI;
    ArrayList<Workout> workoutsList;
    TrainerClients client;
    FloatingActionButton floatingActionButtonAddWorkout;
    DeleteWorkoutAPI deleteWorkoutAPI;

    String addWorkoutName;

    AddClientWorkoutAPI addClientWorkoutAPI;


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


        floatingActionButtonAddWorkout = (FloatingActionButton) findViewById(R.id.floatingAddWorkoutButton);

        floatingActionButtonAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddWorkoutDialog();
            }
        });

    }

    private void openAddWorkoutDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.add_workout_dialog,null);
        final EditText workoutName = (EditText) mView.findViewById(R.id.workoutNameEditText);
        Button addWorkout = (Button) mView.findViewById(R.id.addWorkoutButton);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkoutName = workoutName.getText().toString();
                addClientWorkoutAPI = new AddClientWorkoutAPI();
                addClientWorkoutAPI.execute();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }




    public void openMyClientInfo(View view) {
        Intent intentViewClientInfo = new Intent(TrainerSelectClient.this, TrainerViewClientInfo.class);
        intentViewClientInfo.putExtra("Client", client);
        startActivity(intentViewClientInfo);
    }

    class AddClientWorkoutAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(addWorkout_url);


            BasicNameValuePair addWorkoutNameParam = new BasicNameValuePair("workoutName", addWorkoutName);
            BasicNameValuePair planIDParam = new BasicNameValuePair("planID", client.getClientPlanID());

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(addWorkoutNameParam);
            name_value_pair_list.add(planIDParam);

            try {
                // This is used to send the list with the api in an encoded form entity
                UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(name_value_pair_list);

                // This sets the entity (which holds the list of values) in the http_post object
                http_post.setEntity(url_encoded_form_entity);

                // This gets the response from the post api and returns a string of the response.
                HttpResponse http_response = http_client.execute(http_post);
                InputStream input_stream = http_response.getEntity().getContent();
                InputStreamReader input_stream_reader = new InputStreamReader(input_stream);
                BufferedReader buffered_reader = new BufferedReader(input_stream_reader);
                StringBuilder string_builder = new StringBuilder();
                String buffered_str_chunk = null;
                while ((buffered_str_chunk = buffered_reader.readLine()) != null) {
                    string_builder.append(buffered_str_chunk);
                }
                Log.i("result", string_builder.toString());
                return string_builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                toastMessage(s).show();
                finish();
                startActivity(getIntent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                        Workout workout = new Workout(jsonObject.get("workout_name").toString(), jsonObject.get("workout_id").toString(), jsonObject.get("plan_id").toString(), chosenImage);

                        workoutsList.add(workout);

                    }
                    buildRecyclerView();

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void buildRecyclerView() {
        // Initializing the recyclerView to display the card of each client
        clientSelectedRecyclerView = findViewById(R.id.ClientSelectedRecyclerView);
        workoutLayoutManager = new LinearLayoutManager(TrainerSelectClient.this);
        workoutAdapter = new WorkoutAdapter(workoutsList);
        clientSelectedRecyclerView.setLayoutManager(workoutLayoutManager);
        clientSelectedRecyclerView.setAdapter(workoutAdapter);


        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(TrainerSelectClient.this, TrainerSelectWorkout.class);

                intent.putExtra("Workout", workoutsList.get(position));
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {

                openConfirmationDialog(position);


            }
        });


    }
    private void openConfirmationDialog(int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.confirmation_dialogue,null);
        final TextView confirmationMessageTextView = (TextView) mView.findViewById(R.id.messageConfirmation);
        confirmationMessageTextView.setText("Are you sure you want to delete "+workoutsList.get(position).getWorkoutName()+ " ?");
        Button yes = (Button) mView.findViewById(R.id.YesButton);
        Button no = (Button) mView.findViewById(R.id.NoButton);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteWorkout_url = "http://10.0.2.2/ZYZZ/delete_workout.php?workoutID=" + workoutsList.get(position).getWorkoutID();
                deleteWorkoutAPI = new DeleteWorkoutAPI();
                deleteWorkoutAPI.execute(deleteWorkout_url);
                workoutsList.remove(position);
                workoutAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    public Toast toastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        return toast;
    }


    public class DeleteWorkoutAPI extends AsyncTask<String, Void, String> {
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
                values = values.replaceAll("[\\n]", "");
                toastMessage(values).show();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}
