package com.dhaini.zyzz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientExerciseAdapter extends RecyclerView.Adapter<ClientExerciseAdapter.ClientExerciseViewHolder> {

    private Activity activity;
    private ArrayList<ClientExercise> clientExercisesList;
    private String user;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    private String currentExerciseID;
    private String updatedMyFeedbackToCurrentExercise;

    private UpdateSetAPI updateSetAPI;
    private UpdateClientFeedbackAPI updateClientFeedbackAPI;
    private ClientSetAdapter clientSetAdapter;


    public ClientExerciseAdapter(Activity activity,ArrayList<ClientExercise> clientExercisesList,String user){
        this.activity = activity;
        this.clientExercisesList = clientExercisesList;
        this.user = user;
    }

    public static class ClientExerciseViewHolder extends RecyclerView.ViewHolder{
        public TextView ExerciseNameTextView;
        public TextView commentsTextView;
        public EditText myFeedbackEditText;
        private RecyclerView setRepsWeightInputRecyclerView;

        public ClientExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            ExerciseNameTextView = itemView.findViewById(R.id.ExerciseNameTextView);
            commentsTextView = itemView.findViewById(R.id.commentsTextView);
            myFeedbackEditText = itemView.findViewById(R.id.myFeedbackEditText);
            setRepsWeightInputRecyclerView = itemView.findViewById(R.id.setRepsWeightInputRecyclerView);


        }
    }





    @NonNull
    @Override
    public ClientExerciseAdapter.ClientExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_exercise_card, parent, false);
        ClientExerciseAdapter.ClientExerciseViewHolder mCVH = new ClientExerciseAdapter.ClientExerciseViewHolder(v);
        return mCVH;
    }


    @Override
    public void onBindViewHolder(@NonNull ClientExerciseAdapter.ClientExerciseViewHolder holder, int position) {

       // If the user is a trainer he cant edit the myFeedbackEditText
        if(user.equalsIgnoreCase("Trainer")){
            holder.myFeedbackEditText.setEnabled(false);
        }
        ClientExercise currentExercise = clientExercisesList.get(position);
        int currentExercisePosition = position;

        // Initializing the inner Set RecyclerView
        clientSetAdapter = new ClientSetAdapter(currentExercise.getSetClientList(),user);
        LinearLayoutManager setLayout = new LinearLayoutManager(activity);
        ItemTouchHelper.Callback callback = new myItemTouchHelper(clientSetAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        clientSetAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(holder.setRepsWeightInputRecyclerView);
        holder.setRepsWeightInputRecyclerView.setAdapter(clientSetAdapter);
        holder.setRepsWeightInputRecyclerView.setLayoutManager(setLayout);

        clientSetAdapter.setOnItemClickListener(new ClientSetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position2) {
                openUpdateSetDialog(currentExercisePosition,position2);
            }

        });

        holder.commentsTextView.setText(currentExercise.getComments());
        holder.myFeedbackEditText.setText(currentExercise.getFeedbacks());
        holder.ExerciseNameTextView.setText(currentExercise.getExerciseName());

        // Update the client feedback and save it to the database
        holder.myFeedbackEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        final String edit = s.toString();
                        currentExercise.setComments(edit);
                        currentExerciseID = currentExercise.getExerciseID();
                        updatedMyFeedbackToCurrentExercise = edit;
                        updateClientFeedbackAPI = new UpdateClientFeedbackAPI();
                        updateClientFeedbackAPI.execute();

                    }

                }, DELAY);

            }
        });
    }

    @Override
    public int getItemCount() {
        return clientExercisesList.size();
    }

    private void openUpdateSetDialog(int position1, int position2) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View mView = activity.getLayoutInflater().inflate(R.layout.add_set_dialog, null);

        TextView dialogTitle = (TextView) mView.findViewById(R.id.titleAddWorkoutDialog);
        EditText setNameEditText = (EditText) mView.findViewById(R.id.setNameInputEditText);
        EditText weightEditText = (EditText) mView.findViewById(R.id.weightInputEditText);
        EditText repsEditText = (EditText) mView.findViewById(R.id.repsInputEditText);



        SetClient currentSet = clientExercisesList.get(position1).getSetClientList().get(position2);

        dialogTitle.setText("Change Set");
        setNameEditText.setEnabled(false);
        setNameEditText.setText(currentSet.getSetName());

        weightEditText.setHint(currentSet.getTrainerWeight());
        repsEditText.setHint(currentSet.getTrainerReps());

        if(currentSet.getClientWeight().equalsIgnoreCase("0")){
            weightEditText.setText(currentSet.getTrainerWeight());
        }else{
            weightEditText.setText(currentSet.getClientWeight());
        }

        if(currentSet.getClientReps().equalsIgnoreCase("0")){
            repsEditText.setText(currentSet.getTrainerReps());
        }else{
            repsEditText.setText(currentSet.getClientReps());
        }


        Button updateSetBtn = (Button) mView.findViewById(R.id.addSetButton);
        updateSetBtn.setText("Update Set");

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        updateSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String updatedReps = repsEditText.getText().toString();
                String updatedWeight = weightEditText.getText().toString();
                currentSet.setClientWeight(updatedWeight);
                currentSet.setClientReps(updatedReps);

                String setID = currentSet.getSetID();

                updateSetAPI = new UpdateSetAPI();
                String updateSet_url = "http://10.0.2.2/ZYZZ/client_update_set_reps_and_weight.php?setID=" +
                        setID + "&weight=" + updatedWeight+"&reps="+updatedReps;
                updateSetAPI.execute(updateSet_url);
                notifyItemChanged(position1);
                dialog.dismiss();
            }
        });

    }

    class UpdateClientFeedbackAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost("http://10.0.2.2/ZYZZ/client_update_feedback.php?");

            BasicNameValuePair exerciseIDParam = new BasicNameValuePair("exerciseID", currentExerciseID);
            BasicNameValuePair feedbackExerciseToChangeParam = new BasicNameValuePair("feedback", updatedMyFeedbackToCurrentExercise);


            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(exerciseIDParam);
            name_value_pair_list.add(feedbackExerciseToChangeParam);

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
                Log.i("result", string_builder.toString());
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
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class UpdateSetAPI extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            // URL and HTTP initialization to connect to API 2
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
                return;

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}
