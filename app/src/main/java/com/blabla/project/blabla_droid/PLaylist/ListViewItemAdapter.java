package com.blabla.project.blabla_droid.PLaylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.blabla.project.blabla_droid.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by popolos on 22/05/2017.
*/

public class ListViewItemAdapter extends ArrayAdapter<ListViewItem> {


    public ListViewItemAdapter(Context context, List<ListViewItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playlist_item,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.track = (TextView) convertView.findViewById(R.id.playlistitem_track);
            viewHolder.file = (TextView) convertView.findViewById(R.id.playlistitem_file);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.playlistitem_image);
            convertView.setTag(viewHolder);
        }

        // get item [position] of List<ListViewItem>
        ListViewItem item = getItem(position);

        //fill the view
        viewHolder.track.setText(item.getTrack());
        viewHolder.file.setText(item.getFile());
        //viewHolder.image.setImageResource(item.getImage());
        //viewHolder.image.setImageDrawable(new ColorDrawable(item.getImage()));
        Picasso.with(parent.getContext()).load("http://10.0.2.2/beach.jpeg").into(viewHolder.image);

        return convertView;
    }

    private class TweetViewHolder{
        public TextView track;
        public TextView file;
        public ImageView image;
    }

}
