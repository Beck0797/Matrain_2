package com.matrain.beck;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class aboutTextActivity extends AppCompatActivity {

    int viewCode;
    String aboutText;
    TextView aboutTypeText;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_text);


        aboutTypeText = findViewById(R.id.aboutTypeText);
        TextView aboutTextView = findViewById(R.id.aboutTextView);

        viewCode = getIntent().getIntExtra("viewcode", 44);

        if (viewCode == 1) {
            aboutTypeText.setText(getString(R.string.instructions_text));
            aboutText = getString(R.string.about_instructions);
        } else if (viewCode == 2) {
            aboutTypeText.setText(getString(R.string.rankings_text));
            aboutText = getString(R.string.about_ranking);
        } else {
            aboutTypeText.setText(getString(R.string.app_name));
            aboutText = getString(R.string.about_matrain);
        }


        aboutTextView.setMovementMethod(new ScrollingMovementMethod());
        aboutTextView.setText(aboutText);

        //Justifies the text
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            aboutTextView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
    }
}