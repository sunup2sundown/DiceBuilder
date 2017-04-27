package edu.okami.m.dicebuilder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        downloadsList = (ListView) findViewById(R.id.downloadsList);
        noDownloadsText = (TextView) findViewById(R.id.noDownloadsText);

        Intent i = getIntent();
        final String userId = i.getStringExtra("UserID");
        String url = "https://dicebuilder-15e03.firebaseio.com/users/details/" +userId + "/downloads.json";
        DatabaseReference downloadReference = FirebaseDatabase.getInstance().getReference();

        downloadReference.child("users").child("downloads").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        for(com.google.firebase.database.DataSnapshot data : dataSnapshot.getChildren()){
                            String inDatabase = data.getKey();
                            Log.d(TAG, "Key: " + inDatabase);
                            if(inDatabase.contentEquals(userId)){
                                Log.d(TAG, "File Name: " + data.getValue());
                                String url = data.getValue().toString();

                                break;
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
}