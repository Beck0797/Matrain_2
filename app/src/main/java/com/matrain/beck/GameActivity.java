package com.matrain.beck;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameActivity extends AppCompatActivity {


    //  Global variables
    private static final String TAG = "bos";
    public static Activity gameActivity;
    private TextView firstNum;
    private TextView secondNum;
    private TextView operatorSign;
    private TextView userAnswerTextView;
    private SeekBar timerSeekBar;
    private int level = 1;
    private final Random random = new Random();
    private final List<Integer> randomFourNumbers = new ArrayList<>();
    private int answer;
    private TextView scoreTextView;
    public static Boolean isSoundOn = true;
    private CountDownTimer mCountDownTimer;
    private int score = 0;
    private String userAnswer = "";
    private int timeLeft = 30000;
    private MediaPlayer mediaPlayer;
    private TableLayout mTableLayout;
    String num1;
    String num2;

    public void numPressed(View view) {
        int answer_length = 0;
        if(answer < 0){
            answer_length = (int)(Math.log10(Math.abs(answer))+2);

        }else{
            answer_length = (int)(Math.log10(answer)+1);
        }



        if (userAnswer.length() < 5) {

            switch (view.getId()) {
                case R.id.minusSign: {
                    if (userAnswer.length() == 0) {
                        userAnswer = userAnswer.concat("-");

                    }
                    break;
                }

                case R.id.oneNum: {
                    userAnswer = userAnswer.concat("1");
                    break;
                }
                case R.id.twoNum: {
                    userAnswer = userAnswer.concat("2");
                    break;
                }
                case R.id.threeNum: {
                    userAnswer = userAnswer.concat("3");
                    break;
                }
                case R.id.fourNum: {
                    userAnswer = userAnswer.concat("4");
                    break;
                }
                case R.id.fiveNum: {
                    userAnswer = userAnswer.concat("5");
                    break;
                }
                case R.id.sixNum: {
                    userAnswer = userAnswer.concat("6");
                    break;
                }
                case R.id.sevenNum: {
                    userAnswer = userAnswer.concat("7");
                    break;
                }
                case R.id.eightNum: {
                    userAnswer = userAnswer.concat("8");
                    break;
                }
                case R.id.nineNum: {
                    userAnswer = userAnswer.concat("9");
                    break;
                }
                case R.id.zeroNum: {
                    userAnswer = userAnswer.concat("0");
                    break;
                }
                case R.id.delBtn: {
                    if (userAnswer != null && !userAnswer.isEmpty()) {
                        userAnswer = userAnswer.substring(0, userAnswer.length() - 1);
                        break;
                    }
                    break;


                }
//                case R.id.okBtn: {
//                    num1 = "";
//                    num2 = "";
//                    if (userAnswer != null && !userAnswer.isEmpty()) {
//                        if (userAnswer.length() < 7) {
//
//                            int userAnswerInt = Integer.parseInt(userAnswer);
//                            userAnswerTextView.setText("");
//                            userAnswer = "";
//                            checkAnswer(userAnswerInt);
//                        }
//                    }
//
//
//                    break;
//                }


                default:
                    Log.i(TAG, "default");
                    break;
            }
            if(userAnswer.length() == answer_length){
                num1 = "";
                num2 = "";
                if (userAnswer != null && !userAnswer.isEmpty()) {
                    if (userAnswer.length() < 7) {

                        int userAnswerInt = Integer.parseInt(userAnswer);
                        userAnswerTextView.setText("");
                        userAnswer = "";
                        checkAnswer(userAnswerInt);
                    }
                }
            }

            userAnswerTextView.setText(userAnswer);
        } else {
            userAnswer = "";
        }


    }

    private void checkAnswer(int userAnswerInt) {


        if (userAnswerInt == answer) {
            if (level == 1) {
                score++;
            } else if (level == 2) {
                score+=2;
            } else if (level == 3) {
                score+=2;
            }

            mediaPlayer = MediaPlayer.create(this, R.raw.correct);
//            Log.i(TAG, "checkAnswer: correct");


            timeLeft += 2000;
            mCountDownTimer.cancel();
            setTimer();

            scoreTextView.setText(String.valueOf(score));
            problemGenerator();
        } else {
            if (level == 1) {
                timeLeft -= 2000;
            } else if (level == 2) {
                timeLeft -= 1000;
            } else if (level == 3) {
                timeLeft -= 500;
            }

            mCountDownTimer.cancel();
            setTimer();
            mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
//            Log.i(TAG, "checkAnswer: wrong");
            problemGenerator();
        }
        if (isSoundOn) {
            mediaPlayer.start();
        }
    }


    public void setTimer() {
//        Log.i(TAG, "setTimer: called ");

        mCountDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerSeekBar.setProgress((int) (millisUntilFinished / 100));
                timeLeft = (int) (millisUntilFinished);

            }

            @Override
            public void onFinish() {
                timerSeekBar.setProgress(0);
                userAnswer = "";


                if (isSoundOn) {
                    mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.complete);
                    mediaPlayer.start();
                }
                Intent finishIntent = new Intent(GameActivity.this, FinishActivity.class);
                finishIntent.putExtra("score", score);
                finishIntent.putExtra("level", level);

                startActivity(finishIntent);
                score = 0;
                finish();
//                timeLeft = 0;
            }
        }.start();
    }


    public void problemGenerator() {
        Log.i(TAG, " level " + level);
        randomFourNumbers.add(random.nextInt((99) + 1) + 1);
        randomFourNumbers.add(random.nextInt((99) + 1) + 1);

        num1 = randomFourNumbers.get(0).toString();
        num2 = randomFourNumbers.get(1).toString();
        int operatorNumber;
        Log.i(TAG, "num1 " + num1);
        Log.i(TAG, "num2 " + num2);

        switch (level) {

            case 1: {


                firstNum.setText(num1);
                secondNum.setText(num2);
                operatorSign.setText("+");
                answer = randomFourNumbers.get(0) + randomFourNumbers.get(1);
                break;
            }

            case 2: {

                operatorNumber = random.nextInt(2);
                switch (operatorNumber) {
                    case 0: {

                        firstNum.setText(num1);
                        secondNum.setText(num2);
                        operatorSign.setText("+");
                        answer = randomFourNumbers.get(0) + randomFourNumbers.get(1);

                    }

                    case 1: {
                        firstNum.setText(num1);
                        secondNum.setText(num2);
                        operatorSign.setText("-");
                        answer = randomFourNumbers.get(0) - randomFourNumbers.get(1);
                    }
                }
                break;

            }
            case 3: {
                randomFourNumbers.add(random.nextInt((10) + 1) + 1);
                randomFourNumbers.add(random.nextInt((99) + 1) + 1);

                String multiplyNum1 = randomFourNumbers.get(2).toString();
                String multiplyNum2 = randomFourNumbers.get(3).toString();


                operatorNumber = random.nextInt(4);
                switch (operatorNumber) {
                    case 0: {

                        firstNum.setText(num1);
                        secondNum.setText(num2);
                        operatorSign.setText("+");

                        answer = randomFourNumbers.get(0) + randomFourNumbers.get(1);
                        break;

                    }

                    case 1: {
                        firstNum.setText(num1);
                        secondNum.setText(num2);
                        operatorSign.setText("-");

                        answer = randomFourNumbers.get(0) - randomFourNumbers.get(1);
                        break;
                    }

                    case 2: {


                        firstNum.setText(multiplyNum1);
                        secondNum.setText(multiplyNum2);
                        operatorSign.setText("*");

                        answer = randomFourNumbers.get(2) * randomFourNumbers.get(3);
                        break;


                    }

                    case 3: {
                        if (randomFourNumbers.get(0) % randomFourNumbers.get(1) == 0) {

                            firstNum.setText(num1);
                            secondNum.setText(num2);
                            operatorSign.setText("/");

                            answer = randomFourNumbers.get(0) / randomFourNumbers.get(1);

                        } else {


                            firstNum.setText(multiplyNum1);
                            secondNum.setText(multiplyNum2);
                            operatorSign.setText("*");

                            answer = randomFourNumbers.get(2) * randomFourNumbers.get(3);
                            break;

                        }

                        break;
                    }
                }
                break;
            }
            default:
                Log.i(TAG, "default problem generator");
                break;

        }
        Log.i(TAG, "first: " + firstNum.getText() + " random1=" + randomFourNumbers.get(0));
        Log.i(TAG, "second: " + secondNum.getText() + " random2=" + randomFourNumbers.get(1));
        randomFourNumbers.clear();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game_layout);
