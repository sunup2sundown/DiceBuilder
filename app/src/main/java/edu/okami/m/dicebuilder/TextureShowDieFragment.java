package edu.okami.m.dicebuilder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class TextureShowDieFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    Activity activity;
    Button backToBox;
    TextView nameView;
    String dieName = "";
    String path;
    View v;

    public TextureShowDieFragment() {
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
        v = inflater.inflate(R.layout.fragment_texture_show_die, container, false);

        backToBox = (Button)v.findViewById(R.id.btn_backToBox);
        nameView = (TextView)v.findViewById(R.id.tv_diceName);
        nameView.setText(dieName);

        backToBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent backToBox = new Intent(activity.getApplicationContext(), DiceBoxActivity.class);
                //startActivity(backToBox);
                ((OnFragmentInteractionListener)activity).backToDiceBox();
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
    public void onActivityCreated(Bundle savedInstanceBundle) {

        super.onActivityCreated(savedInstanceBundle);

        GLSurfaceView renderBox = (GLSurfaceView) v.findViewById(R.id.sv_resultDie);
        CustomDie customDie = new CustomDie(path, getActivity());
        ResultRenderer resultRenderer = new ResultRenderer(getActivity(), customDie);
        renderBox.setRenderer(resultRenderer);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setDieName(String name){
        this.dieName = name;
    }

    public void setDiePath(String diePath) { this.path = diePath; }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        public void beginShowDieFragment(String diceName);
        public void backToDiceBox();
    }
}
