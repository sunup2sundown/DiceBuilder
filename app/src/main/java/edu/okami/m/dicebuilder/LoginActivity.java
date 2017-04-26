package edu.okami.m.dicebuilder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.provider.FirebaseInitProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import edu.okami.m.dicebuilder.Dialogs.LoginDialog;
import edu.okami.m.dicebuilder.Dialogs.RegisterDialog;

public class LoginActivity extends AppCompatActivity
        implements RegisterDialog.RegisterDialogListener, LoginDialog.LoginDialogListener{
    private final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference userDbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final FragmentManager dialogManager = getSupportFragmentManager();

        Button btnLogin = (Button)findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDialog login = new LoginDialog();
                login.show(dialogManager, "LoginDialog");
            }
        });
        Button btnRegister = (Button)findViewById(R.id.login_btn_signup);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDialog register = new RegisterDialog();
                register.show(dialogManager, "RegisterDialog");
            }
        });

        Button makeTexture = (Button)findViewById(R.id.button2);
        makeTexture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent textureIntent = new Intent(getApplicationContext(), textureActivity.class);

                startActivity(textureIntent);
            }
        });

        Button diceRoll = (Button) findViewById(R.id.diceRollButton);
        diceRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent diceRollIntent = new Intent(getApplicationContext(), DiceRollActivity.class);
                startActivity(diceRollIntent);
            }
        });

        userDbReference = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth fbAuth){
                FirebaseUser user = fbAuth.getCurrentUser();
                if(user != null){
                    //User is signed in
                } else{
                    //User is signed out
                }
            }
        };

    }

    @Override
    public void onStart(){
        super.onStart();
        // Attach the Authentication instance
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        //Remove Authentication instance if there
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    /*
    * Register Dialog Implementation
    *
     */
    @Override
    public void onRegisterPositiveClick(DialogFragment dialog){
        //Get String Text from EditText fields
        String username = ((EditText)dialog.getDialog().findViewById(R.id.register_dialog_username)).getText().toString();
        String email = ((EditText)dialog.getDialog().findViewById(R.id.register_dialog_email)).getText().toString();
        String password = ((EditText)dialog.getDialog().findViewById(R.id.register_dialog_password)).getText().toString();
        String confirmPassword = ((EditText)dialog.getDialog().findViewById(R.id.register_dialog_confirm)).getText().toString();

        if(username.contains("@")){
            Toast.makeText(this, "Usernames can only have alphanumeric characters", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(username, email, password, confirmPassword);
        }

    }

    @Override
    public void onRegisterNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }
    /*
    * Login Dialog Implementation
     */
    @Override
    public void onLoginPositiveClick(DialogFragment dialog){
        String usernameEmail = ((EditText)dialog.getDialog().findViewById(R.id.login_dialog_username)).getText().toString();
        String password = ((EditText)dialog.getDialog().findViewById(R.id.login_dialog_password)).getText().toString();

        int atIndex = usernameEmail.indexOf("@");
        if(atIndex != -1){
            loginUser(usernameEmail, password);
        } else{
            loginWithUsername(usernameEmail, password);
        }

    }

    @Override
    public void onLoginNegativeClick(DialogFragment dialog){
        dialog.getDialog().cancel();
    }

    private void registerUser(final String username, final String email, String password, String confirmPassword){
        if(password.contentEquals(confirmPassword)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            } else{

                                writeNewUser(mAuth.getCurrentUser().getUid(), username, email);
                                //Go to Home Activity
                                Intent diceRollIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(diceRollIntent);
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Password's do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser(final String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        } else{
                            //Go to home activity
                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                            //i.putExtra("user",userna);
                            startActivity(i);
                        }
                    }
                });
    }


    private void writeNewUser(String userId, String username, String email){
        //userDbReference.child("users").child("details").push().setValue(userId);
        //userDbReference.child("users").child("details").setValue(userId);
        userDbReference.child("users").child("details").child(userId).setValue("email");
        userDbReference.child("users").child("details").child(userId).setValue("username");
        userDbReference.child("users").child("details").child(userId).child("email").setValue(email);
        userDbReference.child("users").child("details").child(userId).child("username").setValue(username);

        //userDbReference.child("users").child("usernames").push().setValue(username);
        userDbReference.child("users").child("usernames").child(username).setValue(userId);
    }

    private void loginWithUsername(final String username, final String password){
        userDbReference.child("users").child("usernames")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        String inDatabase = data.getKey();
                        if(inDatabase.contentEquals(username)){
                            Log.d(TAG, "USername exists");
                            String userId = data.getValue().toString();
                            foundEmail(userId, password);

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

    private void foundEmail(final String key, final String password){
        userDbReference.child("users").child("details").child(key).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue().toString();
                loginUser(email, password);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
