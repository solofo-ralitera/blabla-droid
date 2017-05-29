package com.blabla.project.blabla_droid.Follower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blabla.project.blabla_droid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.follower_item,parent, false);
        }

        FollowerViewHolder viewHolder = (FollowerViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new FollowerViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.follower_avatar);
            viewHolder.username = (TextView) convertView.findViewById(R.id.follower_unsername);
            viewHolder.name = (TextView) convertView.findViewById(R.id.follower_name);
            convertView.setTag(viewHolder);
        }

        // get item [position] of List<ListViewItem>
        ListViewItem item = getItem(position);

        //fill the view
        Picasso.with(parent.getContext()).load(item.getAvatar()).into(viewHolder.avatar);
        viewHolder.username.setText(item.getUsername());
        viewHolder.name.setText(item.getName());

        return convertView;
    }

    private class FollowerViewHolder{
        public ImageView avatar;
        public TextView username;
        public TextView name;
    }

}
