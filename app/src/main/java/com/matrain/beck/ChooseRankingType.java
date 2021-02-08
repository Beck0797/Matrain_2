package com.matrain.beck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

public class ChooseRankingType extends AppCompatActivity {

    AdView mAdView;

    public void chooseRanking(View view) {

        if (view.getId() == R.id.localBtn) {
            Intent localRankingIntent = new Intent(ChooseRankingType.this, LocalRanking.class);
            startActivity(localRankingIntent);

        } else if (view.getId() == R.id.globalBtn) {
            Intent globalRankingIntent = new Intent(ChooseRankingType.this, LeaderBoardActivity.class);
            startActivity(globalRankingIntent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_ranking_type);

//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


    }
}