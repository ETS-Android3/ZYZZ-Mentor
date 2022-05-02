package com.dhaini.zyzz;

import java.util.ArrayList;

public class ClientExercise {
    private String exerciseID;
    private String exerciseName;
    private String comments;
    private String feedbacks;
    private String workoutID;
    private int position;
    private ArrayList<SetClient> SetClientList;

    public ClientExercise(String exerciseID, String exerciseName, String comments, String feedbacks, String workoutID, int position, ArrayList<SetClient> setClientList) {
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.comments = comments;
        this.feedbacks = feedbacks;
        this.workoutID = workoutID;
        this.position = position;
        SetClientList = setClientList;
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

    public String getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<SetClient> getSetClientList() {
        return SetClientList;
    }

    public void setSetClientList(ArrayList<SetClient> setClientList) {
        SetClientList = setClientList;
    }
}
