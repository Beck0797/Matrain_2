package com.beck.matrain;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    //Global Variables

    private static final String TAG = "bos";
    FirebaseFirestore mFirestore;

    List<Score> mScoreList = new ArrayList<>();
    RecyclerView scoresRecyclerView;
    ScoreRecyclerAdapter mScoreRecyclerAdapter;
    AdRequest adRequest;
    ProgressBar mProgressBar;
    TextView loadingAd;
    private boolean reachedBottom;
    private boolean isFirstPageFirstLoad = true;
    private DocumentSnapshot lastVisible;
    private InterstitialAd mInterstitialAd;

    //Gets Ranking data from Firebase
    private void loadData() {

        Query firstQuery = mFirestore.collection("Scores").orderBy("score", Query.Direction.DESCENDING).limit(6);

        firstQuery.addSnapshotListener(LeaderBoardActivity.this, (queryDocumentSnapshots, e) -> {

            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {

                if (isFirstPageFirstLoad) {
                    lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                }

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                    if (documentChange.getType() == DocumentChange.Type.ADDED) {

                        //                                Log.i(TAG, "size : " + queryDocumentSnapshots.size());


                        Score score = documentChange.getDocument().toObject(Score.class);

                        //                            if list is in first page, new item is added to the end else to the beginning of the list
                        if (isFirstPageFirstLoad) {

                            mScoreList.add(score);

                        } else {

                            mScoreList.add(0, score);

                        }

                        mScoreRecyclerAdapter.notifyDataSetChanged();

                    }


                }

                isFirstPageFirstLoad = false;

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadAd();
    }

    // Shows the Google Ad
//    private void loadAd() {
//        MobileAds.initialize(this, initializationStatus -> Log.i(TAG, "onInitializationComplete: " + initializationStatus.toString()));
//
//
//        if (mInterstitialAd.isLoaded()) {
//
//            mInterstitialAd.show();
//
//            mInterstitialAd.setAdListener(new AdListener() {
//
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                    loadData();
//                    checkScrolling();
//                }
//            });
//            mProgressBar.setVisibility(View.INVISIBLE);
//            loadingAd.setVisibility(View.INVISIBLE);
//
//        }
//
//
//    }


    //    Checks whether use has Internet Connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_leader_board);

        if (!isOnline()) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.no_internet_notif)
                    .setNegativeButton(R.string.cancel_text, (dialog, which) -> finish()).setPositiveButton(R.string.connect_to_wifi, (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS))).show();


        } else {



            mFirestore = FirebaseFirestore.getInstance();

            scoresRecyclerView = findViewById(R.id.scoreRecyclerView);
//            mProgressBar = findViewById(R.id.adProgressBar);
//            loadingAd = findViewById(R.id.loadingAdText);

            mScoreRecyclerAdapter = new ScoreRecyclerAdapter(mScoreList);

//            mInterstitialAd = new InterstitialAd(this);
//            mInterstitialAd.setAdUnitId("ca-app-pub-1915468079410759/3959597243");
//
//            adRequest = new AdRequest.Builder().build();
//
//            mInterstitialAd.loadAd(adRequest);
//
//            mInterstitialAd.setAdListener(new AdListener() {
//                public void onAdLoaded() {
//                    mProgressBar.setVisibility(View.VISIBLE);
//
//                    loadAd();
//                }
//            });
//
//            mProgressBar.setIndeterminate(true);
            loadData();
            checkScrolling();


            scoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            scoresRecyclerView.setAdapter(mScoreRecyclerAdapter);


//            checkScrolling();


        }

    }

    private void checkScrolling() {
        //Check whether RecyclerView has reached bottom and if so loads more data from Firebase
        scoresRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                reachedBottom = !recyclerView.canScrollVertically(1);

                if (reachedBottom) {
//                    Toast.makeText(LeaderBoardActivity.this,"Reached bottom",Toast.LENGTH_SHORT).show();
                    loadMore();
                }
            }
        });

    }


    //    Loads 6 more score item from Firebase
    private void loadMore() {

        Query nextQuery = mFirestore.collection("Scores").orderBy("score", Query.Direction.DESCENDING).startAfter(lastVisible).limit(6);

        nextQuery.addSnapshotListener(LeaderBoardActivity.this, (queryDocumentSnapshots, e) -> {


            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {


                lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        //                                Log.i(TAG, "size : " + queryDocumentSnapshots.size());


                        Score score = documentChange.getDocument().toObject(Score.class);

                        mScoreList.add(score);
                        mScoreRecyclerAdapter.notifyDataSetChanged();

                    }
                    //                            Log.i(TAG, "onEvent: " + mSecretList.size());

                }


            }
        });


    }
}
