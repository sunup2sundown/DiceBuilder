package edu.okami.m.dicebuilder.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import edu.okami.m.dicebuilder.R;

/**
 * Created by M on 4/22/2017.
 */

public class ImageAdapter  extends BaseAdapter{
    private Context mContext;
    private final String[] gridViewTitles;
    private final int[] gridViewImageId;

    //Constructor
    public ImageAdapter(Context c, String[] gridViewTitles, int[] gridViewImageId){
        mContext = c;
        this.gridViewImageId = gridViewImageId;
        this.gridViewTitles = gridViewTitles;
    }

    public int getCount(){
        return gridViewTitles.length;
    }

    public Object getItem(int pos){
        return null;
    }

    public long getItemId(int pos){
        return 0;
    }

    //create a new ImageView for each item referenced by the Adapter
    public View getView(int pos, View convertView, ViewGroup parent){
        View gridView;
        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.gridview_layout, null);
            TextView textViewGrid = (TextView)gridView.findViewById(R.id.gridview_layout_title);
            ImageView imageViewGrid = (ImageView) gridView.findViewById(R.id.gridview_layout_image);
            textViewGrid.setText(gridViewTitles[pos]);
            imageViewGrid.setImageResource(gridViewImageId[pos]);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
