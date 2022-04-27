package com.dhaini.zyzz;

import android.os.AsyncTask;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Workout> workoutsList;
    private static OnItemClickListener mListener;
    private static ItemTouchHelper itemTouchHelper;

    @Override
    public void onItemMove(int fromPosition, int toPosition) throws JSONException {
        Workout fromWorkout = workoutsList.get(fromPosition);
        workoutsList.remove(fromWorkout);
        workoutsList.add(toPosition,fromWorkout);

      /*  JSONObject workoutFromJson = new JSONObject();
        workoutFromJson.put("WorkoutID",fromWorkout.getWorkoutID());
        workoutFromJson.put("position",fromWorkout.getWorkoutID());

        JSONObject workoutToJson = new JSONObject();
        workoutToJson.put("WorkoutID",workoutsList.get(toPosition).getWorkoutID());
        workoutToJson.put("position",workoutsList.get(toPosition).getWorkoutID());
      */

        notifyItemMoved(fromPosition,toPosition);

    }

    @Override
    public void onItemSwiped(int position) {

    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper){
        this.itemTouchHelper = itemTouchHelper;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {

        public TextView workoutNameTextView;
        public ImageView workoutCardImageBackground;
        public ImageButton deleteWorkout;
        GestureDetector gestureDetector;

        public WorkoutViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            workoutCardImageBackground = itemView.findViewById(R.id.cardImage);
            deleteWorkout = itemView.findViewById(R.id.deleteWorkout);
            gestureDetector = new GestureDetector(itemView.getContext(),this);


            itemView.setOnTouchListener(this);
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

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            mListener.onItemClick(getAdapterPosition());
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




}