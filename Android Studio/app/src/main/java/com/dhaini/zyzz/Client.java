package com.dhaini.zyzz;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
    private String clientUsername;
    private String clientID;
    private String clientPlanID;


    public Client(String clientUsername, String clientID, String clientPlanID) {
        this.clientUsername = clientUsername;
        this.clientID = clientID;
        this.clientPlanID = clientPlanID;

    }

    protected Client(Parcel in) {
        clientUsername = in.readString();
        clientID = in.readString();
        clientPlanID = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(clientUsername);
        parcel.writeString(clientID);
        parcel.writeString(clientPlanID);
    }
}
