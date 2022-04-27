package com.dhaini.zyzz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddWorkoutDialog extends AppCompatDialogFragment {
    @NonNull
    EditText workoutNameEditText;
    private AddWorkoutListener listener;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =  getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_workout_dialog,null);
        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Add Workout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String workoutName = workoutNameEditText.getText().toString();
                        listener.applyText(workoutName);
                    }
                });

        workoutNameEditText = view.findViewById(R.id.workoutNameEditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddWorkoutListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface AddWorkoutListener{
        void applyText(String workoutName);
    }
}
