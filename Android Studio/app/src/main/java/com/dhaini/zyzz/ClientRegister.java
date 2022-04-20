package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ClientRegister extends AppCompatActivity {
    EditText dobEditText;
    EditText fullNameEditText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Spinner genderSpinner;
    String post_url = "http://10.0.2.2/ZYZZ/client_register.php";
    String password ="";
    String username ="";
    String fullName="";
    String email="";
    String dob="";
    String gender="";
    String confirmPassword="";
    DatePickerDialog.OnDateSetListener onDateSetListener;
    clientRegistrationAPI API;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_client_register);

        // Calendar Picker setup
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        fullNameEditText = (EditText) findViewById(R.id.fullNameInputClient);
        emailEditText =(EditText) findViewById(R.id.emailInputClient);
        usernameEditText = (EditText) findViewById(R.id.usernameInputClient);
        passwordEditText = (EditText) findViewById(R.id.passwordInputClient);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordInputClient);

        dobEditText = (EditText)findViewById(R.id.DOBInputClient);
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ClientRegister.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + " - " + month + " - " + year;
                dobEditText.setText(date);
            }
        };

        //Spinner Initialization
        List<String> genders = Arrays.asList("Male", "Female");
        genderSpinner = findViewById(R.id.genderInputClient);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.selected_item_spinner, genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner);
        genderSpinner.setAdapter(adapter);
    }

    public void registerClient(View view) {

        username = usernameEditText.getText().toString();
        fullName = usernameEditText.getText().toString();
        email = emailEditText.getText().toString();
        dob = dobEditText.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if (password.equalsIgnoreCase("") || username.equalsIgnoreCase("")
                || fullName.equalsIgnoreCase("") || email.equalsIgnoreCase("")
                || confirmPassword.equalsIgnoreCase("")   || dob.equalsIgnoreCase("") || gender.equalsIgnoreCase("")) {
            toastMessage("Credential Incomplete!");
        }
        else if(!confirmPassword.equals(password)){
            toastMessage("Passwords doesn't match!");
        }
        else{
            API = new clientRegistrationAPI();
            API.execute();
        }


    }

    public void toastMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    class clientRegistrationAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost(post_url);


            BasicNameValuePair usernameParam = new BasicNameValuePair("username", username);
            BasicNameValuePair passwordParam = new BasicNameValuePair("password", password);
            BasicNameValuePair fullNameParam = new BasicNameValuePair("fullName", fullName);
            BasicNameValuePair emailParam = new BasicNameValuePair("email", email);
            BasicNameValuePair dobParam = new BasicNameValuePair("dob", dob);
            BasicNameValuePair genderParam = new BasicNameValuePair("gender", gender);
            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(fullNameParam);
            name_value_pair_list.add(passwordParam);
            name_value_pair_list.add(usernameParam);
            name_value_pair_list.add(dobParam);
            name_value_pair_list.add(genderParam);
            name_value_pair_list.add(emailParam);


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
                if(s.equalsIgnoreCase("Client registered")){
                    toastMessage("Welcome "+ fullName);
                    Intent popupmenu = new Intent(ClientRegister.this, ClientMyTraining.class);
                    startActivity(popupmenu);
                }
                else{
                    toastMessage(s);
                }
                toastMessage(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}