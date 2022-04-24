package com.dhaini.zyzz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class TrainerExerciseAdapter extends RecyclerView.Adapter<TrainerExerciseAdapter.TrainerExerciseViewHolder> {
    private ArrayList<TrainerExercise> trainerExerciseList;
    private OnItemClickListener mListener;
    private Activity activity;

    public TrainerExerciseAdapter(ArrayList<TrainerExercise> trainerExerciseList,Activity activity){
        this.activity = activity;
        this.trainerExerciseList = trainerExerciseList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class TrainerExerciseViewHolder extends RecyclerView.ViewHolder{
        public TextView exerciseNameTextView;
        public TextView commentsEditText;
        private RecyclerView setRepsWeightInputRecyclerView;

        public TrainerExerciseViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.ExerciseNameTextView);
            commentsEditText = itemView.findViewById(R.id.commentsEditText);
            setRepsWeightInputRecyclerView = itemView.findViewById(R.id.setRepsWeightInputRecyclerView);


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

    @NonNull
    @Override
    public TrainerExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_exercise_card,parent,false);
        TrainerExerciseViewHolder mCVH = new TrainerExerciseViewHolder(v,mListener);
        return mCVH;
    }



    @Override
    public void onBindViewHolder(@NonNull TrainerExerciseViewHolder holder, int position) {
        TrainerExercise currentExercise = trainerExerciseList.get(position);

        TrainerSetAdapter trainerSetAdapter = new TrainerSetAdapter(currentExercise.getSetTrainerList());
        LinearLayoutManager setLayout = new LinearLayoutManager(activity);
        holder.setRepsWeightInputRecyclerView.setAdapter(trainerSetAdapter);
        holder.setRepsWeightInputRecyclerView.setLayoutManager(setLayout);

        holder.exerciseNameTextView.setText(currentExercise.getExerciseName());
        holder.commentsEditText.setText(currentExercise.getComments());
    }

    @Override
    public int getItemCount() {
        return trainerExerciseList.size();
    }


}