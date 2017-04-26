package edu.okami.m.dicebuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import edu.okami.m.dicebuilder.Adapters.ImageAdapter;

/**
 * Created by M on 4/22/2017.
 */

public class DiceBoxActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicebox);

        Intent i = getIntent();

        //GridView gridView = (GridView)findViewById(R.id.gridview);
        //gridView.setAdapter(new ImageAdapter(this));
    }
}
