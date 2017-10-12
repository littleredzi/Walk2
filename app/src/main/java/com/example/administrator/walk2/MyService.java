package com.example.administrator.walk2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import static android.R.id.edit;

public class MyService extends Service {
    private StepCount mStepCount;
    private SensorManager sensorManager;
    int num;
    public MyService() {
        mStepCount = new StepCount();
        //当前走的步子
        mStepCount.setSteps(num);
        //获得传感器
        sensorManager = (SensorManager) App.myContext.getSystemService(SENSOR_SERVICE);
        //获取加速传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAvailable = sensorManager.registerListener(mStepCount.getStepDetector(), sensor, SensorManager.SENSOR_DELAY_UI);
        mStepCount.initListener(new StepValuePassListener() {
            @Override
            public void stepChanged(int steps) {
                num = steps;
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("number",num);
                message.setData(bundle);
                MainActivity.handler.sendMessage(message);
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Music();
    }

    class Music extends Binder {


    }
}
