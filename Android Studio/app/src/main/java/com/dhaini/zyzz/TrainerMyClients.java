package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainerMyClients extends AppCompatActivity {
    String trainerUsername;
    private RecyclerView myClientsListRecyclerView;
    private MyClientsAdapter myClientsAdapter;
    private RecyclerView.LayoutManager myClientsLayoutManager;

    GetMyClientsAPI getMyClientsAPI;
    ArrayList<TrainerClients> trainerClientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide the actionbar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trainer_my_clients);
        //Get username from Login activity
        trainerUsername = getIntent().getStringExtra("trainerUsername");

        // Initializing API URL
        String getMyClient_url = "http://10.0.2.2/ZYZZ/get_my_clients_list.php?username=" + trainerUsername;
        getMyClientsAPI = new GetMyClientsAPI();
        getMyClientsAPI.execute(getMyClient_url);

        // Initializing Options Button
        List<String> genders = Arrays.asList("","Add Client","Logout");
        Spinner optionsSpinner = findViewById(R.id.spinnerOption);


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_options_trainer,genders);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner_trainer_options);
        optionsSpinner.setAdapter(adapter);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Add Client")){
                    Intent intentAddClient = new Intent(TrainerMyClients.this,TrainerAddClient.class);
                    optionsSpinner.setSelection(0);
                    intentAddClient.putExtra("TrainerUsername",trainerUsername);
                    startActivity(intentAddClient);

                }
                if(adapterView.getItemAtPosition(i).equals("Logout")){
                    Intent intentLogout = new Intent(TrainerMyClients.this,MainActivity.class);
                    // End all previous activities and go back to main page
                    intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentLogout);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public class GetMyClientsAPI extends AsyncTask<String, Void, String> {
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
                Log.i("Message",values);
                // Getting all the info for each client from the database
                JSONArray myClientJson = new JSONArray(values);


                trainerClientList = new ArrayList<>();
                if(myClientJson.length()==0){

                }
                else{
                    for(int i=0;i<myClientJson.length();i++) {
                        JSONObject jsonObject = myClientJson.getJSONObject(i);
                        // Putting all the info of each client in TrainerClients class object and adding it to the myClientList
                        TrainerClients client = new TrainerClients(jsonObject.getString("client_fullname"),jsonObject.getString("client_id"),
                                jsonObject.getString("plan_id"),jsonObject.getString("day_per_week"),
                                jsonObject.getString("objective"));
                        trainerClientList.add(client);

                    }
                    buildRecyclerView();
                }





                // Getting the buy and sell results rates returned from API 2. Using BigDecimal class in case were dealing with huge numbers.


            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }
    public void buildRecyclerView(){
        // Initializing the recyclerView to display the card of each client
        myClientsListRecyclerView = findViewById(R.id.myClientRecyclerView);
        myClientsLayoutManager = new LinearLayoutManager(TrainerMyClients.this);
        myClientsAdapter = new MyClientsAdapter(trainerClientList);
        myClientsListRecyclerView.setLayoutManager(myClientsLayoutManager);
        myClientsListRecyclerView.setAdapter(myClientsAdapter);

        myClientsAdapter.setOnItemClickListener(new MyClientsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent trainerSelectedClientIntent = new Intent(TrainerMyClients.this, TrainerSelectClient.class);
                Intent intent = trainerSelectedClientIntent.putExtra("Client", trainerClientList.get(position));
                startActivity(trainerSelectedClientIntent);
            }
        });
    }
}