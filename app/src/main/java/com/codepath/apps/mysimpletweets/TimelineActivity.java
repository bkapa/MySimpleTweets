package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweets;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TimelineActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 20;
    private TwitterClient client;
    private TweetsArrayAdapter tweetsArrayAdapter;
    private ArrayList<Tweets> tweetsList;
    private ListView lvTweets;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView)findViewById(R.id.lvTweets);
        tweetsList = new ArrayList<Tweets>();
        client = TwitterApplication.getRestClient();
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                long max_id = Tweets.getMax_id()-1;
                customLoadMoreDataFromApi(max_id);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        tweetsArrayAdapter = new TweetsArrayAdapter(this,tweetsList);
        lvTweets.setAdapter(tweetsArrayAdapter);

        populateTimeline(new Long(0));
    }

    public void customLoadMoreDataFromApi(long offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter


        populateTimeline(offset);

    }

    private void populateTimeline(Long offset){
        client.getHomeTimeline(offset,new JsonHttpResponseHandler() {
            //Success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //deserialize json
                //create model object
                //load model object.
                tweetsArrayAdapter.addAll(Tweets.fromJsonArray(response));


            }


            //Failure


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        //User Account Details
        client.getUserAccount(new JsonHttpResponseHandler() {
            //Success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            user= User.fromJson(response);
            }


            //Failure

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miCompose) {

            Intent i = new Intent(TimelineActivity.this, TweetComposeAcitivity.class);
            i.putExtra("user",user);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }
             return super.onOptionsItemSelected(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){

        Log.i("Debug", "onOptionsItemSelected" + requestCode);
        Log.i("Debug", "onOptionsItemSelected" + data);
        if (resultCode == RESULT_OK) {
            // Extract name value from result extras
            tweetsArrayAdapter.clear();
            tweetsList.clear();
            populateTimeline( new Long(0));
        }
    }
}
