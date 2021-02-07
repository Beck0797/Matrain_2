package com.beck.matrain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalRanking extends AppCompatActivity {

    private static final String TAG = "bos";
    RecyclerView mRecyclerView;
    List<Score> mScoreArrayList = new ArrayList<>();
    List<Score> newScoreList = new ArrayList<>();
    SharedPreferences prefs;
    ScoreRecyclerAdapter mScoreRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_local_ranking);

        mRecyclerView = findViewById(R.id.localScoreRecyclerView);
        mScoreRecyclerAdapter = new ScoreRecyclerAdapter(newScoreList);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mScoreRecyclerAdapter);


        prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        Log.i(TAG, "prefs " + prefs.getString("scores", "no"));


        try {
            mScoreArrayList = (List<Score>) ObjectSerializer.deserialize(prefs.getString("scores", ObjectSerializer.serialize(new ArrayList<Score>())));
//            mScoreArrayList.add(new Score("hah", "oson", 14));
            if (!mScoreArrayList.isEmpty()) {
                for (int i = 0; i < mScoreArrayList.size(); i++) {
                    Score score = mScoreArrayList.get(i);
                    newScoreList.add(score);
                }
                Collections.sort(newScoreList);
                mScoreRecyclerAdapter.notifyDataSetChanged();
            }
//            mScoreRecyclerAdapter.notifyDataSetChanged();

            Log.i(TAG, "size  " + mScoreArrayList.size());
        } catch (Exception e) {
            Log.i(TAG, "error " + e.getMessage());
        }


    }

    public void clearRanking(View view) {

        prefs.edit().putString("scores", "").apply();
        Toast.makeText(this, R.string.clear_local_ranking_notif, Toast.LENGTH_SHORT).show();
        finish();
    }
}