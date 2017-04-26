package edu.okami.m.dicebuilder;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by M on 4/20/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    private final String TAG = "MyFBInstanceIDService";

    @Override
    public void onTokenRefresh(){
        //Get Updated InstanceID token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token:" + refreshedToken);

        //To send messages to this application instance or
        //manage this apps subscriptions on the server side,
        //send the refreshed instance ID to app server
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers
     * Modify this method to associate the user's FCM instanceID with
     * any server-side account maintained by application
     *
     * @param token
     */
    private void sendRegistrationToServer(String token){
        //TODO: Implement this method to send token to app server
    }
}
