package com.erickdev.getmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.erickdev.getmovies.recylerview.ItemProductNowPlaying;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailMovie extends AppCompatActivity {

    ImageButton btnback;
    ImageView poster;
    TextView desc, title,rate,release,popularity;
    String idpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent intent = getIntent();
        idpath = intent.getStringExtra("idpath");

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            finish();
          }
        });

        poster = findViewById(R.id.ivposter);
        desc = findViewById(R.id.desc);
        title = findViewById(R.id.title);
        rate = findViewById(R.id.rate);
        release = findViewById(R.id.release);
        popularity = findViewById(R.id.popularity);

      String urlll = "https://api.themoviedb.org/3/movie/movie_id?api_key=284f4b913d3e6938b6a6f860ce961ada&language=en-US";

      String url = urlll.replaceFirst("movie_id", intent.getStringExtra("idpath"));
      final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try {
              JSONObject jsonObject = new JSONObject(response);
              title.setText(jsonObject.getString("title"));
              desc.setText(jsonObject.getString("overview"));
              rate.setText(jsonObject.getString("vote_average"));
              release.setText("Release Date : " + jsonObject.getString("release_date"));
              popularity.setText("Popularity : " + jsonObject.getString("popularity"));
              Glide.with(DetailMovie.this).load("https://image.tmdb.org/t/p/w500"+ jsonObject.getString("poster_path")).into(poster);

            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
          }
        });
      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }
}
