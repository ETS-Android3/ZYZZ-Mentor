package com.dhaini.zyzz;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class TrainerExerciseAdapter extends RecyclerView.Adapter<TrainerExerciseAdapter.TrainerExerciseViewHolder>  {
    private ArrayList<TrainerExercise> trainerExerciseList;
    private OnItemClickListener mListener;
    private Activity activity;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    private String currentExerciseID;
    private String updatedCommentToCurrentExercise;

    private UpdateCommentsAPI updateCommentsAPI;

    public TrainerExerciseAdapter(ArrayList<TrainerExercise> trainerExerciseList, Activity activity) {
        this.activity = activity;
        this.trainerExerciseList = trainerExerciseList;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAddSetClick(int position);

        void onDeleteExerciseClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TrainerExerciseViewHolder extends RecyclerView.ViewHolder  {
        public TextView exerciseNameTextView;
        public EditText commentsEditText;
        private RecyclerView setRepsWeightInputRecyclerView;
        private ImageButton addSetImgBtn;
        private ImageButton deleteExerciseImgBtn;

        public TrainerExerciseViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.ExerciseNameTextView);
            commentsEditText = itemView.findViewById(R.id.commentsEditText);
            setRepsWeightInputRecyclerView = itemView.findViewById(R.id.setRepsWeightInputRecyclerView);
            addSetImgBtn = itemView.findViewById(R.id.addSetButton);
            deleteExerciseImgBtn = itemView.findViewById(R.id.deleteExerciseButton);

            addSetImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddSetClick(position);
                        }
                    }
                }
            });

            deleteExerciseImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteExerciseClick(position);
                        }
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public TrainerExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_exercise_card, parent, false);
        TrainerExerciseViewHolder mCVH = new TrainerExerciseViewHolder(v, mListener);
        return mCVH;
    }


    @Override
    public void onBindViewHolder(@NonNull TrainerExerciseViewHolder holder, int position) {
        TrainerExercise currentExercise = trainerExerciseList.get(position);

        TrainerSetAdapter trainerSetAdapter = new TrainerSetAdapter(currentExercise.getSetTrainerList());
        LinearLayoutManager setLayout = new LinearLayoutManager(activity);

        ItemTouchHelper.Callback callback = new myItemTouchHelper(trainerSetAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        trainerSetAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(holder.setRepsWeightInputRecyclerView);

        holder.setRepsWeightInputRecyclerView.setAdapter(trainerSetAdapter);
        holder.setRepsWeightInputRecyclerView.setLayoutManager(setLayout);

        holder.exerciseNameTextView.setText(currentExercise.getExerciseName());
        holder.commentsEditText.setText(currentExercise.getComments());

        holder.commentsEditText.addTextChangedListener(new TextWatcher() {
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

                        updatedCommentToCurrentExercise = edit;

                        updateCommentsAPI = new UpdateCommentsAPI();
                        updateCommentsAPI.execute();

                    }

                }, DELAY);

            }
        });

    }

    @Override
    public int getItemCount() {
        return trainerExerciseList.size();
    }


    public Toast toastMessage(String message) {
        Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
        return toast;
    }


    class UpdateCommentsAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost("http://10.0.2.2/ZYZZ/update_comments.php?");

            BasicNameValuePair exerciseIDParam = new BasicNameValuePair("exerciseID", currentExerciseID);
            BasicNameValuePair commentExerciseToChangeParam = new BasicNameValuePair("comments", updatedCommentToCurrentExercise);


            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(exerciseIDParam);
            name_value_pair_list.add(commentExerciseToChangeParam);

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