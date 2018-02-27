package com.example.gerdjikov.mathgame;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.gerdjikov.mathgame.Constants.*;


public class UsernameFragment extends Fragment {

    Button mSettings , mProfile, mLogin;
    SharedPreferences sharedPreferences;
    MyDialogFragment myDialogFragment;
    FragmentManager fm;
    boolean logged;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_username, container, false);
        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,0);
        mProfile = view.findViewById(R.id.profileButton);
        mSettings = view.findViewById(R.id.settingsButton);
        mLogin = view.findViewById(R.id.fragmentLoginButton);
        checkLoggedIn();
        mProfile.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),ProfileActivity.class);
                startActivity(i);
            }
        });
        if(!logged) mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(fm, "TAG_ALERT_DIALOG_FRAGMENT");
                if (!myDialogFragment.isVisible()) checkLoggedIn();
            }
        });
        if(getActivity().getClass()==ProfileActivity.class){
            mProfile.setClickable(false);
        }
        return view;
    }



    public void checkLoggedIn(){
        mLogin.setVisibility(View.VISIBLE);
        logged = sharedPreferences.getBoolean(PREFS_LOGGED_IN,false);
        if(logged){
            mLogin.setVisibility(View.GONE);
        }
    }

}
