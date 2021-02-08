package com.matrain.beck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    int viewCode;


    public void callFragment(View view) {
        Intent aboutInfoIntent = new Intent(AboutActivity.this, aboutTextActivity.class);
        if (view.getId() == R.id.instructionsBtn) {
            viewCode = 1;
            aboutInfoIntent.putExtra("viewcode", viewCode);

        } else if (view.getId() == R.id.aboutRanking) {
            viewCode = 2;
            aboutInfoIntent.putExtra("viewcode", viewCode);


        } else {
            viewCode = 3;
            aboutInfoIntent.putExtra("viewcode", viewCode);
        }
        startActivity(aboutInfoIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about);


    }


}
