package com.example.rupali.githubprofile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RUPALI on 18-03-2018.
 */

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<GithubProfileUser> users;

    public UserAdapter(Context context, ArrayList<GithubProfileUser> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view=convertView;
        GithubProfileUser user=users.get(i);
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.follower_list_item,parent,false);
            ViewHolder holder=new ViewHolder();
            holder.avatar=view.findViewById(R.id.followerAvatar);
            holder.name=view.findViewById(R.id.followerUsername);
            view.setTag(holder);
        }
        ViewHolder holder= (ViewHolder) view.getTag();
        holder.name.setText(user.login);
        Picasso.get().load(user.avatar_url).into(holder.avatar);
        return view;
    }
    class ViewHolder{
        TextView name;
        ImageView avatar;
    }
}
