package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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
import java.util.ArrayList;

public class ClientSelectedWorkout extends AppCompatActivity {
    private TextView workoutSelectedBannerNameTextView;
    private Workout workoutSelected;
    private ArrayList<ClientExercise> clientExerciseList = new ArrayList<>();

    private String getExercise_url = "http://10.0.2.2/ZYZZ/get_exercise_and_sets.php";
    private GetExercisesAPI getExercisesAPI;

    private RecyclerView WorkoutSelectedRecyclerView;
    private ClientExerciseAdapter clientExerciseAdapter;
    private RecyclerView.LayoutManager clientExerciseLayoutManager;

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_client_selected_workout);

        workoutSelectedBannerNameTextView = (TextView) findViewById(R.id.ClientBannerWorkoutName);

        workoutSelected = getIntent().getParcelableExtra("Workout");
        user = getIntent().getStringExtra("User");

        workoutSelectedBannerNameTextView.setText(workoutSelected.getWorkoutName());

        getExercisesAPI = new GetExercisesAPI();
        getExercisesAPI.execute();

    }

    class GetExercisesAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(getExercise_url);

            BasicNameValuePair workoutIDParam = new BasicNameValuePair("workoutID", workoutSelected.getWorkoutID());

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(workoutIDParam);

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
                // Getting all the info for each client from the database
                JSONArray exerciseJsonArray = new JSONArray(s);

                if (exerciseJsonArray.length() == 0) {

                } else {

                    clientExerciseList = new ArrayList<>();

                    for (int i = 0; i < exerciseJsonArray.length(); i++) {

                        JSONObject ExerciseJsonObject = exerciseJsonArray.getJSONObject(i);

                        JSONArray setJsonArray = new JSONArray(ExerciseJsonObject.getString("0"));

                        ArrayList<SetClient> setClientList = new ArrayList<>();

                        for (int j = 0; j < setJsonArray.length(); j++) {

                            JSONObject setJsonObject = setJsonArray.getJSONObject(j);
                            String setName = setJsonObject.getString("set_name");

                            if (setName.equalsIgnoreCase("null")) {
                                break;
                            } else {
                                String exerciseID = setJsonObject.getString("exercise_id");
                                String trainerReps = setJsonObject.getString("reps");
                                String trainerWeight = setJsonObject.getString("weight");
                                String setID = setJsonObject.getString("set_id");
                                String clientReps = setJsonObject.getString("client_reps");
                                String clientWeight = setJsonObject.getString("client_weight");
                                int completed = Integer.valueOf(setJsonObject.getString("complete"));

                                SetClient setClient = new SetClient(setName, trainerReps, trainerWeight, clientReps,
                                        clientWeight, exerciseID, setID, completed);

                                setClientList.add(setClient);
                            }
                        }

                        String exerciseName = ExerciseJsonObject.getString("exercise_name");
                        String exerciseID = ExerciseJsonObject.getString("exercise_id");
                        String comments = ExerciseJsonObject.getString("comments");
                        String workoutID = ExerciseJsonObject.getString("workout_id");
                        String feedbacks = ExerciseJsonObject.getString("client_feedback");

                        if (feedbacks.equalsIgnoreCase("null")) {
                            feedbacks = "";
                        }

                        int workoutPosition = Integer.valueOf(ExerciseJsonObject.getString("position"));

                        ClientExercise clientExercise = new ClientExercise(exerciseID, exerciseName, comments, feedbacks,
                                workoutID, workoutPosition, setClientList);

                        clientExerciseList.add(clientExercise);

                    }

                    buildRecyclerView();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void buildRecyclerView() {
            WorkoutSelectedRecyclerView = findViewById(R.id.ClientWorkoutSelectedRecyclerView);
            clientExerciseLayoutManager = new LinearLayoutManager(ClientSelectedWorkout.this);
            clientExerciseAdapter = new ClientExerciseAdapter(ClientSelectedWorkout.this, clientExerciseList, user);
            WorkoutSelectedRecyclerView.setLayoutManager(clientExerciseLayoutManager);
            WorkoutSelectedRecyclerView.setAdapter(clientExerciseAdapter);

        }
    }
}