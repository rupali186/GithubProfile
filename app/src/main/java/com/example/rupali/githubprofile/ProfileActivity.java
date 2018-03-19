package com.example.rupali.githubprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    TextView nameTextView;
    TextView companyTextView;
    TextView emailTextView;
    TextView followersTextView;
    TextView followingTextView;
    ProgressBar progressBar;
    Button showFollowers;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=findViewById(R.id.imageView);
        nameTextView=findViewById(R.id.nameTextView);
        companyTextView=findViewById(R.id.companyTextView);
        emailTextView=findViewById(R.id.emailTextView);
        followersTextView=findViewById(R.id.followersTextView);
        followingTextView=findViewById(R.id.followingTextView);
        progressBar=findViewById(R.id.progressBar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent=getIntent();
        progressBar.setVisibility(View.VISIBLE);
        showFollowers=findViewById(R.id.showFollowers);
        showFollowers.setOnClickListener(this);
        bundle=intent.getExtras();
        if(bundle!=null){
            fetchDataForUser();
        }

    }


    private void fetchDataForUser() {
        showFollowers.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        nameTextView.setVisibility(View.INVISIBLE);
        emailTextView.setVisibility(View.INVISIBLE);
        followingTextView.setVisibility(View.INVISIBLE);
        followersTextView.setVisibility(View.INVISIBLE);
        companyTextView.setVisibility(View.INVISIBLE);
        String name=bundle.getString(Constants.NAME);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.github.com/").
                addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI= retrofit.create(UserAPI.class);
        Call<GithubProfileUser> response=userAPI.getUserResponse(name);
        response.enqueue(new Callback<GithubProfileUser>() {
            @Override
            public void onResponse(Call<GithubProfileUser> call, Response<GithubProfileUser> response) {
                Log.d("res",response.toString());
                if(response.code()==404){
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(imageView,"sorry the user does not exist",Snackbar.LENGTH_LONG).show();
                    return;
                }
                GithubProfileUser githubProfile=response.body();
                nameTextView.setText("NAME: "+githubProfile.name);
                companyTextView.setText("COMPANY: "+githubProfile.company);
                followersTextView.setText("FOLLOWERS: "+githubProfile.followers+"");
                followingTextView.setText("FOLLOWING: "+githubProfile.following+"");
                emailTextView.setText("EMAIL: "+githubProfile.email);
                Picasso.get().load(githubProfile.avatar_url).into(imageView);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                nameTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                followingTextView.setVisibility(View.VISIBLE);
                followersTextView.setVisibility(View.VISIBLE);
                companyTextView.setVisibility(View.VISIBLE);
                showFollowers.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<GithubProfileUser> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("ResponseFailure","fetch data for user failed");
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,FollowerActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
