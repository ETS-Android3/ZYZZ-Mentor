package com.dhaini.zyzz;

import android.os.AsyncTask;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.ArrayList;

public class ClientWorkoutAdapter extends RecyclerView.Adapter<ClientWorkoutAdapter.ClientWorkoutViewHolder> {

    private ArrayList<Workout> workoutsList;
    private static ClientWorkoutAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ClientWorkoutViewHolder extends RecyclerView.ViewHolder {

        public TextView workoutNameTextView;
        public ImageView workoutCardImageBackground;




        public ClientWorkoutViewHolder(@NonNull View itemView, ClientWorkoutAdapter.OnItemClickListener listener) {
            super(itemView);

            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            workoutCardImageBackground = itemView.findViewById(R.id.cardImage);

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

        }

    }

    public ClientWorkoutAdapter(ArrayList<Workout> workoutsList) {
        this.workoutsList = workoutsList;
    }

    @NonNull
    @Override

    public ClientWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_my_traning_item, parent, false);
        ClientWorkoutViewHolder mCVH = new ClientWorkoutViewHolder(v, mListener);
        return mCVH;
    }


    @Override
    public void onBindViewHolder(@NonNull ClientWorkoutViewHolder holder, int position) {

        Workout currentWorkout = workoutsList.get(position);
        holder.workoutNameTextView.setText(currentWorkout.getWorkoutName());
        holder.workoutCardImageBackground.setImageResource(currentWorkout.getBackground_Image());

    }

    @Override
    public int getItemCount() {
        return workoutsList.size();
    }




}
