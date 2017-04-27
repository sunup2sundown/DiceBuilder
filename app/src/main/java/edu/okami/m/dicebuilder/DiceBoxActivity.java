package edu.okami.m.dicebuilder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

import edu.okami.m.dicebuilder.Adapters.GridAdapter;
import edu.okami.m.dicebuilder.Dialogs.CreateDiceDialog;
import edu.okami.m.dicebuilder.Dialogs.NoDiceDialog;
import edu.okami.m.dicebuilder.Dialogs.NoDiceboxDialog;
import edu.okami.m.dicebuilder.Objects.GridItem;

/**
 * Created by M on 4/22/2017.
 */

public class DiceBoxActivity extends AppCompatActivity
        implements NoDiceDialog.NoDiceDialogListener, CreateDiceDialog.CreateDiceDialogListener{
    private final String TAG = "DiceBoxActivity";

    private String boxName;
    private String userId;

    private File boxDirectory;

    private Button button;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicebox);

        Intent i = getIntent();
        boxName = i.getStringExtra("BoxName");
        userId = i.getStringExtra("UserID");
        String file = userId + "\\" + boxName;
        Log.d(TAG, file);


        boxDirectory = new File(getFilesDir(),file);
        boxDirectory = getDir(file, Context.MODE_PRIVATE);

        if(!boxDirectory.exists()){
            boxDirectory.mkdirs();
        }
        gridView = (GridView)findViewById(R.id.dicebox_gridview);
        ArrayList<String> filesArray = getFileNames(boxDirectory.listFiles());

        if(filesArray != null){
            if(filesArray.size() > 0){
                gridView.setAdapter(new GridAdapter(this, R.layout.gridview_layout, populateGridWithDice()));
            } else{
                NoDiceDialog register = new NoDiceDialog();
                register.show(getSupportFragmentManager(), "NoDiceDialog");
            }
        } else {
            NoDiceDialog register = new NoDiceDialog();
            register.show(getSupportFragmentManager(), "NoDiceDialog");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //GridItem item = (GridItem)parent.getItemAtPosition(position);
            }
        });

        button = (Button)findViewById(R.id.button_dicebox_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                CreateDiceDialog register = new CreateDiceDialog();
                register.show(getSupportFragmentManager(), "CreateDiceDialog");
                */
                Intent i = new Intent(getApplicationContext(), textureActivity.class);
                i.putExtra("BoxName", boxName);
                i.putExtra("UserID", userId);
                startActivity(i);
            }
        });
    }

    /*
    * No Dice Box Dialog Implementation
    *
     */
    @Override
    public void onNoDicePositiveClick(DialogFragment dialog){
        Intent i = new Intent(getApplicationContext(), textureActivity.class);
        startActivity(i);
    }

    @Override
    public void onNoDiceNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    /*
    * Create A Dice Box Dialog Implementation
    *
     */
    @Override
    public void onCreateDicePositiveClick(DialogFragment dialog){
        String diceName = ((EditText)dialog.getDialog().findViewById(R.id.createdicebox_dialog_boxname)).getText().toString();

        File directory = new File(boxDirectory, diceName);
        directory.mkdir();
        //gridView.setAdapter(new GridAdapter(this, R.layout.gridview_layout, populateGridWithDice()));
        Intent i = new Intent(getApplicationContext(), textureActivity.class);
        i.putExtra("BoxName", boxName);
        startActivity(i);
    }

    @Override
    public void onCreateDiceNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    private ArrayList<GridItem> populateGridWithDice(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boba_fett);
        ArrayList<GridItem> arrayOfGridItems = new ArrayList<GridItem>();
        ArrayList<String> arrayOfFiles = getFileNames(boxDirectory.listFiles());

        for(int i = 0; i < arrayOfFiles.size(); i++){
            GridItem tempGI = new GridItem(bitmap, arrayOfFiles.get(i));
            arrayOfGridItems.add(tempGI);
        }

        return arrayOfGridItems;
    }
    private ArrayList<String> getFileNames(File[] files){
        ArrayList<String> arrayListFiles = new ArrayList<String>();

        if(files != null){
            if(files.length == 0){
                return null;
            } else{
                for(int i = 0; i < files.length; i++){
                    arrayListFiles.add(files[i].getName());
                }
            }
        } else {
            return null;
        }

        return arrayListFiles;
    }
}
