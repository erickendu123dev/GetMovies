package com.erickdev.getmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.erickdev.getmovies.recylerview.ItemAdapterSearch;
import com.erickdev.getmovies.recylerview.ItemAdapterTopRated;
import com.erickdev.getmovies.recylerview.ItemProductSearch;
import com.erickdev.getmovies.recylerview.ItemProductTopRated;
import com.erickdev.getmovies.recylerview.ItemProductUpComing;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    TextInputEditText textInputEditText;
    ImageButton ibback;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    String edit;

    ItemAdapterSearch itemAdapterSearch;
    List<ItemProductSearch> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
          edit = "Spider";
          list.clear();
          searching();
        ibback = findViewById(R.id.ib_back);
        ibback.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            finish();
          }
        });

      // Top Rated
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      recyclerView = findViewById(R.id.recyclerView);
      itemAdapterSearch = new ItemAdapterSearch(list);
      recyclerView.setAdapter(itemAdapterSearch);
      recyclerView.setLayoutManager(linearLayoutManager);
      shimmerFrameLayout = findViewById(R.id.shimmer);

      textInputEditText = findViewById(R.id.searchView);
      textInputEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            list.clear();
            shimmerFrameLayout.setVisibility(View.VISIBLE);

            if (charSequence.toString().isEmpty()){
              edit = "Spider-Man: No Way Home";
            }else {
              edit = charSequence.toString();
            }
            recyclerView.setVisibility(View.GONE);
            searching();
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
      });

    }

    private void searching(){
      String urltoprated = "https://api.themoviedb.org/3/search/movie?api_key=284f4b913d3e6938b6a6f860ce961ada&language=en-US&page=1&include_adult=false&query=" + edit;
      final StringRequest stringRequest = new StringRequest(Request.Method.GET, urltoprated,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try {
              JSONObject jsonObject = new JSONObject(response);
              String results = jsonObject.getString("results");
              JSONArray jArray1 = new JSONArray(results);
              for (int i = 0; i < jArray1.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jArray1.get(i);
                list.add(new ItemProductSearch(jsonObject1.getString("id"), jsonObject1.getString("title"), jsonObject1.getString("poster_path")));
                itemAdapterSearch.notifyDataSetChanged();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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

    }
}
