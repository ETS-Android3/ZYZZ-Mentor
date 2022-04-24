package com.dhaini.zyzz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MyClientsAdapter extends RecyclerView.Adapter<MyClientsAdapter.myClientsViewHolder> {
    private ArrayList<TrainerClients> myClientsList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class myClientsViewHolder extends RecyclerView.ViewHolder {
        public TextView myClientTextView;

        public myClientsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            myClientTextView = itemView.findViewById(R.id.clientName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MyClientsAdapter(ArrayList<TrainerClients> myClientsList) {
        this.myClientsList = myClientsList;
    }

    @NonNull
    @Override
    public myClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_client_item_recycler_view, parent, false);
        myClientsViewHolder mCVH = new myClientsViewHolder(v, mListener);
        return mCVH;
    }

    @Override
    public void onBindViewHolder(@NonNull myClientsViewHolder holder, int position) {
        String currentClient = myClientsList.get(position).getClientFullName();
        holder.myClientTextView.setText(currentClient);

    }

    @Override
    public int getItemCount() {
        return myClientsList.size();
    }
}