package com.dhaini.zyzz;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private ArrayList<Workout> workoutsList;
    private OnItemClickListener mListener;
    DeleteWorkoutAPI deleteWorkoutAPI;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onItemHold(Workout workout);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder{
        public TextView workoutNameTextView;
        public ImageView workoutCardImageBackground;
        public ImageButton deleteWorkout;
        public WorkoutViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            workoutCardImageBackground = itemView.findViewById(R.id.cardImage);
            deleteWorkout = itemView.findViewById(R.id.deleteWorkout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            deleteWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }
    public WorkoutAdapter(ArrayList<Workout> workoutsList){
        this.workoutsList = workoutsList;
    }
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_selected_item_recycler_view,parent,false);
        WorkoutViewHolder mCVH = new WorkoutViewHolder(v,mListener);
        return mCVH;
    }
    public void deleteItem(int position){
        String deleteWorkout_url = "http://10.0.2.2/ZYZZ/delete_workout.php?workoutID=" + workoutsList.get(position).getWorkoutID();
        deleteWorkoutAPI = new DeleteWorkoutAPI();
        deleteWorkoutAPI.execute(deleteWorkout_url);

    }



    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout currentWorkout = workoutsList.get(position);

        holder.workoutNameTextView.setText(currentWorkout.getWorkoutName());
        holder.workoutCardImageBackground.setImageResource(currentWorkout.getBackground_Image());
    }

    @Override
    public int getItemCount() {
        return workoutsList.size();
    }
    public class DeleteWorkoutAPI extends AsyncTask<String, Void, String> {
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
                Log.i("Delete",values);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


}