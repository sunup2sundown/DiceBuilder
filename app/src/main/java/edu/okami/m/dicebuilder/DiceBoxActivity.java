package edu.okami.m.dicebuilder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowId;
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
import edu.okami.m.dicebuilder.Objects.DiceItem;
import edu.okami.m.dicebuilder.Objects.GridItem;

/**
 * Created by M on 4/22/2017.
 */

public class DiceBoxActivity extends AppCompatActivity
        implements NoDiceDialog.NoDiceDialogListener, CreateDiceDialog.CreateDiceDialogListener{
    private final String TAG = "DiceBoxActivity";
    private final int MAX_DICE = 5;

    private String boxName;
    private String userId;

    private File boxDirectory;

    private ArrayList<CustomDie> passDiceToRoll = new ArrayList<CustomDie>();

    private Button button;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicebox);

        Intent i = getIntent();
        boxName = i.getStringExtra("BoxName");
        userId = i.getStringExtra("UserID");

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(userId, getApplicationContext().MODE_PRIVATE);
        Toolbar diceboxToolbar = (Toolbar)findViewById(R.id.dicebox_toolbar);
        setSupportActionBar(diceboxToolbar);
        getSupportActionBar().setTitle(boxName);

        boxDirectory = new File(directory, boxName);

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
                DiceItem item = (DiceItem)parent.getItemAtPosition(position);
                CustomDie die = (CustomDie)item.getCustomDie();

                passDiceToRoll.add(die);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate menu
        getMenuInflater().inflate(R.menu.dicebox_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle Tool bar item clicks
        int id = item.getItemId();

        switch (id){
            case R.id.action_add_dice:
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * No Dice Box Dialog Implementation
    *
     */
    @Override
    public void onNoDicePositiveClick(DialogFragment dialog){
        Intent i = new Intent(getApplicationContext(), textureActivity.class);
        i.putExtra("BoxName", boxName);
        i.putExtra("UserID", userId);
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
        i.putExtra("UserID", userId);
        startActivity(i);
    }

    @Override
    public void onCreateDiceNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }


    private ArrayList<DiceItem> populateGridWithDice(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ten_sided_shape);
        ArrayList<DiceItem> arrayOfDiceItems = new ArrayList<DiceItem>();

        ArrayList<String> arrayOfFiles = getFileNames(boxDirectory.listFiles());

        for(int i = 0; i < arrayOfFiles.size(); i++){
            String path = boxDirectory.getPath().concat(arrayOfFiles.get(i));
            CustomDie cd = new CustomDie(path, getApplicationContext());

            DiceItem tempDI = new DiceItem(bitmap, arrayOfFiles.get(i), cd);
            arrayOfDiceItems.add(tempDI);
        }

        return arrayOfDiceItems;
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
