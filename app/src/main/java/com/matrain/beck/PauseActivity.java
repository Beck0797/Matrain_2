package com.matrain.beck;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PauseActivity extends AppCompatActivity {

    private static final String TAG = "bos";
    Button resumeBtn;
    Button restartBtn;
    Button exitBtn;
    TextView scoreTextView;
    int score;
    int timeLeft;
    ImageView soundImage;
    Boolean isSoundOn;
    Button mainMenuBtn;
    TextView modeTextView;
    String levelText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pause);

        score = getIntent().getIntExtra("score", 0);
        timeLeft = getIntent().getIntExtra("timeLeft", 10000);
        isSoundOn = getIntent().getBooleanExtra("sound", true);
        int level = getIntent().getIntExtra("level", 55);
        if (level == 1) {
            levelText = getString(R.string.easy_text);
        } else if (level == 2) {
            levelText = getString(R.string.medium_text);
        } else if (level == 3) {
            levelText = getString(R.string.hard_text);
        }


        resumeBtn = findViewById(R.id.resumeBtn);
        restartBtn = findViewById(R.id.restartBtn);
        exitBtn = findViewById(R.id.exitBtn);
        scoreTextView = findViewById(R.id.pauseScore);
        soundImage = findViewById(R.id.soundImage);
        mainMenuBtn = findViewById(R.id.mainMenuBtn);
        modeTextView = findViewById(R.id.pauseModeText);

        modeTextView.setText(levelText);
        scoreTextView.setText(getString(R.string.score_text) + ": " + score);

        if (isSoundOn) {
            soundImage.setImageResource(R.drawable.ic_speaker_on);
        } else {
            soundImage.setImageResource(R.drawable.ic_volume_off_indicator);
        }


        soundImage.setOnClickListener(v -> {
            if (isSoundOn) {
                Log.i(TAG, "on  " + isSoundOn.toString());
                ((ImageView) v).setImageResource(R.drawable.ic_volume_off_indicator);
                GameActivity.isSoundOn = false;
                isSoundOn = false;
            } else {
                Log.i(TAG, "off " + isSoundOn.toString());
                ((ImageView) v).setImageResource(R.drawable.ic_speaker_on);
                GameActivity.isSoundOn = true;
                isSoundOn = true;
            }
        });

        mainMenuBtn.setOnClickListener(v -> {
            Intent mainIntent = new Intent(PauseActivity.this, MainActivity.class);
            GameActivity.gameActivity.finish();
            startActivity(mainIntent);
            finish();
        });

        resumeBtn.setOnClickListener(v -> {
            finish();
        });

        restartBtn.setOnClickListener(v -> {
            Intent levelIntent = new Intent(PauseActivity.this, LevelsActivity.class);

            startActivity(levelIntent);
        });

        exitBtn.setOnClickListener(v -> {
            finish();
            finishAffinity();
            System.exit(0);
        });


    }
}
