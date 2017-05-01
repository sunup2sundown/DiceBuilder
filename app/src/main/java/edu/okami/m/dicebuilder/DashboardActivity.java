package edu.okami.m.dicebuilder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.okami.m.dicebuilder.Adapters.GridAdapter;
import edu.okami.m.dicebuilder.Dialogs.CreateDiceboxDialog;
import edu.okami.m.dicebuilder.Dialogs.DiceboxLongpressDialog;
import edu.okami.m.dicebuilder.Dialogs.DownloadDiceDialog;
import edu.okami.m.dicebuilder.Dialogs.NoDiceboxDialog;
import edu.okami.m.dicebuilder.Dialogs.ShareDiceBoxDialog;
import edu.okami.m.dicebuilder.Handlers.CompressionHandler;
import edu.okami.m.dicebuilder.Objects.GridItem;

/**
 * Created by M on 4/22/2017.
 */

public class DashboardActivity extends AppCompatActivity
        implements NoDiceboxDialog.NoDiceboxDialogListener, CreateDiceboxDialog.CreateDiceboxDialogListener,
        DiceboxLongpressDialog.DiceboxLongpressDialogListener, ShareDiceBoxDialog.ShareDiceBoxDialogListener,
        DownloadDiceDialog.DownloadDiceDialogListener{
    private final String TAG = "DashboardActivity";

    private File userDirectory;
    private final String HOME = "home";
    private String userId;
    private String downloadLink;

    //Firebase Instances
    StorageReference mStorageReference;
    FirebaseDatabase database;
    DatabaseReference urlReference;

    //Layout Components
    GridView gridView;
    Button button;
    GridAdapter gridAdapter;

    GridItem selectedGridItem;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar dashboardToolbar = (Toolbar)findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(dashboardToolbar);
        getSupportActionBar().setTitle("Dashboard");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        userDirectory = new File(getFilesDir(), userId);
        userDirectory = getDir(userId, Context.MODE_PRIVATE);

        if(!userDirectory.exists()){
            userDirectory.mkdirs();
        }

        /**
         * Firebase Instantiation
         */
        database = FirebaseDatabase.getInstance();
        urlReference = database.getReference();

        gridView = (GridView)findViewById(R.id.dashboard_gridview);

        ArrayList<String> filesArray = getFileNames(userDirectory.listFiles());

        if(filesArray != null){
            if(filesArray.size() > 0){
                gridAdapter = new GridAdapter(this, R.layout.gridview_layout, populateGridView());
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
            } else{
                NoDiceboxDialog register = new NoDiceboxDialog();
                register.show(getSupportFragmentManager(), "NoDiceboxDialog");
            }
        } else {
            NoDiceboxDialog register = new NoDiceboxDialog();
            register.show(getSupportFragmentManager(), "NoDiceboxDialog");
        }



        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                GridItem item = (GridItem)parent.getItemAtPosition(pos);
                //TODO: Send intent to DiceBoxActivity
                Intent i = new Intent(getApplicationContext(), DiceBoxActivity.class);
                //TODO: Pass name for next activity to retrieve image from Firebase storage
                i.putExtra("BoxName", item.getTitle());
                i.putExtra("UserID", userId);
                startActivity(i);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem item = (GridItem)parent.getItemAtPosition(position);
                selectedPosition = position;
                selectedGridItem = item;

                DiceboxLongpressDialog dialog = new DiceboxLongpressDialog();
                dialog.show(getSupportFragmentManager(), "DiceboxLongpressDialog");

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate menu
        getMenuInflater().inflate(R.menu.dashboard_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle Tool bar item clicks
        int id = item.getItemId();

        switch (id){
            case R.id.action_add_dicebox:
                CreateDiceboxDialog dialog = new CreateDiceboxDialog();
                dialog.show(getSupportFragmentManager(), "CreateDiceboxDialog");
                break;
            case R.id.action_download_dicebox:
                //TODO: Create a method to handle finding downloadable dice
                DatabaseReference userDbReference = FirebaseDatabase.getInstance().getReference();
                downloadDice(userDbReference, userId);
                /*
                Intent i = new Intent(getApplicationContext(), DownloadsActivity.class);
                i.putExtra("UserID", userId);
                startActivity(i);
                */
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadDice(DatabaseReference databaseReference, final String userID){
        //TODO: Search downloads for userid
        databaseReference.child("users").child("downloads")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            String inDatabase = data.getKey();

                            if(inDatabase.contentEquals(userID)){
                                Log.d(TAG, "Download exists");
                                downloadLink = data.getValue().toString();
                                //TODO: Bring up dialog asking if they would like to download the file by name
                                // If found, get download url and
                                linkFound(downloadLink);
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


        //TODO: Store in userid path
        //TODO: Delete download link in database
    }

    private void linkFound(String downloadLink){
        DownloadDiceDialog dialog = new DownloadDiceDialog();
        dialog.show(getSupportFragmentManager(), "DownloadDiceDialog");
        /*
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(downloadLink);
        File localFile = null;
        try {
            localFile = File.createTempFile("temp", "", userDirectory);
        } catch(IOException e){
            e.printStackTrace();
        }
        final String tempFileLocation = localFile.getPath();
        httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Local Temp File has been created
                //TODO: Uncompress file
                //CompressionHandler ch = new CompressionHandler();
                //TODO: Pass a name given by user to unpackZip, Make a dialog and show
                //ch.unpackZip(tempFileLocation);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO: Handle any Errors
            }
        });
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //If there's a download in progress, save the reference so you can query it later
        if(mStorageReference != null){
            outState.putString("reference", mStorageReference.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        //If there was a download in progress, get its reference and create a new StorageReference
        final String stringRef = savedInstanceState.getString("reference");
        if(stringRef == null){
            return;
        }
        mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);
        //Find all download tasks under this storage reference
        List<FileDownloadTask> tasks = mStorageReference.getActiveDownloadTasks();
        if(tasks.size() > 0){
            //Get the task monitoring the download
            FileDownloadTask task = tasks.get(0);
            //Add new listeners to the task using an activity scope
            task.addOnSuccessListener(this, new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Hand Success
                }
            });
        }

    }


    /*
    * No Dice Box Dialog Implementation
    *
     */
    @Override
    public void onNoDiceboxPositiveClick(DialogFragment dialog){
        dialog.getDialog().cancel();
        CreateDiceboxDialog register = new CreateDiceboxDialog();
        register.show(getSupportFragmentManager(), "CreateDiceboxDialog");
    }

    @Override
    public void onNoDiceboxNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    /*
    * Create A Dice Box Dialog Implementation
    *
     */
    @Override
    public void onCreateDiceboxPositiveClick(DialogFragment dialog){
        String boxName = ((EditText)dialog.getDialog().findViewById(R.id.createdicebox_dialog_boxname)).getText().toString();

        File directory = new File(userDirectory, boxName);

        Log.d(TAG, "User Directory:" + userDirectory.toString());
        directory.mkdir();
        gridAdapter = new GridAdapter(this, R.layout.gridview_layout, populateGridView());

        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateDiceboxNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    /*
    * Dice Box Long press Dialog Implementation
    *
     */
    @Override
    public void onDiceboxLongpressPositiveClick(DialogFragment dialog){

        ShareDiceBoxDialog shareDialog = new ShareDiceBoxDialog();
        shareDialog.show(getSupportFragmentManager(), "ShareDiceBoxDialog");
        //gridView.setAdapter(new GridAdapter(this, R.layout.gridview_layout, populateGridView()));

    }
    @Override
    public void onDiceboxLongpressNeutralClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }
    @Override
    public void onDiceboxLongpressNegativeClick(DialogFragment dialog){
        deleteGridItem(selectedGridItem, selectedPosition);
    }

    /**
     * Share Dice box with Friend Dialog
     *
     */
    @Override
    public void onShareDiceBoxPositiveClick(DialogFragment dialog){
        DatabaseReference userDbReference = FirebaseDatabase.getInstance().getReference();
        EditText toFriend = (EditText)dialog.getDialog().findViewById(R.id.share_dicebox_text);
        File directory = new File(userDirectory, selectedGridItem.getTitle());

        Log.d(TAG, "Directory:src: " + directory.toString());

        Log.d(TAG, "Friend: " + toFriend.getText().toString());
        findFriendUserId(userDbReference, toFriend.getText().toString(), directory);
    }

    private void findFriendUserId(DatabaseReference databaseReference, final String friendName, final File file){
        String userId;
        databaseReference.child("users").child("usernames")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            String inDatabase = data.getKey();

                            if(inDatabase.contentEquals(friendName)){
                                Log.d(TAG, "USername exists");
                                String userId = data.getValue().toString();

                                //Good to Zip folder from Path
                                String zipFolderPath = file.getPath() + "z";
                                CompressionHandler ch = new CompressionHandler();

                                ch.zipFileAtPath(file.getPath(), zipFolderPath);
                                //Send to Firebase
                                File zFile = new File(zipFolderPath);
                                Log.d(TAG, "Zip Directory:" + zFile.toString());
                                Log.d(TAG, "User's ID: " + userId);
                                uploadFile(userId, zFile);

                                //zFile.delete();
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

    }

    @Override
    public void onShareDiceBoxNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    private void uploadFile(final String userId, File zippedFile){
        final Uri downloadLink = null;
        //Firebase instantiation
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //References .getReference, .getChild, .getRoot, .getParent
        //Create reference to firebase storage
        StorageReference storageReference = storage.getReference();
        //create reference to child folder
        StorageReference diceboxRef = storageReference.child(userId);

        Uri uri = Uri.fromFile(new File(zippedFile.toString()));

        Log.d(TAG, "UploadFromURI:src: " + zippedFile.toString());

        UploadTask uploadTask = diceboxRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //TODO: Handle failed outputstream to firebase
                Toast.makeText(getApplicationContext(), "Dicebox storage was unsuccessful.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //taskSnapshot.getMetadata(); will contain file metadata such as size, content-type and download url
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                //Toast.makeText(getApplicationContext(), "Dicebox stored on server!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Download Link: " + downloadUrl);
                uploadURLToFriend(userId, downloadUrl);
            }
        });

    }

    public void onDownloadDicePositiveClick(DialogFragment dialog){
        String directoryName = ((EditText)dialog.getDialog().findViewById(R.id.download_dice_text)).getText().toString();

        final File directory = new File(userDirectory, directoryName);

        Log.d(TAG, "Destination: " + directory.getPath());

        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(downloadLink);
        File localFile = null;
        try {
            localFile = File.createTempFile("temp", "", getFilesDir());
        } catch(IOException e){
            e.printStackTrace();
        }
        final String tempFileLocation = localFile.getPath();

        httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Local Temp File has been created
                //TODO: Uncompress file
                CompressionHandler ch = new CompressionHandler();
                //TODO: Pass a name given by user to unpackZip, Make a dialog and show
                ch.unzip(tempFileLocation, userDirectory.getPath());
                Log.d(TAG, "Zip Source: " + tempFileLocation.toString());
                Log.d(TAG, "New Directory:" + directory.toString());
                Log.d(TAG, "File Contents: " + directory.listFiles());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO: Handle any Errors
            }
        });


    }

    public void onDownloadDiceNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    private ArrayList<GridItem> populateGridView(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_dice_box);
        ArrayList<GridItem> arrayOfGridItems = new ArrayList<GridItem>();
        ArrayList<String> arrayList = getFileNames(userDirectory.listFiles());

           for(int i = 0; i < arrayList.size(); i++){
               GridItem tempGI = new GridItem(bitmap, arrayList.get(i));
               arrayOfGridItems.add(tempGI);
           }

        return arrayOfGridItems;
    }

    private void uploadURLToFriend(String userId, Uri imageURL){
        Log.d(TAG, "Database Reference: " + urlReference.child("users").child("downloads").toString());
        urlReference.child("users").child("downloads").child(userId).setValue(imageURL.toString());
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

    private void deleteGridItem(GridItem item, int position){
        File directory = new File(userDirectory, item.getTitle());
        deleteRecursive(directory);
        gridAdapter.removeItem(position);
        gridAdapter.notifyDataSetChanged();
    }

    private void deleteRecursive(File directory){
        if(directory.isDirectory()){
            for(File child : directory.listFiles()){
                deleteRecursive(child);
            }
        }
        directory.delete();
    }

}
