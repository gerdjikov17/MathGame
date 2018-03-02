package com.example.gerdjikov.mathgame;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity implements View.OnClickListener {
    Button mGameButton, mExitButton, mLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGameButton = findViewById(R.id.gameButton);
        mExitButton = findViewById(R.id.exitButton);
        mLoginButton = findViewById(R.id.loginButton);

        mGameButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);

        loadFragment();


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.gameButton){
            Intent i = new Intent(MainActivity.this,ChooseGame.class);
            startActivity(i);
            finish();
        }
        if(view.getId()==R.id.exitButton){
            super.finish();
        }
    }

    void loadFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        UsernameFragment fragment = new UsernameFragment();
        fragmentTransaction.add(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment();
    }
}
