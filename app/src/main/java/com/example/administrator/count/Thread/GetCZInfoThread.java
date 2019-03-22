package com.example.administrator.count.Thread;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.count.Bean.AcountList;
import com.example.administrator.count.tools.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class GetCZInfoThread extends Thread{
    private String url="http://118.25.27.11:8080/trafic/servlet/SelectAcountAction";
    private Handler handler;
    Map<String,String> map = new HashMap<>();
    private String carid;

    public GetCZInfoThread(Handler handler,String carid) {
        this.handler = handler;
        this.carid = carid;
    }

    @Override
    public void run() {
        super.run();
        map.put("carid",carid);
        try {
            HttpUtils.postmap(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = Message.obtain();
                    message.what=1;
                    message.obj=new Gson().fromJson(response.body().string(),AcountList.Acount.class);
                    handler.sendMessage(message);
                }
            },map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
