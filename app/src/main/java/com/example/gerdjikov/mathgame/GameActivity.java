package com.example.gerdjikov.mathgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static com.example.gerdjikov.mathgame.Constants.PREFS_NAME;
import static com.example.gerdjikov.mathgame.Constants.PREFS_SOUND;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private static boolean active = true;
    private TextView mProblem, mTimeLabel, mScore, mLives;
    private Button mLeftButton, mMiddleButton, mRightButton;
    private int score = 0, lives = 3;
    private int resultNumber;
    private int secondsForTimer = 10000;
    private CountDownTimer timer;
    private String gameType;
    private Numbers numbers;
    private MediaPlayer player;
    //String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        active=true;

        //setDifficulty();
        gameType = getIntent().getStringExtra("gameType");
        numbers = new Numbers("Easy");

        timer = new CountDownTimer(secondsForTimer, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLabel.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(l)));
            }

            @Override
            public void onFinish() {
                livesDown();
            }

        }.start();

        mLeftButton = findViewById(R.id.leftButton);
        mMiddleButton = findViewById(R.id.middleButton);
        mRightButton = findViewById(R.id.rightButton);
        mTimeLabel = findViewById(R.id.timerText);
        mScore = findViewById(R.id.score);
        mLives = findViewById(R.id.lives);
        mProblem = findViewById(R.id.problem);

        mMiddleButton.setOnClickListener(this);
        mLeftButton.setOnClickListener(this);
        mRightButton.setOnClickListener(this);
        mScore.setText(String.valueOf(score));
        mLives.setText(String.valueOf(lives));
        if (getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_SOUND, true)) {
            player = MediaPlayer.create(this, R.raw.blue_wonder);
            player.setLooping(true);
            player.start();
        }
        generateNumbers();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.middleButton) {
            if ((Integer.parseInt(mMiddleButton.getText().toString())) == resultNumber) {
                scoreUp();
            } else {
                livesDown();
            }
        }
        if (view.getId() == R.id.leftButton) {
            if ((Integer.parseInt(mLeftButton.getText().toString())) == resultNumber) {
                scoreUp();
            } else {
                livesDown();
            }
        }
        if (view.getId() == R.id.rightButton) {
            if ((Integer.parseInt(mRightButton.getText().toString())) == resultNumber) {
                scoreUp();
            } else {
                livesDown();
            }
        }
    }

    public void scoreUp() {
        generateNumbers();
        score++;
        if ((score % 10 == 0) && lives < 3) {
            lives++;
            mLives.setText(String.valueOf(lives));
        }
        if (score == 20) {
            numbers.setDifficulty("Medium");
            secondsForTimer -= 1000;
        }
        if (score == 50) {
            numbers.setDifficulty("Hard");
            secondsForTimer -= 1000;
        }
        if (score == 100) {
            numbers.setDifficulty("Insane");
            secondsForTimer -= 1000;
        }
        mScore.setText(String.valueOf(score));
        timer.start();
    }

    public void livesDown() {
        lives--;
        if(lives==0&&active)gameOver();
        mLives.setText(String.valueOf(lives));
        timer.start();
        generateNumbers();
    }

    private void generateNumbers() {

        int[] row;
        switch (gameType) {
            case "Addition":
                numbers.generateAddition();
                break;
            case "Subtraction":
                numbers.generateSubtraction();
                break;
            case "Multiplication":
                numbers.generateMultiplication();
                break;
            case "Root":
                numbers.generateRoot();
                break;
            case "Arcade":
                numbers.generateArcade();
                break;
        }

        row = numbers.getArr();
        resultNumber = numbers.getResultNumber();
        mProblem.setText(numbers.getProblem());
        mLeftButton.setText(String.valueOf(row[0]));
        mRightButton.setText(String.valueOf(row[1]));
        mMiddleButton.setText(String.valueOf(row[2]));
    }

    private void gameOver(){
        Intent i = new Intent(GameActivity.this,GameOverActivity.class);
        i.putExtra("score",score);
        i.putExtra("gameType",gameType);
        startActivity(i);
        if (getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_SOUND, true)) player.stop();
        finish();

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(GameActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        active=false;
        timer.cancel();
        if (getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_SOUND, true)) player.stop();
    }

/*    private void setDifficulty(){
        difficulty=getIntent().getStringExtra("difficulty");
        switch(difficulty){
            case "Easy":secondsForTimer=10000;
                        break;
            case "Medium":secondsForTimer=7500;
                        break;
            case "Hard":secondsForTimer=7500;
                        break;
            case "Insane":secondsForTimer=5000;
                        break;
        }
    }*/

    @Override
    protected void onPostResume() {
        super.onPostResume();
        active=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        active=true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        active=true;
    }

}
