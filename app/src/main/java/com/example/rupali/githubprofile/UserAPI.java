package com.example.rupali.githubprofile;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by RUPALI on 17-03-2018.
 */

public interface UserAPI {
    @GET("users/{uname}")
    Call<GithubProfileUser> getUserResponse(@Path("uname") String username);
    @GET("users/{uname}/followers")
    Call<ArrayList<GithubProfileUser>> getFollowers(@Path("uname") String username);
//    @GET("users/rohanarora")
//    Call<GithubProfileUser> getUserResponse();
}
