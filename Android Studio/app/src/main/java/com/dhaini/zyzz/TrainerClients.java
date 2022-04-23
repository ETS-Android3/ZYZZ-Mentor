package com.dhaini.zyzz;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainerClients implements Parcelable{
    private String clientFullName;
    private String clientID;
    private String clientPlanID;
    private String dayPerWeek;
    private String trainerObjectives;


    public TrainerClients(String clientFullName, String clientID, String clientPlanID, String dayPerWeek, String trainerObjectives) {
        this.clientFullName = clientFullName;
        this.clientID = clientID;
        this.clientPlanID = clientPlanID;
        this.dayPerWeek = dayPerWeek;
        this.trainerObjectives = trainerObjectives;
    }

    protected TrainerClients(Parcel in) {
        clientFullName = in.readString();
        clientID = in.readString();
        clientPlanID = in.readString();
        dayPerWeek = in.readString();
        trainerObjectives = in.readString();
    }

    public static final Creator<TrainerClients> CREATOR = new Creator<TrainerClients>() {
        @Override
        public TrainerClients createFromParcel(Parcel in) {
            return new TrainerClients(in);
        }

        @Override
        public TrainerClients[] newArray(int size) {
            return new TrainerClients[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(clientFullName);
        parcel.writeString(clientID);
        parcel.writeString(clientPlanID);
        parcel.writeString(dayPerWeek);
        parcel.writeString(trainerObjectives);
    }
}
