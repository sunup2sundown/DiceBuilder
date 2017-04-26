package edu.okami.m.dicebuilder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyft.android.scissors.CropView;

import static android.app.Activity.RESULT_OK;
import static edu.okami.m.dicebuilder.R.drawable.pentagon;


public class TextureCropFragment extends Fragment {

    TextView test;
    String diceName;
    int sides;
    int imageCount = 0;

    Bitmap images[];

    Activity activity;
    ImageView overlay;
    CropView cropview;
    TextView imgCount;

    Button selectBtn, cropBtn;

    private OnFragmentInteractionListener mListener;

    public TextureCropFragment() {
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
        View v = inflater.inflate(R.layout.fragment_texture_crop, container, false);

        selectBtn = (Button) v.findViewById(R.id.btn_selectimg);
        cropBtn = (Button) v.findViewById(R.id.btn_crop);
        cropview = (CropView) v.findViewById(R.id.cropview_fragment);
        overlay = (ImageView)v.findViewById(R.id.imageview_fragment);
        imgCount = (TextView)v.findViewById(R.id.tv_count);
        imgCount.setText("Side 1/" + sides);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent();

                photoIntent.setType("image/*");
                photoIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(photoIntent, "Select Picture"), 10);
            }
        });

        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap croppedImage = cropview.crop();
                Bitmap resizedImage = Bitmap.createScaledBitmap(croppedImage, 256, 256, false);
                images[imageCount] = resizedImage;
                imageCount++;
                if (imageCount == sides) {
                    Bitmap merged = merge(images, sides);
                    Toast.makeText(activity, "Images Merged", Toast.LENGTH_SHORT).show();
                    ((OnFragmentInteractionListener) activity).saveMergedImage(merged, diceName);
                    ((OnFragmentInteractionListener) activity).beginShowDieFragment(diceName);
                }
                imgCount.setText("Side " + (imageCount+1) + "/" + sides);

            }
        });

        setOverlay(sides);

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

    public void getInfo(String x, int y) {
        this.diceName = x;
        this.sides = y;
        images = new Bitmap[sides];
    }

    public void setOverlay(int sides){

        Resources res = getResources();
        if(sides == 4 || sides == 8 || sides == 20){
            overlay.setImageDrawable(res.getDrawable(R.drawable.triangle));
        }
        else if(sides == 12){
            overlay.setImageDrawable(res.getDrawable(R.drawable.pentagon));
        }
        else if(sides == 10){
            overlay.setImageDrawable(res.getDrawable(R.drawable.ten_sided_shape));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);

                cropview.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public Bitmap merge(Bitmap[] images, int count) {
        Bitmap retval = Bitmap.createBitmap(images[0].getWidth() * count, images[0].getHeight(), images[0].getConfig());
        Canvas canvas = new Canvas(retval);
        Paint paint = new Paint();
        for (int i = 0; i < images.length; i++) {
            canvas.drawBitmap(images[i], images[i].getWidth() * (i % count), images[i].getHeight() * (i / count), paint);
        }
        return retval;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        public void beginShowDieFragment(String diceName);
        public String saveMergedImage(Bitmap image, String diceName);
    }
}
