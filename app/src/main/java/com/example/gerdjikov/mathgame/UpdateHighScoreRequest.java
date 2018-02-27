package com.example.gerdjikov.mathgame;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Gerdjikov on 20.2.2018 г..
 */

public class UpdateHighScoreRequest extends StringRequest {
    private static final String UPDATE_HIGHSCORE_URL = "http://doperich.000webhostapp.com/UpdateHighScore.php";
    Map<String,String> params;

    public UpdateHighScoreRequest(int user_id, int highscore1, int highscore2,int highscore3, int highscore4, int highscore5, Response.Listener<String> listener){
            super(Method.POST,UPDATE_HIGHSCORE_URL,listener,null);
            params = new HashMap<>();
            params.put("user_id",user_id+"");
            params.put("highscore1", highscore1+"");
            params.put("highscore2",highscore2+"");
            params.put("highscore3",highscore3+"");
            params.put("highscore4",highscore4+"");
            params.put("highscore5",highscore5+"");

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
}
