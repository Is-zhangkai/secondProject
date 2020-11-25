package com.example.secondapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.secondapp.Adapter.NewsListAdapter;
import com.example.secondapp.Adapter.ViewPagerAdapter;
import com.example.secondapp.Data.News;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private NewsListAdapter adapter;
    private int page=0;
    private int dateBefore=0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        viewPager=findViewById(R.id.viewPager);
        recyclerView=this.findViewById(R.id.recyclerview);
        refreshLayout=this.findViewById(R.id.srl);
        Button button_user=findViewById(R.id.btn_user);
        final TextView date=findViewById(R.id.date);
        TextView month=findViewById(R.id.month);
        TextView topword=findViewById(R.id.topWord);

//顶部提醒及日期
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        String word;
        if (hour>=22){word="早点休息！";}
        else if (hour>17){word="晚上好！";}
        else {word="知乎日报";}
        topword.setText(word);
        date.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");
        month.setText(Month(calendar.get(Calendar.MONTH)+1));



//加载数据
        final List<News> news0=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getNewsData1("https://news-at.zhihu.com/api/3/stories/latest",news0);



//刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                news0.clear();
                dateBefore=0;page=0;
                getNewsData1("https://news-at.zhihu.com/api/3/stories/latest",news0);
                refreshLayout.finishRefresh();


            }
        });

//上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                List<News> news=new ArrayList<>();
                getNewsData2("https://news-at.zhihu.com/api/3/news/before/"+getOldDate(dateBefore),news);
                dateBefore=dateBefore-1;
                refreshLayout.finishLoadMore();
            }
        });


//个人中心按钮
        button_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

    }
////////////////////////////////////////////////////////////////////
    //获取数据，解析Json
//轮播图+今日新闻
    public void getNewsData1(String path, final List<News> news){

        HTTPTools.getData(path, new HTTPTools.HTTPBackListener() {
            @Override
            public void onSuccess(String data, int code) {
                try {
                    page++;

//解析轮播图数据
                    JSONObject jsonObject1=new JSONObject(data);

                        JSONArray jsonArray0=jsonObject1.getJSONArray("top_stories");
                        List<News> list=new ArrayList<>();
                        for (int i=0;i<jsonArray0.length();i++){
                        JSONObject jsonObject=jsonArray0.getJSONObject(i);
                        News news1=new News();
                        news1.setTitle(jsonObject.getString("title"));
                        news1.setHint(jsonObject.getString("hint"));
                        news1.setUrl(jsonObject.getString("url"));
                        news1.setId(jsonObject.getInt("id"));
                        news1.setImages(jsonObject.getString("image"));
                        list.add(news1);}
                        News news0=new News();
                        news0.setList(list);
                        news.add(news0);
//今日新闻
                    JSONArray jsonArray=jsonObject1.getJSONArray("stories");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2=jsonArray.getJSONObject(i);
                        News news1=new News();
                        news1.setTitle(jsonObject2.getString("title"));
                        news1.setHint(jsonObject2.getString("hint"));
                        news1.setUrl(jsonObject2.getString("url"));
                        news1.setId(jsonObject2.getInt("id"));
                        JSONArray jsonArray1=jsonObject2.getJSONArray("images");
                        news1.setImages(jsonArray1.getString(0));

                        news.add(news1);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adapter=new NewsListAdapter(MainActivity.this,news);
                            recyclerView.setAdapter(adapter);

                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog response=new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("错误")
                                    .setMessage("未连接网络")
                                    .create();
                            response.show();
                        }
                    });
                }

            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog response=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("错误")
                                .setMessage("未连接网络")
                                .create();
                        response.show();
                    }
                });

            }
        });
    }


//下拉加载的数据
    public void getNewsData2(String path, final List<News> news){

        HTTPTools.getData(path, new HTTPTools.HTTPBackListener() {
            @Override
            public void onSuccess(String data, int code) {
                try {
                    page++;
//获取显示时间
                    JSONObject jsonObject1=new JSONObject(data);
                        News news0=new News();
                        String d=jsonObject1.getString("date");
                        news0.setDate(d.replaceAll("\\d{4}(\\d{2})(\\d{2})","$1月$2日"));
                        news.add(news0);


                    JSONArray jsonArray=jsonObject1.getJSONArray("stories");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2=jsonArray.getJSONObject(i);
                        News news1=new News();
                        news1.setTitle(jsonObject2.getString("title"));
                        news1.setHint(jsonObject2.getString("hint"));
                        news1.setUrl(jsonObject2.getString("url"));
                        news1.setId(jsonObject2.getInt("id"));
                        JSONArray jsonArray1=jsonObject2.getJSONArray("images");
                        news1.setImages(jsonArray1.getString(0));

                        news.add(news1);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addData(news);
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog response=new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("错误")
                                    .setMessage("未连接网络")
                                    .create();
                            response.show();
                        }
                    });
                }

            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog response=new AlertDialog.Builder(MainActivity.this)
                                .setTitle("错误")
                                .setMessage("未连接网络")
                                .create();
                        response.show();
                    }
                });

            }
        });
    }




//获取n天后的时间
    public static String getOldDate(int distanceDay) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert endDate != null;
        return dft.format(endDate);
    }



    //数字月份转汉字
    public String Month(int month){
        String mon="月";
        if (month==1){mon="一月";}if (month==2){mon="二月";}
        if (month==3){mon="三月";}if (month==4){mon="四月";}
        if (month==5){mon="五月";}if (month==6){mon="六月";}
        if (month==7){mon="七月";}if (month==8){mon="八月";}
        if (month==9){mon="九月";}if (month==10){mon="十月";}
        if (month==11){mon="十一月";}if (month==12){mon="十二月";}
        return (mon);
    }
}