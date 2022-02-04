package com.example.fitwayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imgQrCode;
    ImageView imgSearchNews;
    RelativeLayout searchLayout;
    EditText edtSearch;
    ImageView imgCloseIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSearchNews = findViewById(R.id.imgSearchNews);
        searchLayout = findViewById(R.id.searchLayout);
        edtSearch = findViewById(R.id.edtSearch);

        imgCloseIcon = findViewById(R.id.imgCloseIcon);

        imgQrCode = findViewById(R.id.imgQrCode);
        imgQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        imgSearchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.setText("");
                if (searchLayout.getVisibility() == View.VISIBLE) {
                    searchLayout.setVisibility(View.GONE);
                } else {
                    searchLayout.setVisibility(View.VISIBLE);
                }
                /*Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);*/
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    intent.putExtra("searchQuery", edtSearch.getText().toString());
                    startActivity(intent);

                    edtSearch.setText("");
                    searchLayout.setVisibility(View.GONE);

                    return true;
                }

                return false;
            }
        });

        imgCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.setText("");
            }
        });

    }
}