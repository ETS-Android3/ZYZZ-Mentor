package com.dhaini.zyzz;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainerExerciseAdapter extends RecyclerView.Adapter<TrainerExerciseAdapter.TrainerExerciseViewHolder> implements ItemTouchHelperAdapter{
    private ArrayList<TrainerExercise> trainerExerciseList;
    private OnItemClickListener mListener;
    private Activity activity;
    private static ItemTouchHelper itemTouchHelper;
    private DeleteExerciseAPI deleteExerciseAPI;
    public TrainerExerciseAdapter(ArrayList<TrainerExercise> trainerExerciseList, Activity activity) {
        this.activity = activity;
        this.trainerExerciseList = trainerExerciseList;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) throws JSONException {

    }

    @Override
    public void onItemSwiped(int position) {
        trainerExerciseList.remove(trainerExerciseList.get(position));
        String deleteExercise_url = "http://10.0.2.2/ZYZZ/delete_set.php?setID=" + setTrainerList.get(position).getSet_id();
        deleteExerciseAPI = new DeleteExerciseAPI();
        deleteExerciseAPI.execute(deleteWorkout_url);
        notifyItemRemoved(position);

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAddSetClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TrainerExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        public TextView exerciseNameTextView;
        public TextView commentsEditText;
        private RecyclerView setRepsWeightInputRecyclerView;
        private ImageButton addSetImgBtn;
        private GestureDetector gestureDetector;
        public TrainerExerciseViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.ExerciseNameTextView);
            commentsEditText = itemView.findViewById(R.id.commentsEditText);
            setRepsWeightInputRecyclerView = itemView.findViewById(R.id.setRepsWeightInputRecyclerView);
            addSetImgBtn = itemView.findViewById(R.id.addSetButton);

            gestureDetector = new GestureDetector(itemView.getContext(), this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
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
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            itemTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
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
    }

    @Override
    public int getItemCount() {
        return trainerExerciseList.size();
    }



    public Toast toastMessage(String message) {
        Toast toast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
        return toast;
    }

    public class DeleteExerciseAPI extends AsyncTask<String, Void, String> {
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
                values = values.replaceAll("[\\n]", "");

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


}