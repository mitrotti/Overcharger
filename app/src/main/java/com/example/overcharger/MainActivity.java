// (C) 2021 Angelo Mitrotti

package com.example.overcharger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
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


        SeekBar desired_max = findViewById(R.id.desired_max);
        desired_max.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // https://stackoverflow.com/questions/7329166/changing-step-values-in-seekbar
                progress = progress / 1;
                progress = progress * 10;
                if(progress%10==0) {
                    set_max(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        desired_max.setProgress(percent/10);

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
}