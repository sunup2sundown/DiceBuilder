package edu.okami.m.dicebuilder;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TextureMenuFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ArrayAdapter<CharSequence> adapter;
    Spinner spinner;
    Activity activity;
    EditText diceNameText;
    int sides;

    public TextureMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_texture_menu, container, false);

        spinner = (Spinner) v.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.dice_numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        diceNameText = (EditText) v.findViewById(R.id.editText);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Something selected", "" + adapter.getItem(i));
                sides = Integer.parseInt((adapter.getItem(i)).toString());
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button beginBtn = (Button) v.findViewById(R.id.btn_begin);
        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diceNameText.getText().toString().equals("")) {
                    Toast.makeText(activity, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                } else {
                    ((OnFragmentInteractionListener) activity).beginCropFragment(diceNameText.getText().toString(), sides);
                }
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = getActivity();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        public void beginCropFragment(String diceName, int sides);
    }
}
