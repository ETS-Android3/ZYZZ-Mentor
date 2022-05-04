package com.dhaini.zyzz;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientSetAdapter extends RecyclerView.Adapter<ClientSetAdapter.ClientSetViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<SetClient> setClientList;
    private static OnItemClickListener mListener;
    private static ItemTouchHelper itemTouchHelper;

    // User if its a client or trainer
    private String user;

    private Timer timer = new Timer();
    private final long DELAY = 3500; // in ms

    private UpdateSetCompleteAPI updateSetCompleteAPI;


    public ClientSetAdapter(ArrayList<SetClient> setClientList, String user) {
        this.setClientList = setClientList;
        this.user = user; // To know if this is a client or a trainer
    }

    public static class ClientSetViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        public TextView setNameTextView;
        public EditText setRepsEditText;
        public EditText setWeightEditText;
        GestureDetector gestureDetector;

        public ClientSetViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            setNameTextView = itemView.findViewById(R.id.setNameInput);
            setRepsEditText = itemView.findViewById(R.id.repsInput);
            setWeightEditText = itemView.findViewById(R.id.weightInput);
            gestureDetector = new GestureDetector(itemView.getContext(), this);

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

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) throws JSONException {

    }

    @Override
    public void onItemSwiped(int position) {

        // If the user is a client and swiped change the status of the set
        // from complete to incomplete and viceVersa and of course we update it to the database
        if (user.equalsIgnoreCase("Client")) {

            SetClient currentSet = setClientList.get(position);
            if (currentSet.getCompleted() == 0) {
                currentSet.setCompleted(1);
            } else {
                currentSet.setCompleted(0);
            }

            String set_id = currentSet.getSetID();
            String updatedInfo = String.valueOf(currentSet.getCompleted());

            notifyItemChanged(position);
            updateSetCompleteAPI = new UpdateSetCompleteAPI();
            String updateSet_url = "http://10.0.2.2/ZYZZ/client_update_set_complete.php?setID=" + set_id + "&complete=" + updatedInfo;
            updateSetCompleteAPI.execute(updateSet_url);

        }
        else if(user.equalsIgnoreCase("Trainer")){
            notifyDataSetChanged();
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ClientSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_set_card, parent, false);
        ClientSetAdapter.ClientSetViewHolder mCVH = new ClientSetAdapter.ClientSetViewHolder(v, mListener);
        return mCVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientSetAdapter.ClientSetViewHolder holder, int position) {
        SetClient currentSet = setClientList.get(position);

        // If the user is a trainer he can't edit the client weight and reps;
        if (user.equalsIgnoreCase("Trainer")) {
            holder.setWeightEditText.setEnabled(false);
            holder.setRepsEditText.setEnabled(false);
        }

        holder.setNameTextView.setText(currentSet.getSetName());
        holder.setWeightEditText.setEnabled(false);
        holder.setRepsEditText.setEnabled(false);

        // Check if the client completed his set if not we hide the completed line imageView

        if (currentSet.getCompleted() == 0) {
            holder.setNameTextView.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.setNameTextView.setTextColor(Color.parseColor("#025839"));
        }

        // Putting the trainer weight and reps assigned as hint to help the client to remember what
        // the trainer assigned to him in case he changed the reps or weights.


        // If the client didn't change the reps and weight the trainer assigned to him

        if (currentSet.getClientReps().equalsIgnoreCase("0")) {
            holder.setRepsEditText.setText(currentSet.getTrainerReps());
        } else {
            // Check if the trainer and client reps are the same if not we change the text color to Red to highlight that the client changed the reps
            if (!currentSet.getClientReps().equalsIgnoreCase(currentSet.getTrainerReps())) {
                holder.setRepsEditText.setTextColor(Color.parseColor("#FF0000"));
            }

            holder.setRepsEditText.setText(currentSet.getClientReps());

        }

        if (currentSet.getClientWeight().equalsIgnoreCase("0")) {
            holder.setWeightEditText.setText(currentSet.getTrainerWeight());
        } else {
            // Check if the trainer and client weights are the same
            // if not we change the text color to Red to highlight that the client changed the weights
            if (!currentSet.getClientWeight().equalsIgnoreCase(currentSet.getTrainerWeight())) {
                holder.setWeightEditText.setTextColor(Color.parseColor("#FF0000"));
            }
            holder.setWeightEditText.setText(currentSet.getClientWeight());
        }


    }

    @Override
    public int getItemCount() {
        if (setClientList == null) {
            return 0;
        }
        return setClientList.size();
    }




    public class UpdateSetCompleteAPI extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String values) {
            super.onPostExecute(values);
            try {
                return;

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


}