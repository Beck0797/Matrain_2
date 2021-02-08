package com.matrain.beck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class FinishActivity extends AppCompatActivity {


    private static final String TAG = "bos";
    private static File file;
    int level;
    FirebaseFirestore mFirestore;
    String username;
    int height;
    int width;
    private int score;


    //    Checks whether the user has the Internet connection
    private boolean isOnline() {
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
        setContentView(R.layout.activity_finish);

        Button playAgainBtn = findViewById(R.id.playAgainBtn);
        TextView finishScoreView = findViewById(R.id.finishScoreTextView);
        Button shareScore = findViewById(R.id.shareScoreBtn);

        mFirestore = FirebaseFirestore.getInstance();


        if (MainActivity.username != null && !MainActivity.username.isEmpty()) {
            username = MainActivity.username;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        level = getIntent().getIntExtra("level", 0);
        score = getIntent().getIntExtra("score", 0);


        final KonfettiView viewKonfetti = findViewById(R.id.viewKonfetti);
        if (score >= 15) {
            viewKonfetti.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5f))
                    .setPosition((height / 2f) - 200, (width / 2f) - (100))
                    .burst(500);
        }


        String scoreText = getString(R.string.your_score_is_text).concat(" ").concat(String.valueOf(score)).concat(" ").concat(getString(R.string.ochkov));
        finishScoreView.setText(scoreText);


        // Save to SharedPrefs
        SharedPreferences preferences = this.getSharedPreferences("prefs", MODE_PRIVATE);


        List<Score> scoreArrayList = new ArrayList<>();
        try {
            if (preferences.getString("scores", " ").isEmpty()) {
                scoreArrayList = new ArrayList<>();
            } else {
                scoreArrayList = (List<Score>) ObjectSerializer.deserialize(preferences.getString("scores", ObjectSerializer.serialize(new ArrayList<Score>())));
            }
        } catch (
                IOException e) {
            Log.i(TAG, "error " + e.getMessage());
        }
        if (score > 1) {
            if (score > 5) {
                askToShare();
            }
            Score currentScore = new Score(username, level, score);
            scoreArrayList.add(currentScore);

            try {
                String serialized = ObjectSerializer.serialize((Serializable) scoreArrayList);
                preferences.edit().putString("scores", serialized).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        shareScore.setOnClickListener((
                View v) ->

        {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareMessage = getString(R.string.share_message1) + score + getString(R.string.ochkov);
                shareMessage += getString(R.string.share_message2);
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_score_text)));
            } catch (Exception e) {
                //e.toString();
            }
        });


        playAgainBtn.setOnClickListener(v ->

        {
            Intent newGameIntent = new Intent(FinishActivity.this, LevelsActivity.class);
            startActivity(newGameIntent);
            finish();
        });

    }

    private void askToShare() {
        Log.i(TAG, "askToShare: ");

        if (isOnline()) {

            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("username", username);
            scoreMap.put("level", level);
            scoreMap.put("score", score);


            mFirestore.collection("Scores").document().set(scoreMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(FinishActivity.this, getString(R.string.score_shared_message), Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Log.i(TAG, "onFailure: " + e.getMessage()));


        } else {
            Log.i(TAG, "not online");
        }
    }


}
