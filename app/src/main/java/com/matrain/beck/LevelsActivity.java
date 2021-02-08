package com.matrain.beck;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends AppCompatActivity {

    int level;
    LinearLayout levelsHolder;
    TextView secondsToStart;
    TextView getReadyText;
    CountDownTimer mCountDownTimer;
    TextView logoText;


    public void setTimer() {
        mCountDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) != 0) {

                    secondsToStart.setText(Long.toString(millisUntilFinished / 1000));

                } else {

                    secondsToStart.setText(getString(R.string.go_text));
                    Intent gameIntent = new Intent(LevelsActivity.this, GameActivity.class);
                    gameIntent.putExtra("level", level);
                    startActivity(gameIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                }


            }

            @Override
            public void onFinish() {
            }
        }.start();
    }


    public void chooseLevel(View view) {
        switch (view.getId()) {
            case R.id.easyBtn:
                level = 1;
                break;
            case R.id.mediumBtn:
                level = 2;
                break;
            case R.id.hardBtn:
                level = 3;
                break;
//            default:
//                level = 0;
        }

        levelsHolder.setVisibility(View.INVISIBLE);
        getReadyText.setVisibility(View.VISIBLE);
        secondsToStart.setVisibility(View.VISIBLE);
        logoText.setVisibility(View.INVISIBLE);
        setTimer();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_levels);

        levelsHolder = findViewById(R.id.levelsContainer);
        secondsToStart = findViewById(R.id.secondsToStartText);
        getReadyText = findViewById(R.id.getReadyText);
        logoText = findViewById(R.id.logoTextLevels);

        secondsToStart.setVisibility(View.INVISIBLE);
        getReadyText.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
            Intent mainIntent = new Intent(LevelsActivity.this, MainActivity.class);

            startActivity(mainIntent);

            finish();
            finishAffinity();
        }


        return true;
    }
}
