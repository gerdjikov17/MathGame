package com.example.gerdjikov.mathgame;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.gerdjikov.mathgame.Constants.*;


public class UsernameFragment extends Fragment {

    Button mVolume, mProfile, mLogin;
    SharedPreferences sharedPreferences;
    MyDialogFragment myDialogFragment;
    FragmentManager fm;
    boolean logged;
    View view;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_username, container, false);
        Context context = getActivity();

        MobileAds.initialize(getActivity(),
                "ca-app-pub-3293000837894400~1288254849");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        mProfile = view.findViewById(R.id.profileButton);
        mVolume = view.findViewById(R.id.volumeButton);
        if (getActivity().getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_SOUND, false)) {
            mVolume.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
        } else {
            mVolume.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
        }
        mLogin = view.findViewById(R.id.fragmentLoginButton);
        checkLoggedIn();
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });
        if (!logged) mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(fm, "TAG_ALERT_DIALOG_FRAGMENT");
                if (!myDialogFragment.isVisible()) checkLoggedIn();
            }
        });
        if (getActivity().getClass() == ProfileActivity.class) {
            mProfile.setClickable(false);
        }
        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, 0).edit();
                if (getActivity().getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_SOUND, false)) {
                    editor.putBoolean(PREFS_SOUND, false);
                    mVolume.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);
                    Toast.makeText(getActivity(), "Sound off", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean(PREFS_SOUND, true);
                    mVolume.setBackgroundResource(R.drawable.ic_volume_off_black_24dp);
                    Toast.makeText(getActivity(), "Sound on", Toast.LENGTH_SHORT).show();
                }
                editor.apply();
            }
        });
        return view;
    }


    public void checkLoggedIn() {
        mLogin.setVisibility(View.VISIBLE);
        logged = sharedPreferences.getBoolean(PREFS_LOGGED_IN, false);
        if (logged) {
            mLogin.setVisibility(View.GONE);
        }
    }

}
