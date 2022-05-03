package com.dhaini.zyzz;

import android.app.Activity;
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

    private UpdateClientFeedbackAPI updateClientFeedbackAPI;



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

        // Initializing the inner Set RecyclerView
        ClientSetAdapter clientSetAdapter = new ClientSetAdapter(currentExercise.getSetClientList(),user);
        LinearLayoutManager setLayout = new LinearLayoutManager(activity);
        ItemTouchHelper.Callback callback = new myItemTouchHelper(clientSetAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        clientSetAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(holder.setRepsWeightInputRecyclerView);
        holder.setRepsWeightInputRecyclerView.setAdapter(clientSetAdapter);
        holder.setRepsWeightInputRecyclerView.setLayoutManager(setLayout);


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
                //avoid triggering event when text is too short
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

}
