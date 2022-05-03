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

public class TrainerAddClient extends AppCompatActivity {
    EditText clientID_EditText;
    TextView clientName_TextView;

    String clientID;
    String trainerUsername;

    String registerClient_url = "http://10.0.2.2/ZYZZ/login_trainer_client.php";
    String searchClientName_url = "http://10.0.2.2/ZYZZ/search_client_name.php";

    registerClientAPI registerClient_api;
    SearchClientNameAPI searchClientName_api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_trainer_add_client);

        trainerUsername = getIntent().getStringExtra("TrainerUsername");

        clientID_EditText = (EditText) findViewById(R.id.clientIDinputTrainer);
        clientName_TextView = (TextView) findViewById(R.id.clientNameAddClient);

    }

    public void trainerRegisterClient(View view) {
        clientID = clientID_EditText.getText().toString();

        if (clientID.equalsIgnoreCase("")) {
            toastMessage("Please enter a Client ID");
        } else {
            registerClient_api = new registerClientAPI();
            registerClient_api.execute();
        }


    }

    public Toast toastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        return toast;
    }

    public void searchClientName(View view) {
        clientID = clientID_EditText.getText().toString();
        searchClientName_api = new SearchClientNameAPI();
        searchClientName_api.execute();
    }

    class registerClientAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(registerClient_url);

            BasicNameValuePair clientIDParam = new BasicNameValuePair("clientID", clientID);
            BasicNameValuePair trainerUsernameParam = new BasicNameValuePair("trainerUsername", trainerUsername);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(clientIDParam);
            name_value_pair_list.add(trainerUsernameParam);

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
                if (s.equalsIgnoreCase("client registered successfully!")) {
                    clientID_EditText.setText("");
                }
                toastMessage(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SearchClientNameAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(searchClientName_url);

            BasicNameValuePair clientIDParam = new BasicNameValuePair("clientID", clientID);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(clientIDParam);

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
                clientName_TextView.setText(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}