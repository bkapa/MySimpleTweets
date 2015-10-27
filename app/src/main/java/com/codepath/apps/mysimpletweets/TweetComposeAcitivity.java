package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

public class TweetComposeAcitivity extends AppCompatActivity {

    private TwitterClient client;
    private TextView txt;
    private ImageView ivImage;
    private TextView userName;
    private TextView screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_compose_acitivity);
        User user = (User)getIntent().getSerializableExtra("user");
        txt = (TextView) findViewById(R.id.editTweet);
        userName = (TextView) findViewById(R.id.tvUserId);
        ivImage = (ImageView)findViewById(R.id.ivFullImage);
        screenName = (TextView)findViewById(R.id.tvUserName);
        screenName.setText(user.getScreenName());
        userName.setText(user.getName());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivImage);
        client = TwitterApplication.getRestClient();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_compose_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.i("Debug", "onOptionsItemSelected" + id);
        Log.i("Debug", "onOptionsItemSelected" + R.id.action_settings);

        //noinspection SimplifiableIfStatement
        if (id == R.id.miCompose) {
            client.postTweet(txt.getText().toString(),new JsonHttpResponseHandler());
            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
