package com.matrain.beck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    EditText usernameEditText;
    String username;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEdittext);

        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        if (!sharedPref.getString("username", "0").equals("0")) {
//            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
//            startActivity(mainIntent);
//            finish();
//        }

        editor = sharedPref.edit();


        usernameEditText.setOnKeyListener((v, keyCode, event) -> {

            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (!usernameEditText.getText().toString().isEmpty()) {
                    if (usernameEditText.getText().toString().length() < 35) {
                        username = usernameEditText.getText().toString();
                        editor.putString("username", username);
                        editor.commit();
                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();

                        return true;
                    } else {
                        Toast.makeText(RegisterActivity.this, R.string.username_length_warning, Toast.LENGTH_LONG).show();
                    }
                }
            }
            return false;
        });



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            finishAffinity();
            System.exit(0);
            return true;
        }
        return false;
    }


}

