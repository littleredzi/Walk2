package com.example.administrator.walk2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.walk2.view.CirclePercentView;

public class MainActivity extends AppCompatActivity {


    private static TextView textView;
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int number = data.getInt("number");
            textView.setText(number + "");
            if (number<102){
                myview.setPercent(number);
            }else{
            }

        }
    };

    MyService myService;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myService = new MyService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private static CirclePercentView myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.myContext = this;
        initView();
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("正在计算....");
        myview = (CirclePercentView) findViewById(R.id.myview);
    }
}
