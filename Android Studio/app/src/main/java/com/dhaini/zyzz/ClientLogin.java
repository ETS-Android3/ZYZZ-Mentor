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

public class ClientLogin extends AppCompatActivity {
    EditText usernameInputEditText;
    EditText passwordInputEditText;
    String clientUsername;
    String clientPassword;
    clientAuthenticationAPI  API;
    String post_url = "http://10.0.2.2/"+"ZYZZ/client_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_client_login);
        usernameInputEditText = (EditText) findViewById(R.id.usernameInputClient);
        passwordInputEditText = (EditText) findViewById(R.id.passwordInputClient);
    }


    public void createAccountClient(View view){ //Pop up window to show the PL logo and a small comment
        Intent popupmenu = new Intent(this, ClientRegister.class);
        startActivity(popupmenu);

    }

    public void loginClient(View view) {
        API = new clientAuthenticationAPI();
        API.execute();
    }


    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }


    class clientAuthenticationAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);


            clientUsername = usernameInputEditText.getText().toString() ;
            clientPassword = passwordInputEditText.getText().toString();

            BasicNameValuePair usernameParam = new BasicNameValuePair("Username", clientUsername);
            BasicNameValuePair passwordParam = new BasicNameValuePair("Password", clientPassword);

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
                JSONObject json = new JSONObject(s);
                // Getting the status that was returned in the json response from the post api
                String status = json.getString("status");


                if (status.equalsIgnoreCase("accepted")) {
                    String name = json.getString("Name");
                    String clientID = json.getString("clientID");
                    String clientPlanID = json.getString("planID");
                    toastMessage("Welcome "+ name).show();

                    Client client = new Client(clientUsername,clientID,clientPlanID);

                    Intent intent = new Intent(ClientLogin.this, ClientMyTraining.class);
                    intent.putExtra("Client",client);
                    startActivity(intent);
                } else {
                    // If the status != accepted, the user is notified that something wrong happened
                    toastMessage(status).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
