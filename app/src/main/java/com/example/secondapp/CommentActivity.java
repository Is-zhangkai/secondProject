package com.example.secondapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.secondapp.Adapter.CommentAdapter;
import com.example.secondapp.Adapter.NewsListAdapter;
import com.example.secondapp.Data.Comments;
import com.example.secondapp.Data.News;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Comments> comments = new ArrayList<>();
    private TextView textView0;
    private String long_data;
    private int longNumber;
    private int shortNumber;
    private CommentAdapter commentAdapter;
    private int id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conmmit_layout);
        Button buttonBack = findViewById(R.id.button_back);
        SmartRefreshLayout refreshLayout = findViewById(R.id.comment_srl);

        recyclerView = this.findViewById(R.id.comment_recyclerview);
        textView0 = findViewById(R.id.tv_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));


        //接收数据
        Intent intent_get = getIntent();
        id = intent_get.getIntExtra("id", 0);
        long_data = intent_get.getStringExtra("data");
        longNumber = intent_get.getIntExtra("longNumber", 0);
        shortNumber = intent_get.getIntExtra("shortNumber", 0);


        GetComments();
        textView0.setText(longNumber + shortNumber + "条评论");


        //返回按钮
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                GetComments();
                textView0.setText(longNumber + shortNumber + "条评论");
                refreshLayout.finishRefresh();
            }
        });


    }
//////////////////////////////连接网络获取json

    //显示评论
    public void Json(String data, List<Comments> comments, String number) throws JSONException {

        JSONObject jsonObject1 = new JSONObject(data);
        Comments comments0 = new Comments();
        comments0.setNumber(number);
        comments.add(comments0);
        JSONArray jsonArray = jsonObject1.getJSONArray("comments");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            Comments comments1 = new Comments();
            comments1.setAuthor(jsonObject2.getString("author"));
            comments1.setContent(jsonObject2.getString("content"));
            comments1.setLikes(jsonObject2.getInt("likes"));
            comments1.setAvatar(jsonObject2.getString("avatar"));
            comments1.setTime(ToDate(jsonObject2.getLong("time")));
            comments.add(comments1);
        }
    }


    public void GetComments() {
        //如果有长评
        if (longNumber != 0 && long_data != null) {

            try {
                Json(long_data, comments, longNumber + "条长评");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        if (shortNumber != 0) {
            HTTPTools.getData("https://news-at.zhihu.com/api/3/story/" + id + "/short-comments", new HTTPTools.HTTPBackListener() {

                @Override
                public void onSuccess(String data, int code) {
                    String date = data;
                    try {
                        Json(date, comments, shortNumber + "条短评");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                commentAdapter = new CommentAdapter(CommentActivity.this, comments);
                                recyclerView.setAdapter(commentAdapter);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog response = new AlertDialog.Builder(CommentActivity.this)
                                    .setTitle("错误")
                                    .setMessage("未连接网络")
                                    .create();
                            response.show();
                        }
                    });

                }
            });
        }

    }


    //时间戳
    public static String ToDate(long s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }
}

