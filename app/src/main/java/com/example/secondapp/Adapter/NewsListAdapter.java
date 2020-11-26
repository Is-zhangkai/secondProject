package com.example.secondapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.secondapp.Data.Like;
import com.example.secondapp.Data.News;
import com.example.secondapp.R;
import com.example.secondapp.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_NEWS =1 ;
    private static final int ITEM_DATE =2 ;
    private static final int ITEN_VIEW =3 ;
    private static final int ITEM_ERROR =4;
    private Context context;
    private List<News> news;
    private  PagerHolder pagerHolder;
    private ViewPagerAdapter viewPagerAdapter;



    public NewsListAdapter(Context context, List<News> news) {

        this.context=context;
        this.news=news;
    }



    public void addData(List<News> dataUpdate){
        if (dataUpdate!=null&&dataUpdate.size()>0){
            news.addAll(dataUpdate);
            notifyItemChanged(news.size()-dataUpdate.size(),dataUpdate.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view_news=null;
        RecyclerView.ViewHolder holder = null;
        if (i==ITEM_ERROR){ view_news= LayoutInflater.from(context).inflate(R.layout.item_error,viewGroup,false);
            holder= new ErrorHolder(view_news);}
        if (i==ITEN_VIEW){
            view_news= LayoutInflater.from(context).inflate(R.layout.item_news_viewpager,viewGroup,false);
            holder= new PagerHolder(view_news);
        }
        if (i==ITEM_NEWS){
        view_news= LayoutInflater.from(context).inflate(R.layout.item_newslist,viewGroup,false);
        holder= new ViewHolderNews(view_news);
        }
        if (i==ITEM_DATE){
            view_news= LayoutInflater.from(context).inflate(R.layout.item_newsdate,viewGroup,false);
            holder= new ViewHolderDate(view_news);
        }

        assert holder != null;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ViewHolderNews) {

            Glide.with(context).load(Objects.requireNonNull(news.get(i).getImages())).into(((ViewHolderNews)holder).image);
            ((ViewHolderNews)holder).title.setText(Objects.requireNonNull(news.get(i).getTitle()));
            ((ViewHolderNews)holder).hint.setText(Objects.requireNonNull(news.get(i).getHint()));


//点击事件
              holder.itemView.setTag(i);
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    ((ViewHolderNews)holder).title.setTextColor(R.color.gray_50);
                    ((ViewHolderNews)holder).hint.setTextColor(R.color.gray_50);
                    Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                    intent.putExtra("id", news.get(i).getId());
                    v.getContext().startActivity(intent);

                }
            });
        }
        if (holder instanceof PagerHolder){

            List<News> news0=news.get(i).getList();
            pagerHolder=(PagerHolder)holder;


            viewPagerAdapter = new ViewPagerAdapter(context, news0);

            pagerHolder.viewPager2.setAdapter(viewPagerAdapter);
            viewPagerAdapter.notifyDataSetChanged();
      }
        if (holder instanceof ViewHolderDate){
            ((ViewHolderDate)holder).date.setText(Objects.requireNonNull(news.get(i).getDate())); }
    }


    @Override
    public int getItemViewType(int i) {

       if (news.get(i).getError()!=null){
           return ITEM_ERROR;
       }
        else if (news.get(i).getList()!=null){
            return ITEN_VIEW;
        } else if (news.get(i).getDate()==null) {
            return ITEM_NEWS;
        } else if (news.get(i).getDate()!=null ) {
            return ITEM_DATE;
        }
        return super.getItemViewType(i);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolderNews extends RecyclerView.ViewHolder {
        TextView title;
        TextView hint;
        ImageView image;
        RelativeLayout layout;
        public ViewHolderNews(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            hint=itemView.findViewById(R.id.hint);
            image=itemView.findViewById(R.id.image);
            //layout.setOnClickListener(this);
        }
    }

    public static class ViewHolderDate extends RecyclerView.ViewHolder {
        TextView date;
        public ViewHolderDate(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.news_date);
        }
    }

    public static class ErrorHolder extends RecyclerView.ViewHolder {

        public ErrorHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
