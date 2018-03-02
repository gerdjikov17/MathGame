package com.example.gerdjikov.mathgame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.gerdjikov.mathgame.Constants.*;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {
    Button mMenu, mTryAgain;
    TextView mScore;
    SharedPreferences highScorePref;
    boolean isLogged=false;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

/*        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());*/


        mMenu = findViewById(R.id.goToMenuButton);
        mTryAgain = findViewById(R.id.tryAgainButton);
        mScore = findViewById(R.id.scoreOver);
        mMenu.setOnClickListener(this);
        mTryAgain.setOnClickListener(this);
        int score = getIntent().getIntExtra("score", 0);
        mScore.setText(String.format("%s%s", getString(R.string.score), String.valueOf(score)));
        String gameType = getIntent().getStringExtra("gameType");
        isLogged = getSharedPreferences(PREFS_NAME,0).getBoolean(PREFS_LOGGED_IN,false);
        updateHighScore(gameType, score);

        loadFragment();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tryAgainButton) {
            Intent i = new Intent(GameOverActivity.this, ChooseGame.class);
/*            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
            startActivity(i);
            finish();
        }
        if (view.getId() == R.id.goToMenuButton) {
            Intent i = new Intent(GameOverActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    void loadFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        UsernameFragment fragment = new UsernameFragment();
        fragmentTransaction.add(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    void updateHighScore(String gameType, int score) {
        highScorePref = getSharedPreferences(PREFS_NAME, 0);
        if (gameType.equals("Addition")) {
            int additionHighScore = highScorePref.getInt(PREFS_ADDITION_HIGHSCORE, 0);
            if (score > additionHighScore) {
                SharedPreferences.Editor editor = highScorePref.edit();
                editor.putInt(PREFS_ADDITION_HIGHSCORE, score);
                editor.apply();
            }
        }
        if (gameType.equals("Subtraction")) {
            int subtractionHighScore = highScorePref.getInt(PREFS_SUBTRACTION_HIGHSCORE, 0);
            if (score > subtractionHighScore) {
                SharedPreferences.Editor editor = highScorePref.edit();
                editor.putInt(PREFS_SUBTRACTION_HIGHSCORE, score);
                editor.apply();
            }

        }
        if (gameType.equals("Multiplication")) {
            int multiplicationHighScore = highScorePref.getInt(PREFS_MULTIPLICATION_HIGHSCORE, 0);
            if (score > multiplicationHighScore) {
                SharedPreferences.Editor editor = highScorePref.edit();
                editor.putInt(PREFS_MULTIPLICATION_HIGHSCORE, score);
                editor.apply();
            }
        }
        if (gameType.equals("Root")) {
            int rootHighScore = highScorePref.getInt(PREFS_ROOT_HIGHSCORE, 0);
            if (score > rootHighScore) {
                SharedPreferences.Editor editor = highScorePref.edit();
                editor.putInt(PREFS_ROOT_HIGHSCORE, score);
                editor.apply();
            }
        }
        if (gameType.equals("Arcade")) {
            int arcadeHighScore = highScorePref.getInt(PREFS_ARCADE_HIGHSCORE, 0);
            if (score > arcadeHighScore) {
                SharedPreferences.Editor editor = highScorePref.edit();
                editor.putInt(PREFS_ARCADE_HIGHSCORE, score);
                editor.apply();
            }
        }
        if(isLogged)
        updateMysql(highScorePref.getInt(PREFS_ADDITION_HIGHSCORE,0), highScorePref.getInt(PREFS_MULTIPLICATION_HIGHSCORE,0), highScorePref.getInt(PREFS_SUBTRACTION_HIGHSCORE,0),
                highScorePref.getInt(PREFS_ROOT_HIGHSCORE,0), highScorePref.getInt(PREFS_ARCADE_HIGHSCORE,0));

    }
    void updateMysql(int highscore1, int highscore2, int highscore3,int highscore4, int highscore5){
        final int user_id = getSharedPreferences(PREFS_NAME,0).getInt(PREFS_USER_ID,-1);
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        Toast.makeText(GameOverActivity.this,"High score updated!",Toast.LENGTH_LONG).show();
                        Log.d("SD",user_id+"");
                    }
                    else {
                        Toast.makeText(GameOverActivity.this,"Registration error!",Toast.LENGTH_LONG).show();
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                    Log.d("SD",response);
                }
            }
        };

        UpdateHighScoreRequest registerRequest = new UpdateHighScoreRequest(user_id,highscore1, highscore2,highscore3, highscore4, highscore5 ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(GameOverActivity.this);
        queue.add(registerRequest);
    }
}
