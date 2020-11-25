package com.example.secondapp.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.secondapp.Data.News;
import com.example.secondapp.R;
import com.example.secondapp.WebViewActivity;

import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

        private List<News> news;
        private Context context;
        public ViewPagerAdapter(Context context, List<News> news) {
            this.context=context;
            this.news=news;
           // ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager, parent, false);
        return  new ViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "CheckResult"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        Glide.with(context).load(Objects.requireNonNull(news.get(i).getImages())).into(holder.imageView);
        holder.title.setText(news.get(i).getTitle());
        holder.hint.setText(news.get(i).getHint());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra("id", news.get(i).getId());
                v.getContext().startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return news.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView hint;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.view_image);
                title=itemView.findViewById(R.id.viewPager_title);
                hint=itemView.findViewById(R.id.viewPager_hint);
            }
        }
}

