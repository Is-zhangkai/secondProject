package com.example.secondapp;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTools {
    public static void getData(final String path, final HTTPBackListener backListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb=new StringBuilder();
                try {
                    //创建url
                    URL url=new URL(path);

                    Log.d("aaaaaa",url.toString());

                    //获取URLConnection对象
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(4000);
                    connection.setReadTimeout(4000);
                    //connection.setRequestProperty("Content-type","application/x-");
                    connection.setRequestProperty("Content-type","application/x-javascript->json");
                    connection.setRequestMethod("GET");
                    connection.connect();

                    if (connection.getResponseCode() ==HttpURLConnection.HTTP_OK){
                        InputStream inputStream=connection.getInputStream();
                        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                        String temp;
                        while ((temp=bufferedReader.readLine())!=null){
                            sb.append(temp);
                        }
                        bufferedReader.close();
                        backListener.onSuccess(sb.toString(),connection.getResponseCode());
                    }else {
                        backListener.onError();
                    }
                    connection.disconnect();
                    //Log.i("qqin", sb.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    backListener.onError();
                }
            }
        }).start();


    }
    public interface HTTPBackListener{
        void onSuccess(String data,int code);
        void onError();
    }
}
