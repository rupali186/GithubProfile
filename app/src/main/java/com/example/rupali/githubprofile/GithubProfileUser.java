package com.example.rupali.githubprofile;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RUPALI on 17-03-2018.
 */

public class GithubProfileUser {
    String email;
    String avatar_url;
    String name;
    @SerializedName("login")
    String login;
    String company;
    int followers;
    int following;
//    String login;
//    int id;
//    String avatar_url;
//    String gravatar_id;
//    String url;
//    String html_url;
//    String followers_url;
//    String following_url;
//    String gists_url;
//    String starred_url;
//    String subscriptions_url;
//    String organizations_url;
//    String repos_url;
//    String events_url;
//    String received_events_url;
//    String type;
//    Boolean site_admin;
//    String name;
//    String company;
//    String blog;
//    String location;
//    String email;
//    Boolean hireable;
//    int public_repos;
//    int public_gists;
//    int followers;
//    int following;
    public String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

}
