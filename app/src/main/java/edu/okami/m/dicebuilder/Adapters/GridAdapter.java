package edu.okami.m.dicebuilder.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.okami.m.dicebuilder.Objects.GridItem;
import edu.okami.m.dicebuilder.R;

/**
 * Created by M on 4/26/2017.
 */

public class GridAdapter extends ArrayAdapter {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> data = new ArrayList<GridItem>();

    //Constructor
    public GridAdapter(Context c, int layoutResourceId, ArrayList data){
        super(c, layoutResourceId, data);
        mContext = c;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    //create a new ImageView for each item referenced by the Adapter
    public View getView(int pos, View convertView, ViewGroup parent){
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity) mContext)
                    .getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.gridTitle = (TextView)row.findViewById(R.id.gridview_layout_title);
            holder.gridImage = (ImageView)row.findViewById(R.id.gridview_layout_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        GridItem item = data.get(pos);
        holder.gridTitle.setText(item.getTitle());
        holder.gridImage.setImageBitmap(item.getImage());

        return row;
    }

    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    static class ViewHolder{
        TextView gridTitle;
        ImageView gridImage;
    }
}