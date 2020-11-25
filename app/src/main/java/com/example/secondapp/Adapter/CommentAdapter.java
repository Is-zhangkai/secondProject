package com.example.secondapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.secondapp.Data.Comments;
import com.example.secondapp.Data.News;
import com.example.secondapp.R;

import java.util.List;
import java.util.Objects;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_NUMBER =1 ;
    private static final int ITEM_COMMENTS = 2;
    private Context context;
    private List<Comments> comments;
    public CommentAdapter(Context context, List<Comments> comments) {
        this.context=context;
        this.comments=comments;
    }

    public void addComments(List<Comments> dataUpdate){
        if (dataUpdate != null) {

            comments.addAll(dataUpdate);
            notifyItemChanged(comments.size() - dataUpdate.size(), dataUpdate.size());
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ViewHolderComments){
            ((ViewHolderComments)holder).writer.setText(Objects.requireNonNull(comments.get(i).getAuthor()));
            ((ViewHolderComments)holder).comment.setText(Objects.requireNonNull(comments.get(i).getContent()));
        Glide.with(context).load(Objects.requireNonNull(comments.get(i).getAvatar())).into(((ViewHolderComments)holder).image);
            ((ViewHolderComments)holder).date.setText(comments.get(i).getTime() +"");
            ((ViewHolderComments)holder).number.setText(comments.get(i).getLikes() +"");
        final boolean[] flag = {true};

        //点赞按钮
            ((ViewHolderComments)holder).like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[0]){
                    flag[0] =false;
                    ((ViewHolderComments)holder).number.setText(comments.get(i).getLikes()+1 +"");
                    ((ViewHolderComments)holder).like.setBackgroundResource(R.drawable.good_t);

                }
                else { flag[0] =true;
                    ((ViewHolderComments)holder).number.setText(comments.get(i).getLikes()+"");
                    ((ViewHolderComments)holder).like.setBackgroundResource(R.drawable.good_f);}
            }
        });}else if (holder instanceof ViewHolderNumber){
            ((ViewHolderNumber)holder).number.setText(Objects.requireNonNull(comments.get(i).getNumber()));
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
        if (i==ITEM_COMMENTS){
            view= LayoutInflater.from(context).inflate(R.layout.item_comment,viewGroup,false);
            holder=new ViewHolderComments(view);}
        else if (i==ITEM_NUMBER){
            view= LayoutInflater.from(context).inflate(R.layout.item_comment_number,viewGroup,false);
            holder=new ViewHolderNumber(view);}
        assert holder != null;
        return holder;
    }



    @Override
    public int getItemViewType(int i) {
        if (comments.get(i).getNumber()!=null) {
            return ITEM_NUMBER;
        } else if (comments.get(i).getNumber()==null ) {
            return ITEM_COMMENTS;
        }
        return super.getItemViewType(i);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolderComments extends RecyclerView.ViewHolder {
        TextView title;
        TextView writer;
        TextView date;
        TextView comment;
        TextView comment_other;
        TextView number;
        ImageView image;
        Button like;
        public ViewHolderComments(@NonNull View itemView) {
            super(itemView);
            writer=itemView.findViewById(R.id.comment_name);
            date=itemView.findViewById(R.id.comment_date);
            comment=itemView.findViewById(R.id.comment_main);
            comment_other=itemView.findViewById(R.id.comment_extra);
            number=itemView.findViewById(R.id.comment_nice_number);
            image=itemView.findViewById(R.id.comment_photo);
            title=itemView.findViewById(R.id.title);
            like=itemView.findViewById(R.id.comment_nice);
        }
    }

    public static class ViewHolderNumber extends RecyclerView.ViewHolder {
        TextView number;
        public ViewHolderNumber(@NonNull View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.comment_number);
        }
    }

}
