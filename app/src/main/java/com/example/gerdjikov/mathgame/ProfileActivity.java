package com.example.gerdjikov.mathgame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.gerdjikov.mathgame.Constants.*;

public class ProfileActivity extends AppCompatActivity {
    public boolean loggedIn = false;
    TextView mtvUserName, mtvHighScore, mtvRegisterHere, mETUserName, mChangePassword;
    Spinner mGameTypeSpinner;
    ArrayAdapter<CharSequence> mGameTypeAdapter;
    String gameType;
    SharedPreferences sharedPreferences;
    int highScore;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mtvUserName = findViewById(R.id.tvUsername);
        mtvHighScore = findViewById(R.id.tvHighScoreProfile);
        mtvRegisterHere = findViewById(R.id.registerHere);
        mETUserName = findViewById(R.id.etUserName);
        mChangePassword = findViewById(R.id.tvchangePassword);
        mETUserName.setFocusable(false);

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);

        mGameTypeSpinner = findViewById(R.id.sHighScore);
        mGameTypeAdapter = ArrayAdapter.createFromResource(this, R.array.game_type_array, R.layout.my_spinner_layout);
        mGameTypeAdapter.setDropDownViewResource(R.layout.my_spinner_layout);
        mGameTypeSpinner.setAdapter(mGameTypeAdapter);
        mGameTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gameType = (String) adapterView.getItemAtPosition(i);
                updateHighScore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        checkLogged();
    }

    void updateHighScore() {
        //should check if logged in then show highscore from DB if possible
        switch (gameType) {
            case "Addition":
                highScore = sharedPreferences.getInt(PREFS_ADDITION_HIGHSCORE, 0);
                mtvHighScore.setText(String.valueOf(highScore));
                break;
            case "Subtraction":
                highScore = sharedPreferences.getInt(PREFS_SUBTRACTION_HIGHSCORE, 0);
                mtvHighScore.setText(String.valueOf(highScore));
                break;
            case "Multiplication":
                highScore = sharedPreferences.getInt(PREFS_MULTIPLICATION_HIGHSCORE, 0);
                mtvHighScore.setText(String.valueOf(highScore));
                break;
            case "Root":
                highScore = sharedPreferences.getInt(PREFS_ROOT_HIGHSCORE, 0);
                mtvHighScore.setText(String.valueOf(highScore));
                break;
            case "Arcade":
                highScore = sharedPreferences.getInt(PREFS_ARCADE_HIGHSCORE, 0);
                mtvHighScore.setText(String.valueOf(highScore));
                break;
        }
    }

    void checkLogged() {
        loggedIn = sharedPreferences.getBoolean(PREFS_LOGGED_IN, false);
        if (loggedIn) {
            changePassword();
            mChangePassword.setVisibility(View.VISIBLE);
            mtvRegisterHere.setText(R.string.logout);
            mtvRegisterHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(PREFS_LOGGED_IN, false);
                    editor.putInt(PREFS_ADDITION_HIGHSCORE, 0);
                    editor.putInt(PREFS_MULTIPLICATION_HIGHSCORE, 0);
                    editor.putInt(PREFS_SUBTRACTION_HIGHSCORE, 0);
                    editor.putInt(PREFS_ROOT_HIGHSCORE, 0);
                    editor.putInt(PREFS_ARCADE_HIGHSCORE, 0);
                    editor.apply();
                    updateHighScore();
                    mETUserName.setText("");
                    mtvRegisterHere.setText(R.string.register_here);
                    checkLogged();
                }
            });
            username = sharedPreferences.getString(PREFS_USERNAME, null);
            mETUserName.setText(username);
        } else {
            mChangePassword.setVisibility(View.INVISIBLE);
            mtvRegisterHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.password_layout, null);
                    final EditText mUserName = mView.findViewById(R.id.usernameAD);
                    final EditText mPassword = mView.findViewById(R.id.passwordAD);
                    Button mRegister = mView.findViewById(R.id.buttonReg);
                    builder.setView(mView);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    mRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!mUserName.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
                                final String username = mUserName.getText().toString();
                                final String password = mPassword.getText().toString();
                                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                int user_id = jsonResponse.getInt("id");
                                                Toast.makeText(ProfileActivity.this, "User id = " + user_id, Toast.LENGTH_LONG).show();
                                                mETUserName.setText(mUserName.getText().toString());
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putBoolean(PREFS_LOGGED_IN, true);
                                                editor.putString(PREFS_USERNAME, mUserName.getText().toString());
                                                editor.putInt(PREFS_USER_ID, user_id);
                                                editor.apply();
                                                dialog.dismiss();
                                                Toast.makeText(ProfileActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
                                                checkLogged();
                                            } else
                                                Toast.makeText(ProfileActivity.this, "This name is already taken!", Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("STRING", response);
                                        }
                                    }
                                };
                                RegisterRequest registerRequest = new RegisterRequest(username, password, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                                queue.add(registerRequest);
                            } else
                                Toast.makeText(ProfileActivity.this, "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }

    void changePassword() {
        if (loggedIn) {
            mChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.password_layout, null);
                    final EditText mNewPassword1 = mView.findViewById(R.id.usernameAD);
                    final EditText mNewPassword2 = mView.findViewById(R.id.passwordAD);
                    mNewPassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mNewPassword1.setHint("New password");
                    mNewPassword2.setHint("Repeat password");
                    Button mChange = mView.findViewById(R.id.buttonReg);
                    mChange.setText("Change password");
                    builder.setView(mView);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    final int user_id = getSharedPreferences(PREFS_NAME, 0).getInt(PREFS_USER_ID, -1);
                    mChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mNewPassword1.getText().toString().equals(mNewPassword2.getText().toString())) {
                                final String new_password = mNewPassword1.getText().toString();
                                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                Toast.makeText(ProfileActivity.this, "Password changed!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(ProfileActivity.this, "Error changing password!", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("SD", response);
                                        }
                                    }
                                };
                                ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(user_id, new_password, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                                queue.add(changePasswordRequest);
                            } else {
                                Toast.makeText(ProfileActivity.this, "Please repeat password correctly!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            });

        }
    }

}
