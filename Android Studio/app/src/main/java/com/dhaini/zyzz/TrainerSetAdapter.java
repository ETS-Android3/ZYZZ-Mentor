package com.dhaini.zyzz;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TrainerSetAdapter extends RecyclerView.Adapter<TrainerSetAdapter.TrainerSetViewHolder> {
    private ArrayList<SetTrainer> setTrainerList;
    private OnItemClickListener mListener;
    private Timer timer = new Timer();
    private final long DELAY = 5000; // in ms

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class TrainerSetViewHolder extends RecyclerView.ViewHolder{
        public EditText setNameTextView;
        public EditText setRepsTextView;
        public EditText setWeightTextView;

        public TrainerSetViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            setNameTextView = itemView.findViewById(R.id.setNameInput);
            setRepsTextView = itemView.findViewById(R.id.repsInput);
            setWeightTextView = itemView.findViewById(R.id.weightInput);


        }
    }
    public TrainerSetAdapter(ArrayList<SetTrainer> setTrainerList){
        this.setTrainerList = setTrainerList;
    }
    @NonNull
    @Override
    public TrainerSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_set_card,parent,false);
        TrainerSetViewHolder mCVH = new TrainerSetViewHolder(v,mListener);
        return mCVH;
    }



    @Override
    public void onBindViewHolder(@NonNull TrainerSetViewHolder holder, int position) {
        SetTrainer currentSet = setTrainerList.get(position);
        holder.setNameTextView.setText(currentSet.getSetName());
        holder.setRepsTextView.setText(currentSet.getReps());
        holder.setWeightTextView.setText(currentSet.getWeight());

        //////////////////////// Set Name /////////////////////////
        holder.setNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO: do what you need here (refresh list)
                            // you will probably need to use
                            // runOnUiThread(Runnable action) for some specific
                            // actions
                            currentSet.setSetName(s.toString());
                            String updateSet_url = "http://10.0.2.2/ZYZZ/get_my_clients_list.php?username=" + trainerUsername;
                            getMyClientsAPI = new TrainerMyClients.GetMyClientsAPI();
                            getMyClientsAPI.execute(getMyClient_url);
                            Log.i("Message From set", "Hello");
                        }

                    }, DELAY);
                }
            }
        });

        ///////////////////////////////// Reps////////////////////////////////
        holder.setRepsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            currentSet.setReps(s.toString());
                            Log.i("Message From set", "Hello");
                        }

                    }, DELAY);
                }
            }
        });


        //////////////////////// Weight ////////////////////////////////////////////////
        holder.setWeightTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO: do what you need here (refresh list)
                            // you will probably need to use
                            // runOnUiThread(Runnable action) for some specific
                            // actions
                            currentSet.setWeight(s.toString());
                            Log.i("Message From set", "Hello");
                        }

                    }, DELAY);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(setTrainerList==null){
            return 0;
        }
        return setTrainerList.size();
    }

    public class GetTrainerClientInfo extends AsyncTask<String, Void, String> {
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
                try {
                    Log.i("Message",values);
                    JSONArray jsonArray = new JSONArray(values);
                    JSONObject json = jsonArray.getJSONObject(0);
                    // Getting client info from api and displaying to the ui


                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


}