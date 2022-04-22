package com.dhaini.zyzz;

public class TrainerClients {
    private String clientFullName;
    private String clientID;
    private String clientPlanID;
    private String dayPerWeek;
    private String trainerObjectives;


    public TrainerClients(String client_fullname, String clientFullName, String clientID, String clientPlanID, String dayPerWeek, String trainerObjectives) {
        this.clientFullName = client_fullname;
        this.clientID = clientID;
        this.clientPlanID = clientPlanID;
        this.dayPerWeek = dayPerWeek;
        this.trainerObjectives = trainerObjectives;

    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientPlanID() {
        return clientPlanID;
    }

    public void setClientPlanID(String clientPlanID) {
        this.clientPlanID = clientPlanID;
    }

    public String getDayPerWeek() {
        return dayPerWeek;
    }

    public void setDayPerWeek(String dayPerWeek) {
        this.dayPerWeek = dayPerWeek;
    }

    public String getTrainerObjectives() {
        return trainerObjectives;
    }

    public void setTrainerObjectives(String trainerObjectives) {
        this.trainerObjectives = trainerObjectives;
    }
}
