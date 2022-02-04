package com.example.fitwayapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitwayapp.R;
import com.example.fitwayapp.Model.Result;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<Result> resultList;

    public NewsAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_news_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Result result = resultList.get(position);
        String title = result.getTitle();
        String description = result.getDescription();
        String imageUrl = String.valueOf(result.getImageUrl());

        holder.txtNewsHeadline.setText(title);
        holder.txtNewsDescription.setText(description);
//        holder.txtNewsHeadline.setText(title);


        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imgNews);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

//                    Snackbar.make(view, "Clicked", Snackbar.LENGTH_SHORT).show();
                    String url = result.getLink();

                    int colorInt = Color.parseColor("#FF0000"); //red
                    CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(colorInt)
                            .build();

                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setUrlBarHidingEnabled(true);
                    builder.setShowTitle(true);
                    builder.setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM);

                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(url));

                }catch (Exception e){
                    Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView txtNewsHeadline;
        TextView txtNewsDescription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNews = itemView.findViewById(R.id.imgNews);
            txtNewsHeadline = itemView.findViewById(R.id.txtNewsHeadline);
            txtNewsDescription = itemView.findViewById(R.id.txtNewsDescription);
        }
    }

}
