package com.dhaini.zyzz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private ArrayList<Workout> workoutsList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder{
        public TextView workoutNameTextView;
        public ImageView workoutCardImageBackground;
        public WorkoutViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            workoutCardImageBackground = itemView.findViewById(R.id.cardImage);
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