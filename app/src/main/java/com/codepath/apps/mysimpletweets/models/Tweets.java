package com.codepath.apps.mysimpletweets.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by bkapa on 10/26/15.
 */
public class Tweets {

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String body;
    private long uid;
    private String createdAt;

    public static long getMax_id() {
        return max_id;
    }

    public static void setMax_id(long max_id) {
        Tweets.max_id = max_id;
    }

    private static long max_id;

    public User getUser() {
        return user;
    }

    private User user;

    public static Tweets fromJson(JSONObject json){
        Tweets tweet = new Tweets();
        try {
            tweet.body = json.getString("text");
            tweet.uid = json.getLong("id");
            tweet.createdAt = json.getString("created_at");
            tweet.user = User.fromJson(json.getJSONObject("user"));
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
       return tweet;
    }

    public static ArrayList<Tweets> fromJsonArray(JSONArray jsonArray){

        ArrayList<Tweets> results = new ArrayList<Tweets>();
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Tweets tweet = Tweets.fromJson(jsonObject);
                if (tweet != null) {
                    results.add(tweet);
                    max_id = tweet.getUid();
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
