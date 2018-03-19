package com.example.rupali.githubprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<GithubProfileUser> followers=new ArrayList<>();
    RecyclerAdapter adapter;
    Bundle bundle;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.followerRecyclerView);
        progressBar=findViewById(R.id.followerProgressBar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent=getIntent();
        bundle=intent.getExtras();
        adapter=new RecyclerAdapter(this, followers, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Snackbar.make(recyclerView,followers.get(position).login+"",Snackbar.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        if(bundle!=null){
            fetchDataForFollowers();
        }
    }

    private void fetchDataForFollowers() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.github.com/").
                addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI=retrofit.create(UserAPI.class);
        Call<ArrayList<GithubProfileUser>> call=userAPI.getFollowers(bundle.getString(Constants.NAME));
        call.enqueue(new Callback<ArrayList<GithubProfileUser>>() {
            @Override
            public void onResponse(Call<ArrayList<GithubProfileUser>> call, Response<ArrayList<GithubProfileUser>> response) {
                ArrayList<GithubProfileUser> users=response.body();
                if(users!=null){
                    followers.clear();
                    followers.addAll(users);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<GithubProfileUser>> call, Throwable t) {
                Toast.makeText(FollowerActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
