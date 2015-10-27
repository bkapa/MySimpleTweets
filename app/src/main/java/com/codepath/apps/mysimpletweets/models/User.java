package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bkapa on 10/26/15.
 */
public class User implements Serializable {


    private String name;
    private long uid;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private String ScreenName;
    private String profileImageUrl;

    public static User fromJson(JSONObject json){

        User user = new User();
        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.ScreenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
        }catch(JSONException je){
            je.printStackTrace();
        }
        return user;
    }


}
