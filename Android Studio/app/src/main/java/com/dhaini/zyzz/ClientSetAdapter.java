package com.dhaini.zyzz;

import android.graphics.Color;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientSetAdapter extends RecyclerView.Adapter<ClientSetAdapter.ClientSetViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<SetClient> setClientList;
    private OnItemClickListener mListener;
    private static ItemTouchHelper itemTouchHelper;
    private String user;
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    //Global variable to be initialized to be able to send the data to the api
    private UpdateSetAPI updateSetAPI;
    private String columnToChange;
    private String updatedInfo;
    private String set_id;



    public ClientSetAdapter(ArrayList<SetClient> setClientList, String user) {
        this.setClientList = setClientList;
        this.user = user; // To know if this is a client or a trainer
    }
    public static class ClientSetViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        public TextView setNameTextView;
        public EditText setRepsEditText;
        public EditText setWeightEditText;
        public ImageView CompleteLineImageView;
        GestureDetector gestureDetector;

        public ClientSetViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            setNameTextView = itemView.findViewById(R.id.setNameInput);
            setRepsEditText = itemView.findViewById(R.id.repsInput);
            setWeightEditText = itemView.findViewById(R.id.weightInput);
            CompleteLineImageView = itemView.findViewById(R.id.CompleteLineImageView);
            gestureDetector = new GestureDetector(itemView.getContext(), this);
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

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ClientSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_set_card, parent, false);
        ClientSetAdapter.ClientSetViewHolder mCVH = new ClientSetAdapter.ClientSetViewHolder (v, mListener);
        return mCVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientSetAdapter.ClientSetViewHolder holder, int position) {
        SetClient currentSet = setClientList.get(position);

        // If the user is a trainer he can't edit the client weight and reps;
        if(user.equalsIgnoreCase("Trainer")){
            holder.setWeightEditText.setEnabled(false);
            holder.setRepsEditText.setEnabled(false);
        }
        // Check if the client completed his set if not we hide the completed line imageView
        if(currentSet.getCompleted() == 0){
            holder.CompleteLineImageView.setImageAlpha(0);
        }

        holder.setNameTextView.setText(currentSet.getSetName());

        // Putting the trainer weight and reps assigned as hint to help the client to remember what the trainer assigned to him in case he changed the reps or weights.
        holder.setWeightEditText.setHint(currentSet.getTrainerWeight());
        holder.setRepsEditText.setHint(currentSet.getTrainerReps());


        // If the client didn't change the reps and weight the trainer assigned to him

        if(currentSet.getClientReps().equals("null")){
            holder.setRepsEditText.setText(currentSet.getTrainerReps());
        }
        else{
            // Check if the trainer and client reps are the same if not we change the text color to yellow to highlight that the client changed the reps
            if(currentSet.getClientReps().equalsIgnoreCase(currentSet.getTrainerReps())){
                holder.setRepsEditText.setText(currentSet.getTrainerReps());
            }
            else{
                holder.setRepsEditText.setTextColor(Color.parseColor("#E9CB0C"));
                holder.setRepsEditText.setText(currentSet.getClientReps());
            }

        }

        if(currentSet.getClientWeight().equals("null")){
            holder.setWeightEditText.setText(currentSet.getTrainerWeight());
        }
        else{
            // Check if the trainer and client weights are the same if not we change the text color to yellow to highlight that the client changed the weights
            if(currentSet.getClientWeight().equalsIgnoreCase(currentSet.getTrainerWeight())){
                holder.setRepsEditText.setText(currentSet.getTrainerReps());
            }
            else{
                holder.setWeightEditText.setTextColor(Color.parseColor("#E9CB0C"));
                holder.setWeightEditText.setText(currentSet.getClientWeight());
            }
        }


        // When the client change the reps and weight assigned by the trainer
        holder.setRepsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        final String edit = s.toString();
                        currentSet.setClientReps(edit);

                        set_id = currentSet.getSetID();
                        columnToChange = "reps";
                        updatedInfo = edit;


                        updateSetAPI = new TrainerSetAdapter.UpdateSetAPI();
                        updateSetAPI.execute();
                        Log.i("Message From set", "Hello");
                    }

                }, DELAY);

            }
        });


        //////////////////////// Weight ////////////////////////////////////////////////
        holder.setWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // TODO: do what you need here (refresh list)
                        // you will probably need to use
                        // runOnUiThread(Runnable action) for some specific
                        // actions

                        final String edit = s.toString();

                        currentSet.setClientWeight(edit);
                        set_id = currentSet.getSetID();
                        columnToChange = "client_weight";
                        updatedInfo = edit;

                        if(!updatedInfo.equalsIgnoreCase(currentSet.getTrainerWeight())){
                            holder.setWeightEditText.setTextColor(Color.parseColor("#E9CB0C"));
                        }
                        updateSetAPI = new UpdateSetAPI();
                        updateSetAPI.execute();
                        Log.i("Message From set", s.toString());
                    }

                }, DELAY);

            }
        });


    }

    @Override
    public int getItemCount() {
        if(setClientList==null){
            return 0;
        }
        return setClientList.size();
    }

    class UpdateSetAPI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient http_client = new DefaultHttpClient();
            HttpPost http_post = new HttpPost("http://10.0.2.2/ZYZZ/update_set.php?");
            Log.i("Message from api",set_id);
            BasicNameValuePair setIDParam = new BasicNameValuePair("setID", set_id);
            BasicNameValuePair columnToChangeParam = new BasicNameValuePair("column", columnToChange);
            BasicNameValuePair updatedInfoParam = new BasicNameValuePair("updatedInfo", updatedInfo);

            ArrayList<NameValuePair> name_value_pair_list = new ArrayList<>();
            name_value_pair_list.add(setIDParam);
            name_value_pair_list.add(columnToChangeParam);
            name_value_pair_list.add(updatedInfoParam);

            try {
                // This is used to send the list with the api in an encoded form entity
                UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(name_value_pair_list);

                // This sets the entity (which holds the list of values) in the http_post object
                http_post.setEntity(url_encoded_form_entity);

                // This gets the response from the post api and returns a string of the response.
                HttpResponse http_response = http_client.execute(http_post);
                InputStream input_stream = http_response.getEntity().getContent();
                InputStreamReader input_stream_reader = new InputStreamReader(input_stream);
                BufferedReader buffered_reader = new BufferedReader(input_stream_reader);
                StringBuilder string_builder = new StringBuilder();
                String buffered_str_chunk = null;
                while ((buffered_str_chunk = buffered_reader.readLine()) != null) {
                    string_builder.append(buffered_str_chunk);
                }
                Log.i("result", string_builder.toString());
                return string_builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