//        Log.i(TAG, "time " + timeLeft);

        gameActivity = this;

        TextView pauseBtn = findViewById(R.id.pauseBtn);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        TextView currentLevelText = findViewById(R.id.modeTextView);
        scoreTextView = findViewById(R.id.scoreTextview);
        firstNum = findViewById(R.id.firstNumber);
        secondNum = findViewById(R.id.secondNumber);
        operatorSign = findViewById(R.id.operatorSign);
        userAnswerTextView = findViewById(R.id.userAnswer);
        mTableLayout = findViewById(R.id.tableLayout);

        level = getIntent().getIntExtra("level", 1);

        timerSeekBar.setEnabled(false);
        String modeString = "";

        switch (level) {

            case 1: {
                modeString = getString(R.string.easy_text);

                break;
            }
            case 2: {
                modeString = getString(R.string.medium_text);

                break;
            }

            case 3: {
                modeString = getString(R.string.hard_text);

                break;
            }
            default: {
                currentLevelText.setText("Mode: Unknown");
                break;
            }

        }
        currentLevelText.setText(modeString);

        problemGenerator();


        pauseBtn.setOnClickListener(v -> {
            pauseTheGame();
        });


//        setTimer();


    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.i(TAG, "onPause: ");
        mCountDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isSoundOn) {
            mTableLayout.setSoundEffectsEnabled(false);
            muteAll();
        }
//        Log.i(TAG, "onResume: " + isSoundOn);
//        if (!isTimerWorking){
        setTimer();
        problemGenerator();

    }

    private void muteAll() {
        Log.i(TAG, "muteAll: " + mTableLayout.getChildCount());
        for (int i = 0; i < mTableLayout.getChildCount(); i++) {
            for (int j = 0; j < ((TableRow) mTableLayout.getChildAt(i)).getChildCount(); j++) {
                ((TableRow) mTableLayout.getChildAt(i)).getChildAt(j).setSoundEffectsEnabled(false);
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            pauseTheGame();
            return true;
        }
        return false;
    }

    private void pauseTheGame() {
        mCountDownTimer.cancel();
        Intent pauseIntent = new Intent(GameActivity.this, PauseActivity.class);
        pauseIntent.putExtra("score", score);
        pauseIntent.putExtra("timeLeft", timeLeft);
        pauseIntent.putExtra("sound", isSoundOn);
        pauseIntent.putExtra("level", level);
        startActivity(pauseIntent);
    }
}
