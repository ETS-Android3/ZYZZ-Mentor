package com.dhaini.zyzz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class TrainerSetAdapter extends RecyclerView.Adapter<TrainerSetAdapter.TrainerSetViewHolder> {
    private ArrayList<SetTrainer> setTrainerList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class TrainerSetViewHolder extends RecyclerView.ViewHolder{
        public TextView setNameTextView;
        public TextView setRepsTextView;
        public TextView setWeightTextView;

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
    }

    @Override
    public int getItemCount() {
        return setTrainerList.size();
    }
}