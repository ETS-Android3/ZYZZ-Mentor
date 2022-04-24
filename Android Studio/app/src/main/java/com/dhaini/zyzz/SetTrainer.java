package com.dhaini.zyzz;

public class SetTrainer {
    private String setName;
    private String reps;
    private String weight;
    private String exerciseID;

    public SetTrainer(String setName, String reps, String weight, String exerciseID) {
        this.setName = setName;
        this.reps = reps;
        this.weight = weight;
        this.exerciseID = exerciseID;
    }


    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }
}
