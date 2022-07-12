package com.syed.ary_news;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class live_streaming extends Fragment
{
    public static final String API_KEY = "AIzaSyCq-QGfmqqgO7xGX9iYKLw6Vh3RfZ84qLY";
    public static String VIDEO_ID="8H-N--0rjPk";
    View view;
    YouTubePlayer player;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Copied from stack Overflow

        if(view!=null)
        {
            ViewGroup parent=(ViewGroup)view.getParent();
            if(parent!=null)
            {
                parent.removeView(view);
            }
        }
        try
        {
            view=inflater.inflate(R.layout.fragment_live_streaming, container, false);
        }
        catch(InflateException ie)
        {

        }

        //read video id from preferenes

        /*SharedPreferences sharedPreferences=getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
        VIDEO_ID=sharedPreferences.getString("video",""); */

        if(VIDEO_ID!=null)
        {
            YouTubePlayerFragment obj=(YouTubePlayerFragment)getActivity().getFragmentManager().findFragmentById(R.id.youtube_fragment2);
            obj.initialize(API_KEY,listenerObj);

        }
        else
        {
            Toast.makeText(getContext(), "Video id is invlaid", Toast.LENGTH_SHORT).show();
        }

        new FirebaseDatabaseHelper().getLiveVideoId(new OnVideoIdLoadedListener() {
            @Override
            public void onLoaded(String newVideoId)
            {
                if (!newVideoId.equals(VIDEO_ID))
                {
                    VIDEO_ID=newVideoId;
                    if (player!=null)
                    {
                        player.loadVideo(VIDEO_ID);
                    }
                    //save new video id in prefernces
                    /* SharedPreferences sh=getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sh.edit();
                    editor.putString("video",VIDEO_ID);
                    editor.commit(); */
                }

            }
        });

        return view ;
    }

    YouTubePlayer.OnInitializedListener listenerObj=new YouTubePlayer.OnInitializedListener()
    {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean flag)
        {
            player=youTubePlayer;
            if(!flag)
            {
                youTubePlayer.loadVideo(VIDEO_ID);
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        }
    };

}
