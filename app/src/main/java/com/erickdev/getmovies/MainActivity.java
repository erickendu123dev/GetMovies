package com.erickdev.getmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.erickdev.getmovies.recylerview.ItemAdapterNowPlaying;
import com.erickdev.getmovies.recylerview.ItemAdapterPopular;
import com.erickdev.getmovies.recylerview.ItemAdapterTopRated;
import com.erickdev.getmovies.recylerview.ItemAdapterUpComing;
import com.erickdev.getmovies.recylerview.ItemProductNowPlaying;
import com.erickdev.getmovies.recylerview.ItemProductPopular;
import com.erickdev.getmovies.recylerview.ItemProductTopRated;
import com.erickdev.getmovies.recylerview.ItemProductUpComing;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    RecyclerView rViewtoprated,rViewupcoming,rViewnowplaying,rViewpopular;
    ItemAdapterTopRated itemAdapterTopRated;
    CardView cardView;
    List<ItemProductTopRated> listtoprated = new ArrayList<>();
    ShimmerFrameLayout shimmertoprated, shimmerupcoming, shimmernowplaying, shimmerpopular;
    private static final String TAG = "MainActivity";

    ItemAdapterUpComing itemAdapterUpComing;
    List<ItemProductUpComing> listupcoming = new ArrayList<>();

    ItemAdapterNowPlaying itemAdapterNowPlaying;
    List<ItemProductNowPlaying> listnowplaying = new ArrayList<>();

    ItemAdapterPopular itemAdapterPopular;
    List<ItemProductPopular> listpopular = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = findViewById(R.id.btnsearch);
        cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
          }
        });

      // Top Rated
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      rViewtoprated = findViewById(R.id.recyclerViewtoprated);
      itemAdapterTopRated = new ItemAdapterTopRated(listtoprated);
      rViewtoprated.setAdapter(itemAdapterTopRated);
      rViewtoprated.setLayoutManager(linearLayoutManager);
      shimmertoprated = findViewById(R.id.shimmerFrameLayouttoprated);

      String urltoprated = "https://api.themoviedb.org/3/movie/top_rated?api_key=284f4b913d3e6938b6a6f860ce961ada&language=en-US&page=1";
      final StringRequest stringRequest = new StringRequest(Request.Method.POST, urltoprated,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try {
              JSONObject jsonObject = new JSONObject(response);
              String results = jsonObject.getString("results");
              JSONArray jArray1 = new JSONArray(results);
                for (int i = 0; i < jArray1.length(); i++) {
                  JSONObject jsonObject1 = (JSONObject) jArray1.get(i);
                  listtoprated.add(new ItemProductTopRated(jsonObject1.getString("id"), jsonObject1.getString("title"), jsonObject1.getString("poster_path")));
                  itemAdapterTopRated.notifyDataSetChanged();
                  shimmertoprated.setVisibility(View.GONE);
                  rViewtoprated.setVisibility(View.VISIBLE);
                }
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

      //upcoming
      LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      rViewupcoming = findViewById(R.id.recyclerViewupcoming);
      itemAdapterUpComing = new ItemAdapterUpComing(listupcoming);
      rViewupcoming.setAdapter(itemAdapterUpComing);
      rViewupcoming.setLayoutManager(linearLayoutManager2);
      shimmerupcoming = findViewById(R.id.shimmerFrameLayoutupcoming);

      String urlupcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key=284f4b913d3e6938b6a6f860ce961ada&language=en-US&page=1";
      final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, urlupcoming,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try {
              JSONObject jsonObject = new JSONObject(response);
              String results = jsonObject.getString("results");
              JSONArray jArray1 = new JSONArray(results);
              for (int i = 0; i < jArray1.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jArray1.get(i);
                listupcoming.add(new ItemProductUpComing(jsonObject1.getString("id"), jsonObject1.getString("title"), jsonObject1.getString("poster_path")));
                itemAdapterUpComing.notifyDataSetChanged();
                shimmerupcoming.setVisibility(View.GONE);
                rViewupcoming.setVisibility(View.VISIBLE);
              }
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
      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);

      //Now playing
      LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      rViewnowplaying = findViewById(R.id.recyclerViewnowplaying);
      itemAdapterNowPlaying = new ItemAdapterNowPlaying(listnowplaying);
      rViewnowplaying.setAdapter(itemAdapterNowPlaying);
      rViewnowplaying.setLayoutManager(linearLayoutManager3);
      shimmernowplaying = findViewById(R.id.shimmerFrameLayoutnowplaying);

      String urlnowplaying = "https://api.themoviedb.org/3/movie/now_playing?api_key=284f4b913d3e6938b6a6f860ce961ada&language=en-US&page=1";
      final StringRequest stringRequest3 = new StringRequest(Request.Method.POST, urlnowplaying,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try {
              JSONObject jsonObject = new JSONObject(response);
              String results = jsonObject.getString("results");
              JSONArray jArray1 = new JSONArray(results);
              for (int i = 0; i < jArray1.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jArray1.get(i);
                listnowplaying.add(new ItemProductNowPlaying(jsonObject1.getString("id"), jsonObject1.getString("title"), jsonObject1.getString("poster_path")));
                itemAdapterNowPlaying.notifyDataSetChanged();
                shimmernowplaying.setVisibility(View.GONE);
                rViewnowplaying.setVisibility(View.VISIBLE);
              }
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
      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);

      //Popular
      LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      rViewpopular = findViewById(R.id.recyclerViewpopular);
      itemAdapterPopular = new ItemAdapterPopular(listpopular);
      rViewpopular.setAdapter(itemAdapterNowPlaying);
      rViewpopular.setLayoutManager(linearLayoutManager4);
      shimmerpopular = findViewById(R.id.shimmerFrameLayoutpopular);

      String urlpopular = "https://api.themoviedb.org/3/movie/popular?api_key=284f4b913d3e6938b6a6f860ce961ada&language=en-US&page=1";
      final StringRequest stringRequest4 = new StringRequest(Request.Method.POST, urlpopular,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try {
              JSONObject jsonObject = new JSONObject(response);
              String results = jsonObject.getString("results");
              JSONArray jArray1 = new JSONArray(results);
              for (int i = 0; i < jArray1.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jArray1.get(i);
                listpopular.add(new ItemProductPopular(jsonObject1.getString("id"), jsonObject1.getString("title"), jsonObject1.getString("poster_path")));
                itemAdapterPopular.notifyDataSetChanged();
                shimmerpopular.setVisibility(View.GONE);
                rViewpopular.setVisibility(View.VISIBLE);
              }
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
      MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest4);
    }
}
