package com.example.administrator.count.Thread;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.count.Bean.AcountList;
import com.example.administrator.count.tools.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetChongThread extends Thread{
    private String url="http://118.25.27.11:8080/trafic/servlet/AddChargeAction";
    private Handler handler;
    private HashMap<String,String> map = new HashMap<>();
    private AcountList.Acount acount;
    private String money;

    public GetChongThread(Handler handler, AcountList.Acount acount,String money) {
        this.handler = handler;
        this.acount = acount;
        this.money = money;
    }

    @Override
    public void run() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日 HH时mm分ss秒");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        map.put("userid","admin");
        map.put("carid",acount.getCar_id());
        map.put("carnum",acount.getCarnum());
        map.put("money",money);
        map.put("time",time);
        try {
            HttpUtils.postmap(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Message message = Message.obtain();
                        message.what=2;
                        message.obj=new JSONObject(response.body().string());
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
