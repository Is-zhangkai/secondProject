package com.example.secondapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class WebViewActivity extends AppCompatActivity {
    private int commentsNumber;
    private int popularity ;
    private int long_comments;
    private int short_comments;
    private String long_data;
    private boolean if_like=true;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        WebView webView = findViewById(R.id.webview);
        Button button_back = findViewById(R.id.web_back);
        Button button_comment = findViewById(R.id.web_comment);
        final Button button_like = findViewById(R.id.btn_good);
        final TextView textView_comment = findViewById(R.id.tv_commentNumber);
        final TextView textView_likes = findViewById(R.id.tv_likes);



        final int id = getIntent().getIntExtra("id", 0);




        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://daily.zhihu.com/story/" + id);


        HTTPTools.getData("https://news-at.zhihu.com/api/3/story-extra/" + id, new HTTPTools.HTTPBackListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(String data, int code) {
                try {
                    JSONObject jsonObject1 = new JSONObject(data);
                    long_comments= jsonObject1.getInt("long_comments");
                    short_comments=jsonObject1.getInt("short_comments");
                    commentsNumber = jsonObject1.getInt("comments");
                    popularity = jsonObject1.getInt("popularity");

                    if (long_comments != 0) {
                        HTTPTools.getData("https://news-at.zhihu.com/api/3/story/" + id + "/long-comments", new HTTPTools.HTTPBackListener() {
                            @Override
                            public void onSuccess(String data, int code) {
                                long_data = data;
                                long_data = data;
                            }

                            @Override
                            public void onError() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog response = new AlertDialog.Builder(WebViewActivity.this)
                                                .setTitle("错误")
                                                .setMessage("未连接网络")
                                                .create();
                                        response.show();
                                    }
                                });

                            }
                        });
                    }

                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            textView_comment.setText(commentsNumber + "");
                            textView_likes.setText(popularity + "");
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
                        AlertDialog response = new AlertDialog.Builder(WebViewActivity.this)
                                .setTitle("错误")
                                .setMessage("未连接网络")
                                .create();
                        response.show();
                    }
                });

            }
        });





        button_like.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (if_like){
                    if_like=false;
                textView_likes.setText(popularity+1+"");
                button_like.setBackgroundResource(R.drawable.good_t);
            }
                else {
                    if_like=true;
                    textView_likes.setText(popularity +"");
                    button_like.setBackgroundResource(R.drawable.good_f);
                }
            }
        });

        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(WebViewActivity.this, CommentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("longNumber",long_comments);
                intent.putExtra("shortNumber",short_comments);
                intent.putExtra("data",long_data);
                startActivity(intent);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}