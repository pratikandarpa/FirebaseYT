package com.elite.firebaseyt.PlayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elite.firebaseyt.Apikey;
import com.elite.firebaseyt.MainActivity;
import com.elite.firebaseyt.R;
import com.elite.firebaseyt.YouTubePlay;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements YouTubeThumbnailView.OnInitializedListener, YouTubeThumbnailLoader.OnThumbnailLoadedListener, YouTubePlayer.OnInitializedListener {

    YouTubePlayerFragment playerFragment;
    YouTubePlayer Player;
    YouTubeThumbnailView thumbnailView;
    YouTubeThumbnailLoader thumbnailLoader;
    RecyclerView VideoList;
    RecyclerView.Adapter adapter;
    List<Drawable> thumbnailViews;
    List<String> VideoId;

    public static String listvideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        thumbnailViews = new ArrayList<>();
        VideoList = (RecyclerView) findViewById(R.id.VideoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        VideoList.setLayoutManager(layoutManager);
        adapter = new VideoListAdapter();
        VideoList.setAdapter(adapter);
        VideoId = new ArrayList<>();
        thumbnailView = new YouTubeThumbnailView(this);
        thumbnailView.initialize("AIzaSyABI6ztl4D-KST6NgXZGxByR0aWgNnVxTU", this);
//        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.VideoFragment);
//        playerFragment.initialize("AIzaSyABI6ztl4D-KST6NgXZGxByR0aWgNnVxTU", this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Player = youTubePlayer;
        Player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                VideoList.setVisibility(b ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
        thumbnailViews.add(youTubeThumbnailView.getDrawable());
        VideoId.add(s);
        add();
    }

    @Override
    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        thumbnailLoader = youTubeThumbnailLoader;
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(PlaylistActivity.this);
        String abc = "PLHbAYx3uGq6Ol7hLw8K-ndfEwDML708jm";
        thumbnailLoader.setPlaylist(abc);
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public void add() {
        adapter.notifyDataSetChanged();
        if (thumbnailLoader.hasNext())
            thumbnailLoader.next();
    }

    public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyView> {

        public class MyView extends RecyclerView.ViewHolder {

            ImageView imageView;

            public MyView(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            }

        }

        @Override
        public VideoListAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_row, parent, false);
            return new MyView(itemView);
        }

        @Override
        public void onBindViewHolder(VideoListAdapter.MyView holder, final int position) {
            holder.imageView.setImageDrawable(thumbnailViews.get(position));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Player.cueVideo(VideoId.get(position));
                    listvideo =  VideoId.get(position);
                    Log.i("Data-YT","VideoId"+VideoId);
                    Log.i("Data-YT","VideoId - Postion"+VideoId.get(position));
                    startActivity(new Intent(PlaylistActivity.this, YouTubePlay.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return thumbnailViews.size();
        }
    }
}
