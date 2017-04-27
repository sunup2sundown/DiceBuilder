package edu.okami.m.dicebuilder.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.okami.m.dicebuilder.CustomDie;
import edu.okami.m.dicebuilder.Objects.DiceItem;
import edu.okami.m.dicebuilder.Objects.GridItem;
import edu.okami.m.dicebuilder.R;

/**
 * Created by M on 4/26/2017.
 */

public class DiceAdapter extends ArrayAdapter {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<DiceItem> data = new ArrayList<DiceItem>();

    //Constructor
    public DiceAdapter(Context c, int layoutResourceId, ArrayList data){
        super(c, layoutResourceId, data);
        mContext = c;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    //create a new ImageView for each item referenced by the Adapter
    public View getView(int pos, View convertView, ViewGroup parent){
        View row = convertView;
        DiceAdapter.ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity) mContext)
                    .getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new DiceAdapter.ViewHolder();
            holder.diceTitle = (TextView)row.findViewById(R.id.gridview_layout_title);
            holder.diceImage = (ImageView)row.findViewById(R.id.gridview_layout_image);
            row.setTag(holder);
        } else {
            holder = (DiceAdapter.ViewHolder)row.getTag();
        }

        DiceItem item = data.get(pos);
        holder.diceTitle.setText(item.getTitle());
        holder.diceImage.setImageBitmap(item.getImage());
        holder.customDie = item.getCustomDie();

        return row;
    }

    static class ViewHolder{
        TextView diceTitle;
        ImageView diceImage;
        CustomDie customDie;
    }
}
