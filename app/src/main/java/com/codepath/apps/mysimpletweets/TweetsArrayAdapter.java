package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweets;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by bkapa on 10/26/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweets> {
    public TweetsArrayAdapter(Context context, List<Tweets> objects) {
        super(context,0,objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Tweets tweet = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUserProfile);
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView  tvBody = (TextView) convertView.findViewById(R.id.tvbody);
        TextView  tvHrs = (TextView) convertView.findViewById(R.id.tvRelHrs);
        tvImage.setImageResource(android.R.color.transparent);
        tvUser.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvHrs.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(tvImage);
        return convertView;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
