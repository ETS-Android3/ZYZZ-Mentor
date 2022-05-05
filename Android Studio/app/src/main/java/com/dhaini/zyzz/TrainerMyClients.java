package com.dhaini.zyzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainerMyClients extends AppCompatActivity {
    private String trainerUsername;
    private RecyclerView myClientsListRecyclerView;
    private MyClientsAdapter myClientsAdapter;
    private RecyclerView.LayoutManager myClientsLayoutManager;

    private GetMyClientsAPI getMyClientsAPI;
    private ArrayList<TrainerClients> trainerClientList;
    private ImageButton trainerOptionImageButton;


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

        trainerOptionImageButton = (ImageButton) findViewById(R.id.trainerOptionButton);

        trainerOptionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainerOptionImageButton.animate().rotation(trainerOptionImageButton.getRotation()-360).setDuration(500).start();
                openTrainerOptionDialog();
            }
        });

    }

    public void buildRecyclerView(){
        myClientsListRecyclerView = findViewById(R.id.myClientRecyclerView);
        myClientsLayoutManager = new LinearLayoutManager(TrainerMyClients.this);
        myClientsAdapter = new MyClientsAdapter(trainerClientList);
        myClientsListRecyclerView.setLayoutManager(myClientsLayoutManager);
        myClientsListRecyclerView.setAdapter(myClientsAdapter);
        myClientsAdapter.setOnItemClickListener(new MyClientsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent trainerSelectedClientIntent = new Intent(TrainerMyClients.this, TrainerSelectedClient.class);
                trainerSelectedClientIntent.putExtra("Client", trainerClientList.get(position));
                startActivity(trainerSelectedClientIntent);
            }
        });
    }

    private void openTrainerOptionDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.trainer_options_dialog, null);

      ImageButton myClientsImgBtn = (ImageButton) mView.findViewById(R.id.myClientsOptionButton);
        ImageButton addClientImgBtn = (ImageButton) mView.findViewById(R.id.addClientOptionButton);
        ImageButton logoutImgBtn = (ImageButton) mView.findViewById(R.id.logoutOptionBtn);


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        addClientImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddClient = new Intent(TrainerMyClients.this,TrainerAddClient.class);
                intentAddClient.putExtra("TrainerUsername",trainerUsername);
                startActivity(intentAddClient);

                dialog.dismiss();
            }
        });

        myClientsImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainerOptionImageButton.animate().rotation(trainerOptionImageButton.getRotation()-360).setDuration(500).start();
                dialog.dismiss();
            }
        });

        logoutImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogout = new Intent(TrainerMyClients.this,MainActivity.class);
                // End all previous activities and go back to main page
                intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLogout);
                dialog.dismiss();
            }
        });

    }

    public class GetMyClientsAPI extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String values) {
            super.onPostExecute(values);
            try {
                // Getting all the info for each client from the database
                JSONArray myClientJson = new JSONArray(values);

                trainerClientList = new ArrayList<>();
                if(myClientJson.length()==0){

                }
                else{
                    for(int i=0;i<myClientJson.length();i++) {
                        JSONObject jsonObject = myClientJson.getJSONObject(i);
                        // Putting all the info of each client in TrainerClients class object and adding it to the myClientList
                        TrainerClients client = new TrainerClients(jsonObject.getString("client_fullname"),
                                jsonObject.getString("client_id"),
                                jsonObject.getString("plan_id"));

                        trainerClientList.add(client);

                    }
                    buildRecyclerView();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
    public Toast toastMessage(String message){
        Toast toast= Toast.makeText(getApplicationContext(),message,Toast. LENGTH_SHORT);
        return toast;
    }


}