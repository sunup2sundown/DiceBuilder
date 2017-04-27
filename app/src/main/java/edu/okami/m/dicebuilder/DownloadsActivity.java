package edu.okami.m.dicebuilder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import edu.okami.m.dicebuilder.Objects.UserDetails;

/**
 * Created by M on 4/27/2017.
 */

public class DownloadsActivity extends AppCompatActivity {
    private final String TAG = "DownloadsActivity";
    ListView downloadsList;
    TextView noDownloadsText;

    ArrayList<String> downloads = new ArrayList<>();
    int totalDownloads;

    Firebase downloadReference;

    ProgressDialog pd;

    File storageUrl;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        downloadsList = (ListView) findViewById(R.id.downloadsList);
        noDownloadsText = (TextView) findViewById(R.id.noDownloadsText);

        Intent i = getIntent();
        userId = i.getStringExtra("UserID");
        String url = "https://dicebuilder-15e03.firebaseio.com/users/details/" +userId + "/downloads.json";
        storageUrl = getDir(userId, Context.MODE_PRIVATE);
        DatabaseReference downloadReference = FirebaseDatabase.getInstance().getReference();

        downloadReference.child("users").child("downloads").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        for(com.google.firebase.database.DataSnapshot data : dataSnapshot.getChildren()){
                            String inDatabase = data.getKey();
                            Log.d(TAG, "Key: " + inDatabase);
                            Log.d(TAG, "ID: " + userId);
                            if(inDatabase.contentEquals(userId)){
                                Log.d(TAG, "File Name: " + data.getValue());
                                String url = data.getValue().toString();
                                new DownloadTask(getApplicationContext()).execute(url);
                                downloads.add(url);
                            }
                            else{
                                Log.d(TAG, "Does not exist");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "Cancelled. USername exists");
                    }
                });

        downloadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startActivity(new Intent(Users.this, Chat.class));
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(storageUrl.getPath());

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
    }
}