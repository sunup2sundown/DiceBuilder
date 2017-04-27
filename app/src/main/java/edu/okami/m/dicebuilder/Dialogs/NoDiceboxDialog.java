package edu.okami.m.dicebuilder.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

import edu.okami.m.dicebuilder.R;

/**
 * Created by M on 4/26/2017.
 */

public class NoDiceboxDialog extends DialogFragment {
    private final String TAG = "NoDiceboxDialog";
    private EditText username, password, confirmPassword;
    NoDiceboxDialog.NoDiceboxDialogListener mListener;

    /*The activity that creates instance of dialog must implement
    * this interface in order to recieve event callbacks
     */
    public interface NoDiceboxDialogListener{
        public void onNoDiceboxPositiveClick(DialogFragment dialog);
        public void onNoDiceboxNegativeClick(DialogFragment dialog);
    }

    //Override Fragment.onAttach method to instantiate listener
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        //Verify that the host activity implements the callback interface
        try{
            //Instantiate listener so events can be sent to host
            mListener = (NoDiceboxDialog.NoDiceboxDialogListener) activity;
        } catch(ClassCastException e){
            //Activity doesn't implement
            throw new ClassCastException(activity.toString() + " must implement NoDiceboxDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Builder Class for dialog Construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get Layout inflater for custom layout
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflate dialog with Custom Layout
        //Null for parent view
        builder.setView(inflater.inflate(R.layout.dialog_nodicebox, null))
                .setPositiveButton("Create a dicebox", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //Send the positive button event back to host activity
                        mListener.onNoDiceboxPositiveClick(NoDiceboxDialog.this);
                    }
                })
                .setNegativeButton("Not now", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //send the negative button event back to host activity
                        mListener.onNoDiceboxNegativeClick(NoDiceboxDialog.this);
                    }
                });
        //Create Dialog object and return it
        return builder.create();
    }
}
