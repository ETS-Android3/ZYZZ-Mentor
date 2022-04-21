package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginWithTrainer extends AppCompatActivity {
    String post_url = "http://10.0.2.2/ZYZZ/get_list_of_trainer.php";
    getTrainerListAPI APITrainerList;
    AutoCompleteTextView searchTrainerAutoCompleteTextView;
    JSONArray trainerUsernameJsonArray;
    String trainerUsernameSelected;
    TextView trainerNameTextView;
    boolean isValidTrainerUsername = false;
    String clientUsername;
    String clientID;
    sendEmailToTrainerAPI sendEmailAPI;
    TextView myTrainerTextView;
    myTrainerAPI getMyTrainerAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_with_trainer);

        clientUsername = getIntent().getStringExtra("ClientUsername");
        clientID = getIntent().getStringExtra("ClientID");

        searchTrainerAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchTrainerInput);
        trainerNameTextView = (TextView) findViewById(R.id.trainerNameInput);
        myTrainerTextView = (TextView) findViewById(R.id.MyTrainerInput);

        getMyTrainerAPI = new myTrainerAPI();
        getMyTrainerAPI.execute(post_url);

        APITrainerList = new getTrainerListAPI();
        APITrainerList.execute(post_url);


        List<String> confirmation = Arrays.asList("", "Yes", "No");
        Spinner confirmationSpinner = findViewById(R.id.dumpTrainerSpinner);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer, confirmation);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        confirmationSpinner.setAdapter(adapter);

        confirmationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confirmation.set(0, "Are you sure ?");
                if (adapterView.getItemAtPosition(i).equals("Yes")) {
                    confirmation.set(0, "");
                    confirmationSpinner.setSelection(0);

                }
                if (adapterView.getItemAtPosition(i).equals("No")) {
                    confirmation.set(0, "");
                    confirmationSpinner.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                confirmation.set(0, "");
            }
        });
    }
    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }
    public void searchTrainerName(View view) throws JSONException {
        trainerUsernameSelected = searchTrainerAutoCompleteTextView.getText().toString();
        Log.i("Name",trainerUsernameSelected);

        for(int i=0;i<trainerUsernameJsonArray.length();i++) {
            JSONObject jsonObject = trainerUsernameJsonArray.getJSONObject(i);
            if(jsonObject.getString("username").equalsIgnoreCase(trainerUsernameSelected)){
                trainerNameTextView.setText(jsonObject.getString("full_name"));
                isValidTrainerUsername = true;
                break;
            }
        }
        if(isValidTrainerUsername == false){
            toastMessage("Trainer username does not exist");
        }

    }

    public void requestTrainer(View view) {
        if(!myTrainerTextView.getText().toString().equalsIgnoreCase("")){
            toastMessage("You already have a trainer!");
        }
        else if(!isValidTrainerUsername){
            toastMessage("Trainer name not shown exist please enter another");
        }
        else{
            sendEmailAPI = new sendEmailToTrainerAPI();
            sendEmailAPI.execute();
        }

    }

    public class getTrainerListAPI extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            // URL and HTTP initialization to connect to API 1
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                // Retrieve API 1 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 1 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                // Append API 1 content to empty string
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                // Return content from API 1
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // Getting the values returned from API 1 as JSON Object
                trainerUsernameJsonArray = new JSONArray(s);
                List<String> trainerUsernameList = new ArrayList<>();

                for(int i=0;i<trainerUsernameJsonArray.length();i++) {
                    JSONObject jsonObject = trainerUsernameJsonArray.getJSONObject(i);
                        trainerUsernameList.add(jsonObject.getString("username"));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginWithTrainer.this, android.R.layout.simple_list_item_1,trainerUsernameList);
                searchTrainerAutoCompleteTextView.setAdapter(adapter);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class sendEmailToTrainerAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);



            BasicNameValuePair usernameParam = new BasicNameValuePair("clientID", clientID);
            BasicNameValuePair passwordParam = new BasicNameValuePair("trainerUsername", trainerUsernameSelected);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(usernameParam);
            name_value_pair_list.add(passwordParam);

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
                toastMessage(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class myTrainerAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);



            BasicNameValuePair clientIParam = new BasicNameValuePair("clientID", clientID);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(clientIParam);


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
               if(s.equalsIgnoreCase("No trainer")){
                   toastMessage("Search for a trainer");
               }
               else{
                   myTrainerTextView.setText(s);
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}