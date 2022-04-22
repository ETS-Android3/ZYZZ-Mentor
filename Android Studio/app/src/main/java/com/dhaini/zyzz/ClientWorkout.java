package com.dhaini.zyzz;

public class ClientWorkout {
    private String workoutName;
    private String workoutID;
    private String planID;
    private int background_Image;




    public ClientWorkout(String workoutName, String workoutID, String planID,int background_Image) {
        this.workoutName = workoutName;
        this.workoutID = workoutID;
        this.planID = planID;
        this.background_Image = background_Image;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }
    public void setBackground_Image(int background_Image) {
        this.background_Image = background_Image;
    }
    public int getBackground_Image() {
        return background_Image;
    }
}
