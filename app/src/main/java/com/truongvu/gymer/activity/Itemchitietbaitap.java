package com.truongvu.gymer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.truongvu.gymer.R;
import com.truongvu.gymer.ultil.ItemBaiTap;

public class Itemchitietbaitap extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    String API_KEY="AIzaSyBYBd5VQFnzLNWr8Qz7zPm0kfUL9ixmdPg";
    int REQUEST_VIDEO=123;
    YouTubePlayerView youTubePlayerView;
    String link,id,content,title;
    private TextView mcontent;
    private TextView h_title;
    private ImageView h_back;
    private TextView mtitle;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.chitetbaitap);
        anhxa();

        id = getIntent().getStringExtra("id");
        content = getIntent().getStringExtra("content");
        link = getIntent().getStringExtra("video");
        title= getIntent().getStringExtra("title");
        String s="";
        if(title.length()>27)
            s = title.substring(0,27);
        else s = title;
        h_title.setText(s+"...");
        h_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Toast.makeText(Itemchitietbaitap.this, "ID: " + id, Toast.LENGTH_LONG).show();
        mcontent.setText(content);
        mtitle.setText(title);


        youTubePlayerView= (YouTubePlayerView) findViewById(R.id.myyoutube);
        youTubePlayerView.initialize(API_KEY, this);

    }

    private void anhxa() {
        mtitle=findViewById(R.id.bt_title);
        mcontent=findViewById(R.id.bt_content);
        h_back=findViewById(R.id.ct_back);
        h_title=findViewById(R.id.ct_title);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
         youTubePlayer.cueVideo(link);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    if (youTubeInitializationResult.isUserRecoverableError()){
        youTubeInitializationResult.getErrorDialog(Itemchitietbaitap.this, REQUEST_VIDEO);
    }else {
        Toast.makeText(this,"ERROR", Toast.LENGTH_SHORT).show();
    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_VIDEO){
            youTubePlayerView.initialize(API_KEY,Itemchitietbaitap.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}