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

public class TrainerLogin extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TrainerAuthenticationAPI trainerAuthenticationAPI;
    private String post_url = "http://10.0.2.2/ZYZZ/trainer_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trainer_login);

        username = (EditText) findViewById(R.id.usernameInputTrainer);
        password = (EditText) findViewById(R.id.passwordInputTrainer);
    }


    public void createAccountTrainer(View view){
        Intent intent = new Intent(this, TrainerRegister.class);
        startActivity(intent);

    }

    public void loginTrainer(View view) {
        trainerAuthenticationAPI = new TrainerAuthenticationAPI();
        trainerAuthenticationAPI.execute();
    }


    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }


    class TrainerAuthenticationAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);

            String usernameInput, passwordInput;
            usernameInput = username.getText().toString() ;
            passwordInput = password.getText().toString();

            BasicNameValuePair usernameParam = new BasicNameValuePair("Username", usernameInput);
            BasicNameValuePair passwordParam = new BasicNameValuePair("Password", passwordInput);

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
                JSONObject json = new JSONObject(s);

                String status = json.getString("status");

                if (status.equalsIgnoreCase("accepted")) {
                    String name = json.getString("Name");
                    toastMessage("Welcome "+ name).show();

                    Intent intent = new Intent(TrainerLogin.this, TrainerMyClients.class);
                    intent.putExtra("trainerUsername",username.getText().toString());

                    startActivity(intent);
                } else {
                    // If the status!= accepted, the user is notified that something wrong happened
                    toastMessage(status).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
