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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_with_trainer);

        searchTrainerAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchTrainerInput);
        trainerNameTextView = (TextView) findViewById(R.id.trainerNameInput);
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

    public void searchTrainerName(View view) throws JSONException {
        trainerUsernameSelected = searchTrainerAutoCompleteTextView.getText().toString();
        Log.i("Name",trainerUsernameSelected);
        for(int i=0;i<trainerUsernameJsonArray.length();i++) {
            JSONObject jsonObject = trainerUsernameJsonArray.getJSONObject(i);
            if(jsonObject.getString("username").equalsIgnoreCase(trainerUsernameSelected)){
                trainerNameTextView.setText(jsonObject.getString("full_name"));
                break;
            }
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
                // Using formatter function to place a comma every 3 digits
               Log.i("apimessage",s);
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
}