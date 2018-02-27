package com.example.gerdjikov.mathgame;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.gerdjikov.mathgame.Constants.PREFS_ADDITION_HIGHSCORE;
import static com.example.gerdjikov.mathgame.Constants.PREFS_ARCADE_HIGHSCORE;
import static com.example.gerdjikov.mathgame.Constants.PREFS_LOGGED_IN;
import static com.example.gerdjikov.mathgame.Constants.PREFS_MULTIPLICATION_HIGHSCORE;
import static com.example.gerdjikov.mathgame.Constants.PREFS_NAME;
import static com.example.gerdjikov.mathgame.Constants.PREFS_ROOT_HIGHSCORE;
import static com.example.gerdjikov.mathgame.Constants.PREFS_SUBTRACTION_HIGHSCORE;
import static com.example.gerdjikov.mathgame.Constants.PREFS_USERNAME;
import static com.example.gerdjikov.mathgame.Constants.PREFS_USER_ID;

/**
 * Created by Gerdjikov on 19.2.2018 г..
 */

public class MyDialogFragment extends DialogFragment {
    SharedPreferences sharedPreferences;

    public MyDialogFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login,container);
        final EditText mUserName = view.findViewById(R.id.etUserNameLogin);
        final EditText mPassword = view.findViewById(R.id.etPasswordLog);
        final Button mLogin = view.findViewById(R.id.loginButton);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameLogin = mUserName.getText().toString();
                final String passwordLogin = mPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.optBoolean("success");
                        //String state = jsonResponse.getString("state");
                            if(success){

                                String username = jsonResponse.getString("username");
                                int highscoreAdd = jsonResponse.getInt("highscoreAdd");
                                int highscoreMult = jsonResponse.getInt("highscoreMult");
                                int highscoreSub  = jsonResponse.getInt("highscoreSub");
                                int highscoreRoot  = jsonResponse.getInt("highscoreRoot");
                                int highscoreArcade  = jsonResponse.getInt("highscoreArcade");
                                int user_id = jsonResponse.getInt("user_id");
                                Context context = getActivity();
                                sharedPreferences = context.getSharedPreferences(PREFS_NAME,0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();


                                editor.putBoolean(PREFS_LOGGED_IN,true);
                                editor.putString(PREFS_USERNAME,username);
                                editor.putInt(PREFS_ADDITION_HIGHSCORE,highscoreAdd);
                                editor.putInt(PREFS_MULTIPLICATION_HIGHSCORE,highscoreMult);
                                editor.putInt(PREFS_SUBTRACTION_HIGHSCORE,highscoreSub);
                                editor.putInt(PREFS_ROOT_HIGHSCORE,highscoreRoot);
                                editor.putInt(PREFS_ARCADE_HIGHSCORE,highscoreArcade);
                                editor.putInt(PREFS_USER_ID,user_id);

                                editor.apply();
                                getDialog().dismiss();

                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                UsernameFragment fragment = new UsernameFragment();
                                fragmentTransaction.replace(R.id.frame_layout,fragment);
                                fragmentTransaction.commit();
                                Log.d("S",response);
                                Log.d("S",user_id+"");
                            }
                            else{
                                Toast.makeText(getActivity(),"LOGIN FAILED ",Toast.LENGTH_LONG).show();
                                Log.d("SD",response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SD",response);
                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(usernameLogin,passwordLogin,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(loginRequest);
            }
        });
        return view;
    }

}
