package com.example.gerdjikov.mathgame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mLeftText, mSymbol, mRightText, mTimeLabel, mScore, mLives;
    Button mLeftButton, mMiddleButton, mRightButton, mBackButton;
    int score = 0, lives = 3;
    int leftNumber, rightNumber, resultNumber, multiplication;
    int secondsForTimer=10000;
    //Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    CountDownTimer timer;
    String difficulty,gameType;
    static boolean active = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        active=true;
        mSymbol = findViewById(R.id.symbol);
        setDifficulty();
        setGameType();
        timer = new CountDownTimer(secondsForTimer, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLabel.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(l)));
                if(l<3000) {
                    //vibrator.vibrate(500);
                }
                if(l<1000){
                    //vibrator.vibrate(500);
                }
            }

            @Override
            public void onFinish() {
                livesDown();
            }

        }.start();

        mLeftButton = findViewById(R.id.leftButton);
        mMiddleButton = findViewById(R.id.middleButton);
        mRightButton = findViewById(R.id.rightButton);
        mLeftText = findViewById(R.id.leftNumber);
        mRightText = findViewById(R.id.rightNumber);
        mTimeLabel = findViewById(R.id.timerText);
        mScore = findViewById(R.id.score);
        mLives = findViewById(R.id.lives);
//        mBackButton = findViewById(R.id.backButton);

        mMiddleButton.setOnClickListener(this);
        mLeftButton.setOnClickListener(this);
        mRightButton.setOnClickListener(this);
//      mBackButton.setOnClickListener(this);
        mScore.setText(String.valueOf(score));
        mLives.setText(String.valueOf(lives));

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
       /* if (view.getId()==R.id.backButton){
            Intent i = new Intent(GameActivity.this,MainActivity.class);
            startActivity(i);
            finish();
            active=false;
        }*/
    }

    public void scoreUp() {
        generateNumbers();
        score++;
        if(score%10==0){
            lives++;
            mLives.setText(String.valueOf(lives));
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
        Integer[] row = new Integer[3];
        leftNumber = (int) ((Math.random() * multiplication) +3);
        rightNumber = (int) ((Math.random() * multiplication) +3);

        if(gameType.equals("Addition"))
            resultNumber = leftNumber + rightNumber;

        if(gameType.equals("Multiplication"))
            resultNumber = leftNumber * rightNumber;

        if(gameType.equals("Subtraction")){
            if(leftNumber<rightNumber){
                int temp = leftNumber;
                leftNumber=rightNumber;
                rightNumber=temp;
            }
            resultNumber=leftNumber-rightNumber;
        }
        row[0] = resultNumber;

        if(getBoolean())row[1] = (int)(resultNumber*1.12);
        else row[1]=(int)(resultNumber*0.9);

        if(getBoolean())row[2] = (int)(resultNumber*1.2);
        else row[2]=(int)(resultNumber*0.8);

        Arrays.sort(row, new Comparator<Integer>()
        {
            @Override
            public int compare(Integer x, Integer y)
            {
                return x - y;
            }
        });

        mLeftText.setText(String.valueOf(leftNumber));
        mRightText.setText(String.valueOf(rightNumber));

        mLeftButton.setText(String.valueOf(row[0]));
        mRightButton.setText(String.valueOf(row[1]));
        mMiddleButton.setText(String.valueOf(row[2]));
    }

    private boolean getBoolean() {
        double num = Math.random();
        if(num>0.5)return true;
        else return false;
    }
    private void gameOver(){
        Intent i = new Intent(GameActivity.this,GameOverActivity.class);
        i.putExtra("score",score);
        i.putExtra("gameType",gameType);
        startActivity(i);
        finish();

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(GameActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        active=false;
        timer.cancel();
    }

    private void setDifficulty(){
        difficulty=(String)getIntent().getStringExtra("difficulty");
        switch(difficulty){
            case "Easy":secondsForTimer=10000;
                        break;
            case "Medium":secondsForTimer=7500;
                        break;
            case "Hard":secondsForTimer=5000;
                        break;
        }
    }
    private void setGameType(){
        gameType=(String)getIntent().getStringExtra("gameType");
        switch(gameType){
            case "Addition":
                multiplication=100;
                mSymbol.setText("+");
                break;
            case "Subtraction":
                multiplication=100;
                mSymbol.setText("-");
                break;
            case "Multiplication":
                multiplication=10;
                mSymbol.setText("*");
                break;
        }
    }

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
