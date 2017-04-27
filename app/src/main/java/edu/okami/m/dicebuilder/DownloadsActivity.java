package edu.okami.m.dicebuilder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        downloadsList = (ListView)findViewById(R.id.downloadsList);
        noDownloadsText = (TextView)findViewById(R.id.noDownloadsText);

        pd = new ProgressDialog(DownloadsActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        Intent i = getIntent();
        String userId = i.getStringExtra("UserID");

        String userUrl = "https://dicebuilder-15e03.firebaseio.com/details/" + userId + "/images";
        StringRequest request = new StringRequest(Request.Method.GET, userUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO:Success
                doOnSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(DownloadsActivity.this);
        rQueue.add(request);

        downloadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
                    downloads.add(key);
                }

                totalDownloads++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalDownloads <=1){
            noDownloadsText.setVisibility(View.VISIBLE);
            downloadsList.setVisibility(View.GONE);
        }
        else{
            noDownloadsText.setVisibility(View.GONE);
            downloadsList.setVisibility(View.VISIBLE);
            downloadsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, downloads));
        }

        pd.dismiss();
    }
}
