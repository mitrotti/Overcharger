// (C) 2021 Angelo Mitrotti

package com.example.overcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        receiver = new BatteryBroadcastReceiver();
        registerReceiver(receiver, intentFilter);

        percent_storage = this.getPreferences(Context.MODE_PRIVATE);
        percent = percent_storage.getInt(getString(R.string.percent_key), 80);

        TextView percent_label = findViewById(R.id.percent_label);
        percent_label.setText(""+this.percent+"%");
        getSupportActionBar().hide();

        thunder = findViewById(R.id.thunder_svg);
        set_thunder(percent-10);
        thunder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                set_thunder(percent);
                return false;
            }
        });

        executorService = Executors.newFixedThreadPool(2);
        }

        protected void set_max(int percent) {
            this.percent = percent;
            SharedPreferences.Editor editor = percent_storage.edit();
            editor.putInt(getString(R.string.percent_key), percent);
            editor.apply();

            TextView percent_label = findViewById(R.id.percent_label);
            percent_label.setText(""+this.percent+"%");
        }

        public static int get_max() {
            return percent;
        }

        private static int percent;
        SharedPreferences percent_storage;

        private ExecutorService executorService;
        private BatteryBroadcastReceiver receiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.stop_tts();
    }

    private void set_thunder(int percent) {
        switch(percent) {
            case 0:
                thunder.setImageResource(R.drawable.ic_view_10);
                set_max(percent+10);
                break;
            case 10:
                thunder.setImageResource(R.drawable.ic_view_20);
                set_max(percent+10);
                break;
            case 20:
                thunder.setImageResource(R.drawable.ic_view_30);
                set_max(percent+10);
                break;
            case 30:
                thunder.setImageResource(R.drawable.ic_view_40);
                set_max(percent+10);
                break;
            case 40:
                thunder.setImageResource(R.drawable.ic_view_50);
                set_max(percent+10);
                break;
            case 50:
                thunder.setImageResource(R.drawable.ic_view_60);
                set_max(percent+10);
                break;
            case 60:
                thunder.setImageResource(R.drawable.ic_view_70);
                set_max(percent+10);
                break;
            case 70:
                thunder.setImageResource(R.drawable.ic_view_80);
                set_max(percent+10);
                break;
            case 80:
                thunder.setImageResource(R.drawable.ic_view_90);
                set_max(percent+10);
                break;
            case 90:
                thunder.setImageResource(R.drawable.ic_view_100);
                set_max(percent+10);
                break;
            default:
                thunder.setImageResource(R.drawable.ic_view_0);
                set_max(0);

        }
    }

    private ImageView thunder;
}