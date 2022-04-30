package com.dhaini.zyzz;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TrainerSetAdapter extends RecyclerView.Adapter<TrainerSetAdapter.TrainerSetViewHolder> implements ItemTouchHelperAdapter{
    private ArrayList<SetTrainer> setTrainerList;
    private OnItemClickListener mListener;
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private UpdateSetAPI updateSetAPI;
    private String columnToChange;
    private String updatedInfo;
    private String set_id;
    private DeleteSetAPI deleteSetAPI;
    private static ItemTouchHelper itemTouchHelper;


    @Override
    public void onItemMove(int fromPosition, int toPosition) throws JSONException {

    }

    @Override
    public void onItemSwiped(int position) {

        String deleteWorkout_url = "http://10.0.2.2/ZYZZ/delete_set.php?setID=" + setTrainerList.get(position).getSet_id();
        deleteSetAPI = new DeleteSetAPI();
        deleteSetAPI.execute(deleteWorkout_url);

        setTrainerList.remove(setTrainerList.get(position));
        notifyItemRemoved(position);

    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TrainerSetViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener{
        public EditText setNameTextView;
        public EditText setRepsTextView;
        public EditText setWeightTextView;

        GestureDetector gestureDetector;

        public TrainerSetViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            setNameTextView = itemView.findViewById(R.id.setNameInput);
            setRepsTextView = itemView.findViewById(R.id.repsInput);
            setWeightTextView = itemView.findViewById(R.id.weightInput);

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

    public TrainerSetAdapter(ArrayList<SetTrainer> setTrainerList) {
        this.setTrainerList = setTrainerList;
    }

    @NonNull
    @Override
    public TrainerSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainer_set_card, parent, false);
        TrainerSetViewHolder mCVH = new TrainerSetViewHolder(v, mListener);
        return mCVH;
    }


    @Override
    public void onBindViewHolder(@NonNull TrainerSetViewHolder holder, int position) {
        SetTrainer currentSet = setTrainerList.get(position);
        holder.setNameTextView.setText(currentSet.getSetName());
        holder.setRepsTextView.setText(currentSet.getReps());
        holder.setWeightTextView.setText(currentSet.getWeight());

        //////////////////////// Set Name /////////////////////////
        holder.setNameTextView.addTextChangedListener(new TextWatcher() {
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
                        currentSet.setSetName(edit);

                        set_id = currentSet.getSet_id();
                        columnToChange = "set_name";
                        updatedInfo = edit;

                        updateSetAPI = new UpdateSetAPI();
                        updateSetAPI.execute();
                        Log.i("Message From set", "Hello");
                    }

                }, DELAY);

            }
        });

        ///////////////////////////////// Reps////////////////////////////////
        holder.setRepsTextView.addTextChangedListener(new TextWatcher() {
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
                        currentSet.setReps(edit);

                        set_id = currentSet.getSet_id();
                        columnToChange = "reps";
                        updatedInfo = edit;


                        updateSetAPI = new UpdateSetAPI();
                        updateSetAPI.execute();
                        Log.i("Message From set", "Hello");
                    }

                }, DELAY);

            }
        });


        //////////////////////// Weight ////////////////////////////////////////////////
        holder.setWeightTextView.addTextChangedListener(new TextWatcher() {
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

                        currentSet.setWeight(edit);
                        set_id = currentSet.getSet_id();
                        columnToChange = "weight";
                        updatedInfo = edit;

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
        if (setTrainerList == null) {
            return 0;
        }
        return setTrainerList.size();
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


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class DeleteSetAPI extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            // URL and HTTP initialization to connect to API 2
            URL url;
            HttpURLConnection http;

            try {
                // Connect to API 2
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                // Retrieve API 2 content
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                // Read API 2 content line by line
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
                // Return content from API 2
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String values) {
            super.onPostExecute(values);
            try {
                values = values.replaceAll("[\\n]", "");

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


}