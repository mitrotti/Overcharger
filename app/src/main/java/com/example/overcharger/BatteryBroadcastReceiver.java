// (C) 2021 Angelo Mitrotti

package com.example.overcharger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // https://developer.android.com/training/monitoring-device-state/battery-monitoring#java
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float percent = level / (float)scale * 100;

        executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        };

        listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(percent%10 == 0 && status == TextToSpeech.SUCCESS){
                    speaker.setLanguage(Locale.US);
                    speaker.speak("The battery is at "+(int)percent+" percent",TextToSpeech.QUEUE_FLUSH,null, "battery_percent"+Math.random());
                }
                if(percent >= MainActivity.get_max() && status == TextToSpeech.SUCCESS) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            for(int i=0; i<10; i++) {
                                try {
                                    TimeUnit.MINUTES.sleep(1);
                                } catch (Exception e) {
                                    break;
                                }
                                speaker.setLanguage(Locale.US);
                                speaker.speak("The battery is at "+(int)percent+" percent",TextToSpeech.QUEUE_FLUSH,null, "battery_percent"+Math.random());
                            }
                        }
                    });
                }
            }
        };
        speaker = new TextToSpeech(context, listener);
        };

    public void stop_tts() {
        this.speaker.shutdown();
    }

    private TextToSpeech speaker;
    public TextToSpeech.OnInitListener listener;
    private Executor executor;
}
