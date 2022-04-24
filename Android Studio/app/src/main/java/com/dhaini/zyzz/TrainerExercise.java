package com.dhaini.zyzz;

import java.util.ArrayList;
import java.util.Set;

public class TrainerExercise {
    private String exerciseID;
    private String exerciseName;
    private String comments;
    private String workoutID;
    private ArrayList<SetTrainer> SetTrainerList;

    public TrainerExercise(String exerciseID, String exerciseName, String comments, String workoutID, ArrayList<SetTrainer> setTrainerList) {
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.comments = comments;
        this.workoutID = workoutID;
        SetTrainerList = setTrainerList;
    }


    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public ArrayList<SetTrainer> getSetTrainerList() {
        return SetTrainerList;
    }

    public void setSetTrainerList(ArrayList<SetTrainer> setTrainerList) {
        SetTrainerList = setTrainerList;
    }
}
