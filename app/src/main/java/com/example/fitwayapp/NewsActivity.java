package com.example.fitwayapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitwayapp.Adapter.NewsAdapter;
import com.example.fitwayapp.Model.News;
import com.example.fitwayapp.Model.Result;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class NewsActivity extends AppCompatActivity {
    RecyclerView newsRecyclerView;
    TextView txtQuery;
    String searchString = "India"; //default search for
    ShimmerFrameLayout shimmerLayout;
    LinearLayout linearNoDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        shimmerLayout = findViewById(R.id.shimmerLayout);
        txtQuery = findViewById(R.id.txtQuery);
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearNoDataLayout = findViewById(R.id.linearNoDataLayout);

        Intent intent = getIntent();
        if (intent.getStringExtra("searchQuery") != null) {
            searchString = intent.getStringExtra("searchQuery");
        }
        String searchMessage = "Searching : " + searchString;
        txtQuery.setText(searchMessage);

        getNews(searchString);
    }

    private void getNews(String searchString) {
        String apiKey = Constants.NEWS_API_KEY;
        String newsURL = Constants.NEWS_URL;

        String requestURL = newsURL + apiKey + "&q=" + searchString;


        StringRequest stringRequest = new StringRequest(requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);


                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                News news = gson.fromJson(response, News.class);
                Log.d("Status", news.getStatus());
                String status = news.getStatus();

                shimmerLayout.setVisibility(View.GONE);

                if (status.equalsIgnoreCase("success")) {
                    List<Result> newsList = news.getResults();
                    if (newsList.size() > 0) {
                        NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, newsList);
                        newsRecyclerView.setAdapter(newsAdapter);
                        linearNoDataLayout.setVisibility(View.GONE);
                    } else {
                        linearNoDataLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", error.getMessage().toString());

                linearNoDataLayout.setVisibility(View.VISIBLE);
                shimmerLayout.setVisibility(View.GONE);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}