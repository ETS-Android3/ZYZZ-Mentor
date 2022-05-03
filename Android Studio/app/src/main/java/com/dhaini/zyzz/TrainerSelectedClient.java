package com.dhaini.zyzz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collections;

public class TrainerSelectedClient extends AppCompatActivity {

    private TextView clientNameBanner;
    private FloatingActionButton floatingActionButtonAddWorkout;

    private int imageCardArray[] = {R.drawable.card_image_1, R.drawable.card_image_2, R.drawable.card_image_3, R.drawable.card_image_4};

    private RecyclerView clientSelectedRecyclerView;
    private WorkoutAdapter workoutAdapter;
    private RecyclerView.LayoutManager workoutLayoutManager;

    private ArrayList<Workout> workoutsList = null;
    private TrainerClients client;

    private String addWorkoutName;
    private GetClientWorkoutAPI getClientWorkoutAPI;
    private DeleteWorkoutAPI deleteWorkoutAPI;
    private AddClientWorkoutAPI addClientWorkoutAPI;
    private String addWorkout_url = "http://10.0.2.2/ZYZZ/workout_register_trainer.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trainer_selected_client);

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
        View mView = getLayoutInflater().inflate(R.layout.add_workout_dialog, null);

        EditText workoutName = (EditText) mView.findViewById(R.id.workoutNameEditText);
        Button addWorkout = (Button) mView.findViewById(R.id.addWorkoutButton);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkoutName = workoutName.getText().toString();
                addClientWorkoutAPI = new AddClientWorkoutAPI();
                addClientWorkoutAPI.execute();
                dialog.dismiss();
            }
        });

    }


    public void openMyClientInfo(View view) {
        Intent intentViewClientInfo = new Intent(TrainerSelectedClient.this, TrainerViewClientInfo.class);
        intentViewClientInfo.putExtra("Client", client);
        startActivity(intentViewClientInfo);
    }

    class AddClientWorkoutAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(addWorkout_url);

            int position;
            if (workoutsList == null) {
                position = 0;
            } else {
                position = workoutsList.size();
            }

            BasicNameValuePair addWorkoutNameParam = new BasicNameValuePair("workoutName", addWorkoutName);
            BasicNameValuePair planIDParam = new BasicNameValuePair("planID", client.getClientPlanID());
            BasicNameValuePair positionParam = new BasicNameValuePair("position", String.valueOf(position));

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();

            name_value_pair_list.add(addWorkoutNameParam);
            name_value_pair_list.add(planIDParam);
            name_value_pair_list.add(positionParam);

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
                String getMyClient_url = "http://10.0.2.2/ZYZZ/get_trainer_client_workouts.php?planID=" + client.getClientPlanID();
                getClientWorkoutAPI = new GetClientWorkoutAPI();
                getClientWorkoutAPI.execute(getMyClient_url);

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

                if (values.equalsIgnoreCase("0")) {
                    toastMessage("Workouts none");

                } else {
                    JSONArray clientWorkoutJson = new JSONArray(values);
                    workoutsList = new ArrayList<>();

                    int chosenImageIndex = 0;
                    for (int i = 0; i < clientWorkoutJson.length(); i++) {

                        if (chosenImageIndex> 2) {
                            chosenImageIndex = 0;
                        } else {
                            chosenImageIndex++;
                        }
                        int chosenImage = imageCardArray[chosenImageIndex];

                        JSONObject jsonObject = clientWorkoutJson.getJSONObject(i);
                        Workout workout = new Workout(jsonObject.get("workout_name").toString(),
                                jsonObject.get("workout_id").toString(), jsonObject.get("plan_id").toString(),
                                chosenImage, jsonObject.getInt("position"));

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
        clientSelectedRecyclerView = findViewById(R.id.ClientSelectedRecyclerView);
        workoutLayoutManager = new LinearLayoutManager(TrainerSelectedClient.this);
        workoutAdapter = new WorkoutAdapter(workoutsList);
        clientSelectedRecyclerView.setLayoutManager(workoutLayoutManager);

        ItemTouchHelper.Callback callback = new myItemTouchHelper(workoutAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        workoutAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(clientSelectedRecyclerView);

        clientSelectedRecyclerView.setAdapter(workoutAdapter);

        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(TrainerSelectedClient.this, TrainerSelectedWorkout.class);
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
        View mView = getLayoutInflater().inflate(R.layout.confirmation_dialogue, null);

        TextView confirmationMessageTextView = (TextView) mView.findViewById(R.id.messageConfirmation);
        confirmationMessageTextView.setText("Are you sure you want to delete " + workoutsList.get(position).getWorkoutName() + " ?");
        Button yesButton = (Button) mView.findViewById(R.id.YesButton);
        Button noButton = (Button) mView.findViewById(R.id.NoButton);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteWorkout_url = "http://10.0.2.2/ZYZZ/delete_workout.php?workoutID=" + workoutsList.get(position).getWorkoutID();
                deleteWorkoutAPI = new DeleteWorkoutAPI();
                deleteWorkoutAPI.execute(deleteWorkout_url);

                workoutsList.remove(position);
                workoutAdapter.notifyItemRemoved(position);

                dialog.dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
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
