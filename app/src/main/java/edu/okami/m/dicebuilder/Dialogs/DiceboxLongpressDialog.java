package edu.okami.m.dicebuilder.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

import edu.okami.m.dicebuilder.R;

/**
 * Created by M on 4/27/2017.
 */

public class DiceboxLongpressDialog extends DialogFragment {
    private final String TAG = "DiceboxLongpressDialog";
    DiceboxLongpressDialog.DiceboxLongpressDialogListener mListener;

    /*The activity that Shares instance of dialog must implement
    * this interface in order to recieve event callbacks
     */
    public interface DiceboxLongpressDialogListener{
        public void onDiceboxLongpressPositiveClick(DialogFragment dialog);
        public void onDiceboxLongpressNeutralClick(DialogFragment dialog);
        public void onDiceboxLongpressNegativeClick(DialogFragment dialog);
    }

    //Override Fragment.onAttach method to instantiate listener
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        //Verify that the host activity implements the callback interface
        try{
            //Instantiate listener so events can be sent to host
            mListener = (DiceboxLongpressDialog.DiceboxLongpressDialogListener) activity;
        } catch(ClassCastException e){
            //Activity doesn't implement
            throw new ClassCastException(activity.toString() + " must implement DiceboxLongpressDialogListener");
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
        builder.setView(inflater.inflate(R.layout.dialog_diceboxlongpress, null))
                .setTitle("Choose an Option...")
                .setPositiveButton("Share Dicebox", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //Send the positive button event back to host activity
                        mListener.onDiceboxLongpressPositiveClick(DiceboxLongpressDialog.this);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDiceboxLongpressNeutralClick(DiceboxLongpressDialog.this);
                    }
                })
                .setNegativeButton("Delete Dicebox", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //send the negative button event back to host activity
                        mListener.onDiceboxLongpressNegativeClick(DiceboxLongpressDialog.this);
                    }
                });
        //Create Dialog object and return it
        return builder.create();
    }
}