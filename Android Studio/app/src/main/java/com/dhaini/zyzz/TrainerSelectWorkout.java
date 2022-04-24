package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Random;

public class TrainerSelectWorkout extends AppCompatActivity {
    TextView workoutNameBannerTextView;
    String post_url = "http://10.0.2.2/ZYZZ/get_exercise_and_sets.php";
    ArrayList<TrainerExercise> trainerExerciseList;
    Workout workoutSelected;
    private RecyclerView WorkoutSelectedRecyclerView;
    private TrainerExerciseAdapter trainerExerciseAdapter;
    private RecyclerView.LayoutManager trainerExerciseLayoutManager;

    GetExercisesAPI getExercisesAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_trainer_select_workout);

        workoutSelected = getIntent().getParcelableExtra("Workout");


        workoutNameBannerTextView = (TextView) findViewById(R.id.bannerWorkoutName);
        workoutNameBannerTextView.setText(workoutSelected.getWorkoutName());

        getExercisesAPI = new GetExercisesAPI();
        getExercisesAPI.execute();


    }

    class GetExercisesAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);

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
                Log.i("message", s);
                // Getting all the info for each client from the database
                JSONArray exerciseJsonArray = new JSONArray(s);

                if (exerciseJsonArray.length() == 0) {

                } else {

                    trainerExerciseList = new ArrayList<>();

                    for (int i = 0; i < exerciseJsonArray.length(); i++) {
                        // Set a random Image that are in the array to card Image

                        JSONObject ExerciseJsonObject = exerciseJsonArray.getJSONObject(i);
                        Log.i("Array",ExerciseJsonObject.get("0").toString());
                        JSONArray setJsonArray = new JSONArray(ExerciseJsonObject.getString("0"));

                        ArrayList<SetTrainer> setTrainerList =  new ArrayList<>();

                        for(int j =0;j<setJsonArray.length();j++){
                            JSONObject setJsonObject = setJsonArray.getJSONObject(j);
                            String setName = setJsonObject.getString("set_name");

                            if(setName.equalsIgnoreCase("null")){
                                break;
                            }

                            else{
                                String exerciseID = setJsonObject.getString("exercise_id");
                                String reps = setJsonObject.getString("reps");
                                String weight = setJsonObject.getString("weight");
                                SetTrainer setTrainer = new SetTrainer(setName,reps,weight,exerciseID);
                                setTrainerList.add(setTrainer);
                            }
                        }

                        String exerciseName = ExerciseJsonObject.getString("exercise_name");
                        String exerciseID = ExerciseJsonObject.getString("exercise_id");
                        String comments = ExerciseJsonObject.getString("comments");
                        String workoutID = ExerciseJsonObject.getString("workout_id");

                        TrainerExercise trainerExercise = new TrainerExercise(exerciseID,exerciseName,comments,workoutID,setTrainerList);
                        trainerExerciseList.add(trainerExercise);

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
        WorkoutSelectedRecyclerView = findViewById(R.id.WorkoutSelectedRecyclerView);
        trainerExerciseLayoutManager = new LinearLayoutManager(TrainerSelectWorkout.this);
        trainerExerciseAdapter = new TrainerExerciseAdapter(trainerExerciseList,this);
        WorkoutSelectedRecyclerView.setLayoutManager(trainerExerciseLayoutManager);
        WorkoutSelectedRecyclerView.setAdapter(trainerExerciseAdapter);

       trainerExerciseAdapter.setOnItemClickListener(new TrainerExerciseAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {

           }
       });
    }
}