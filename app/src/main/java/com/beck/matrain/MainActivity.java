package com.beck.matrain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "bos";
    Intent intent;
    Button newGameButton;
    Button leaderBoardButton;
    Button settingsButton;
    Button exitButton;
    LinearLayout linearLayout;
    ConstraintLayout mainLoyout;
    public static String username;
    static TextView usernameLogoText;
    static TextView logo;

    public static void btnChangeOnClick(String s, String s2) {
        usernameLogoText.setVisibility(View.VISIBLE);
        usernameLogoText.setText(s2);
        username = s;

    }
//    Boolean  isFirstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
//
        username = sharedPref.getString("username", "none");
        if (username.equals("none")) {
            callRegActivity();

        }

        mainLoyout = findViewById(R.id.mainLayout);
        newGameButton = findViewById(R.id.newGameButton);
        leaderBoardButton = findViewById(R.id.leaderBoardButton);
        settingsButton = findViewById(R.id.settingsButton);
        exitButton = findViewById(R.id.exitButton);
        usernameLogoText = findViewById(R.id.usernameLogoText);
        logo = findViewById(R.id.logoTextMain);
        linearLayout = findViewById(R.id.linearLayout);

        usernameLogoText.setText(getString(R.string.username_text).concat(username));
        usernameLogoText.setOnClickListener(v -> {
            usernameLogoText.setVisibility(View.INVISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            EditNameFragment fragment = new EditNameFragment();
            fm.beginTransaction().replace(R.id.myFrame, fragment).commit();

        });
        logo.setOnClickListener(v -> {
            closeEditName();
            Toast.makeText(this, R.string.math_train_text, Toast.LENGTH_SHORT).show();
        });

        newGameButton.setOnClickListener(this);
        leaderBoardButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        mainLoyout.setOnClickListener(v -> {
            closeEditName();
        });


    }

    private void closeEditName() {
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.myFrame);
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fm.beginTransaction();
        if (fragment != null) {
            usernameLogoText.setVisibility(View.VISIBLE);
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    private void callRegActivity() {
        Intent regIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(regIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newGameButton:
                intent = new Intent(this, LevelsActivity.class);
                break;

            case R.id.leaderBoardButton:
                intent = new Intent(this, ChooseRankingType.class);
                break;

            case R.id.settingsButton:
                intent = new Intent(this, AboutActivity.class);
                break;

            case R.id.exitButton:
                finish();
                finishAffinity();
                System.exit(0);
                break;

        }

        startActivity(intent);
        closeEditName();

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.myFrame);
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fm.beginTransaction();
        if (fragment != null) {
            usernameLogoText.setVisibility(View.VISIBLE);
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExitPressedOnce) {
                finish();
                finishAffinity();
                System.exit(0);
                return true;
            }else{
                Toast.makeText(this, R.string.back_to_exit, Toast.LENGTH_SHORT).show();
                doubleBackToExitPressedOnce = true;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }

        }
        return false;
    }


}
