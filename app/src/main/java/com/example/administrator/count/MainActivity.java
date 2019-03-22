package com.example.administrator.count;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.count.Bean.AcountList;
import com.example.administrator.count.Thread.GetCZInfoThread;
import com.example.administrator.count.Thread.GetChongThread;
import com.example.administrator.count.Thread.GetUpdateThread;
import com.example.administrator.count.tools.DbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private Handler handler;

    private TextView yue;
    private Spinner spinner;
    private String select;
    private EditText czje;
    private Button cx,cz,czjl;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private AcountList.Acount acount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        dbHelper=new DbHelper(MainActivity.this);
        db=dbHelper.getWritableDatabase();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        acount = (AcountList.Acount)msg.obj;
                        yue.setText(acount.getMoney());
                        break;
                    case 2:
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        try {
                            if (jsonObject.getString("result").equals("t")){
                                Toast.makeText(MainActivity.this,"充值成功！",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this,"充值失败！",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        JSONObject jsonObject1 = (JSONObject)msg.obj;
                        try{
                            yue.setText(jsonObject1.getString("result"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = spinner.getSelectedItem().toString();
                new GetCZInfoThread(handler,select).start();
            }
        });
        cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = spinner.getSelectedItem().toString();
                new GetCZInfoThread(handler,select).start();
                new GetChongThread(handler,acount,czje.getText().toString()).start();
                new GetUpdateThread(handler,select,
                        String.valueOf(Integer.parseInt(yue.getText().toString())+Integer.parseInt(czje.getText().toString()))).start();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                dbHelper.insert(select,czje.getText().toString(),"user1",simpleDateFormat.format(date));
            }
        });
        czjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(MainActivity.this).inflate(R.layout.acountdialog,null);
                ListView lv=(ListView)view.findViewById(R.id.lv);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("账户充值记录");
                builder.setView(view);
                List<Map<String,Object>> list=new ArrayList<>();
                Cursor cursor=db.rawQuery("select * from czjl",null);
                while(cursor.moveToNext()){
                    Map<String, Object> map=new HashMap<>();
                    map.put("id",cursor.getInt(cursor.getColumnIndex("_id")));
                    map.put("carid",cursor.getString(cursor.getColumnIndex("car_id")));
                    map.put("money",cursor.getString(cursor.getColumnIndex("money")));
                    map.put("time",cursor.getString(cursor.getColumnIndex("time")));
                    list.add(map);
                }
                lv.setAdapter(new SimpleAdapter(MainActivity.this,list,R.layout.dialog,new String[]{"id","carid","money","time"},new int[]{R.id.id,R.id.carid,R.id.money,R.id.time}));
                builder.create();
                builder.show();
            }
        });
    }

    private void initView() {
        yue = (TextView)findViewById(R.id.yue);
        spinner = (Spinner)findViewById(R.id.spinner);
        czje = (EditText)findViewById(R.id.czje);
        cx = (Button)findViewById(R.id.cx);
        cz = (Button)findViewById(R.id.cz);
        czjl = (Button)findViewById(R.id.czjl);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
