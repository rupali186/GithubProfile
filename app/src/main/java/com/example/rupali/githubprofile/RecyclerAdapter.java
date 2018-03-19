package com.example.rupali.githubprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RUPALI on 18-03-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.UserViewHolder> {

    interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<GithubProfileUser> users;
    Context context;
    OnItemClickListener listener;

    public RecyclerAdapter(Context context,ArrayList<GithubProfileUser> users,OnItemClickListener listener){
        this.users = users;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.follower_list_item,parent,false);
        UserViewHolder holder = new UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        GithubProfileUser user = users.get(position);
        holder.username.setText(user.login);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load(user.avatar_url).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        ImageView avatar;
        View itemView;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            username = itemView.findViewById(R.id.followerUsername);
            avatar = itemView.findViewById(R.id.followerAvatar);
        }
    }
}