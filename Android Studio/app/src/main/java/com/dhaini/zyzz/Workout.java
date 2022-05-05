package com.dhaini.zyzz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Workout implements Parcelable {
    private String workoutName;
    private String workoutID;
    private String planID;
    private int background_Image;
    private int position;

    public Workout(String workoutName, String workoutID, String planID, int background_Image, int position) {
        this.workoutName = workoutName;
        this.workoutID = workoutID;
        this.planID = planID;
        this.background_Image = background_Image;
        this.position = position;
    }


    protected Workout(Parcel in) {
        workoutName = in.readString();
        workoutID = in.readString();
        planID = in.readString();
        background_Image = in.readInt();
        position = in.readInt();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(workoutName);
        parcel.writeString(workoutID);
        parcel.writeString(planID);
        parcel.writeInt(background_Image);
        parcel.writeInt(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "Workout{" +
                "workoutName='" + workoutName + '\'' +
                ", workoutID='" + workoutID + '\'' +
                ", planID='" + planID + '\'' +
                ", background_Image=" + background_Image +
                ", position=" + position +
                '}';
    }
}
