package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyInfoClient extends AppCompatActivity {
    String clientUsername;
    String post_url = "http://10.0.2.2/ZYZZ/get_client_info.php";
    String post_url2 = "http://10.0.2.2/ZYZZ/client_register_info.php";
    TextView ageTextView;
    EditText heightEditText;
    EditText weightEditText;
    EditText pastInjuriesEditText;
    EditText healthIssuesEditText;
    EditText daysWeekEditText;
    EditText hoursDayEditText;
    EditText trainingPreferenceEditText;
    EditText objectiveEditText;
    String height;
    String weight;
    String pastInjuries;
    String healthIssues;
    String daysWeek;
    String hoursDay;
    String trainingPreference;
    String objectives;
    getClientInfoAPI get_clientInfoAPI;
    clientUpdateInfoAPI updateClientInfoAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_info_client);

        clientUsername = getIntent().getStringExtra("ClientUsername");
        Log.i("Message info",clientUsername);

        ageTextView =(TextView) findViewById(R.id.AgeInput);
        heightEditText = (EditText) findViewById(R.id.heightInput);
        weightEditText = (EditText) findViewById(R.id.WeightInput);
        pastInjuriesEditText = (EditText) findViewById(R.id.pastInjuriesInput);
        healthIssuesEditText = (EditText) findViewById(R.id.healthIssuesInput);
        daysWeekEditText = (EditText) findViewById(R.id.dayPerWeekInput);
        hoursDayEditText = (EditText) findViewById(R.id.hoursPerDayInput);
        trainingPreferenceEditText = (EditText) findViewById(R.id.trainingPreferencesInput);
        objectiveEditText = (EditText) findViewById(R.id.objectiveInput);

        get_clientInfoAPI = new getClientInfoAPI();
        get_clientInfoAPI.execute();
    }

    public void updateMyInfo(View view) {
        height = heightEditText.getText().toString();
        weight  = weightEditText.getText().toString();
        pastInjuries   = pastInjuriesEditText.getText().toString();
         healthIssues = healthIssuesEditText.getText().toString();
        daysWeek  = daysWeekEditText.getText().toString();
        hoursDay  = hoursDayEditText.getText().toString();
        trainingPreference = trainingPreferenceEditText.getText().toString();
        objectives = objectiveEditText.getText().toString();

        updateClientInfoAPI = new clientUpdateInfoAPI();
        updateClientInfoAPI.execute();
    }


    class clientUpdateInfoAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url2);

            // Getting client info from ui and send update info to the backend
            BasicNameValuePair usernameParam = new BasicNameValuePair("username", clientUsername);
            BasicNameValuePair heightParam = new BasicNameValuePair("height", height);
            BasicNameValuePair weightParam = new BasicNameValuePair("weight", weight);
            BasicNameValuePair pastInjuriesParam = new BasicNameValuePair("pastInjuries", pastInjuries);
            BasicNameValuePair healthIssuesParam = new BasicNameValuePair("healthIssues", healthIssues);
            BasicNameValuePair  daysWeekParam= new BasicNameValuePair("daysPerWeek", daysWeek);
            BasicNameValuePair hoursDayParam = new BasicNameValuePair("hoursPerDay", hoursDay);
            BasicNameValuePair trainingPreferenceParam = new BasicNameValuePair("trainingPreference", trainingPreference);
            BasicNameValuePair objectivesParam = new BasicNameValuePair("objectives", objectives);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();

            name_value_pair_list.add(usernameParam);
            name_value_pair_list.add(healthIssuesParam);
            name_value_pair_list.add(heightParam);
            name_value_pair_list.add(weightParam);
            name_value_pair_list.add(pastInjuriesParam);
            name_value_pair_list.add(daysWeekParam);
            name_value_pair_list.add(hoursDayParam);
            name_value_pair_list.add(trainingPreferenceParam);
            name_value_pair_list.add(objectivesParam);

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class getClientInfoAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);

            BasicNameValuePair usernameParam = new BasicNameValuePair("username", clientUsername);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(usernameParam);

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
                Log.i("result",string_builder.toString());
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
                // when the client info does not exist in the database
                if(s.equalsIgnoreCase("Please register your info!")){
                   toastMessage(s).show();
                }
                else{
                    JSONObject json = new JSONObject(s);

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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Toast toastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        return toast;
    }
}