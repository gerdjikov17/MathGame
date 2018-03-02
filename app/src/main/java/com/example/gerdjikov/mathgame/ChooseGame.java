package com.example.gerdjikov.mathgame;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class ChooseGame extends AppCompatActivity {
    //Spinner mDifficulty;
    //String difficulty;
    //ArrayAdapter<CharSequence> mDifficultyAdapter;
    Spinner mGameTypeSpinner;
    ArrayAdapter<CharSequence> mGameTypeAdapter;
    String gameType;
    Button mStartGameButton;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
        MobileAds.initialize(this,
                "ca-app-pub-3293000837894400~1288254849");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
/*        mDifficultySpinner = findViewById(R.id.difficulty_spinner);
        mDifficultyAdapter = ArrayAdapter.createFromResource(this,R.array.difficulty_array,R.layout.my_spinner_layout);
        mDifficultyAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        mDifficultySpinner.setAdapter(mDifficultyAdapter);
        mDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficulty = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        mGameTypeSpinner = findViewById(R.id.game_type_spinner);
        mGameTypeAdapter = ArrayAdapter.createFromResource(this,R.array.game_type_array,R.layout.my_spinner_layout);
        mGameTypeAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        mGameTypeSpinner.setAdapter(mGameTypeAdapter);
        mGameTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gameType = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mStartGameButton = findViewById(R.id.start_game_button);
        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean goOn=false;
                /*
                if(difficulty==null){
                    Toast.makeText(ChooseGame.this,"Please select Difficulty",Toast.LENGTH_SHORT).show();
                }*/
                if (gameType == null) {
                    Toast.makeText(ChooseGame.this,"Please select Game Type",Toast.LENGTH_SHORT).show();
                }
                else goOn=true;
                if(goOn){
                    Intent intent = new Intent(ChooseGame.this,GameActivity.class);
                    //intent.putExtra("difficulty",difficulty);
                    intent.putExtra("gameType",gameType);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChooseGame.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
