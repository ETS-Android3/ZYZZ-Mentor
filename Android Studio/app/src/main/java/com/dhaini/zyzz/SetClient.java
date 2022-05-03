package com.dhaini.zyzz;

public class SetClient {
    private String setName;
    private String trainerReps;
    private String trainerWeight;
    private String clientReps;
    private String clientWeight;
    private String exerciseID;
    private String setID;
    private int completed;


    public SetClient(String setName, String trainerReps, String trainerWeight, String clientReps, String clientWeight, String exerciseID, String setID, int completed) {
        this.setName = setName;
        this.trainerReps = trainerReps;
        this.trainerWeight = trainerWeight;
        this.clientReps = clientReps;
        this.clientWeight = clientWeight;
        this.exerciseID = exerciseID;
        this.setID = setID;
        this.completed = completed;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getTrainerReps() {
        return trainerReps;
    }

    public void setTrainerReps(String trainerReps) {
        this.trainerReps = trainerReps;
    }

    public String getTrainerWeight() {
        return trainerWeight;
    }

    public void setTrainerWeight(String trainerWeight) {
        this.trainerWeight = trainerWeight;
    }

    public String getClientReps() {
        return clientReps;
    }

    public void setClientReps(String clientReps) {
        this.clientReps = clientReps;
    }

    public String getClientWeight() {
        return clientWeight;
    }

    public void setClientWeight(String clientWeight) {
        this.clientWeight = clientWeight;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "SetClient{" +
                "setName='" + setName + '\'' +
                ", trainerReps='" + trainerReps + '\'' +
                ", trainerWeight='" + trainerWeight + '\'' +
                ", clientReps='" + clientReps + '\'' +
                ", clientWeight='" + clientWeight + '\'' +
                ", exerciseID='" + exerciseID + '\'' +
                ", setID='" + setID + '\'' +
                ", completed=" + completed +
                '}';
    }
}
