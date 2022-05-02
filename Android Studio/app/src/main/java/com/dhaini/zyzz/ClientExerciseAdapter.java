package com.dhaini.zyzz;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClientExerciseAdapter extends RecyclerView.Adapter<ClientExerciseAdapter.ClientExerciseViewHolder> {

    private Activity activity;
    private ArrayList<ClientExercise> clientExercisesList;
    private String user;


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
        
    }

    @Override
    public int getItemCount() {
        return clientExercisesList.size();
    }
}
