package com.example.secondapp.Adapter;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.secondapp.R;

public class PagerHolder extends RecyclerView.ViewHolder {

    public ViewPager2 viewPager2;
    public RelativeLayout relativeLayout;
    public PagerHolder(@NonNull View itemView) {
        super(itemView);
        viewPager2=itemView.findViewById(R.id.viewPager);
        relativeLayout=itemView.findViewById(R.id.r_layout);

    }
}
