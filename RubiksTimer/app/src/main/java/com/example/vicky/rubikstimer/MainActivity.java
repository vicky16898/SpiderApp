package com.example.vicky.rubikstimer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {





    SoundPool beepsound;
    int SoundId;


    public TextView cubertimer;
    public int onetime = 0;
    public TextView inspectiontimer;
    long MillisecondTime,StartTime,TimeBuff,UpdateTime = 0L;
    int Seconds,MilliSeconds,Minutes;
    public boolean flag=true;
    public int interrupt = 0;
    public Handler handler;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            MillisecondTime=SystemClock.uptimeMillis() - StartTime;
            UpdateTime= TimeBuff+MillisecondTime;
            Seconds=(int)(UpdateTime/1000);
            Minutes=Seconds/60;
            Seconds=Seconds%60;
            MilliSeconds=(int)(UpdateTime%1000);

            cubertimer.setText(String.valueOf(Minutes)+":"+String.format("%02d",Seconds)+":"+String.format("%03d",MilliSeconds));
            handler.postDelayed(this,0);



        }
    };

    public Runnable inspection =new Runnable() {
        @Override
        public void run() {

            MillisecondTime=SystemClock.uptimeMillis() - StartTime;
            UpdateTime= TimeBuff+MillisecondTime;
            Seconds=(int)(UpdateTime/1000);
            Seconds=Seconds%60;
            MilliSeconds=(int)(UpdateTime%1000);

            Log.d("seconds","value :"+Integer.toString(Seconds));
            Log.d("milliseconds","value :"+Integer.toString(MilliSeconds));



            if((Seconds==12)&&((MilliSeconds>=0)&&(MilliSeconds<=16)))
            {

                beepsound.play(SoundId,1,1,0,0,1);

            }


            if((Seconds<15))

            {
                Seconds = 14 - Seconds;
                MilliSeconds = 1000 - MilliSeconds;
                inspectiontimer.setText(String.format("%02d",Seconds) + ":" + String.format("%03d",MilliSeconds));



                handler.postDelayed(this, 0);
                interrupt=1;

            }
            else {
                inspectiontimer.setText("00:00");

                StartTime = SystemClock.uptimeMillis();
                flag = false;
                handler.postDelayed(runnable, 0);
                interrupt=0;


            }



        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        beepsound = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
        SoundId = beepsound.load(this,R.raw.beep,1);



        final LinearLayout mainlayout=(LinearLayout)findViewById(R.id.mainlayout);
        inspectiontimer=(TextView)findViewById(R.id.inspectiontimer);
        cubertimer=(TextView)findViewById(R.id.cubertimer);
        handler=new Handler();

        mainlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //pausing cubetimer;
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);

                //pausing inspectiontimer;
                handler.removeCallbacks(inspection);

                inspectiontimer.setText("15:00");
                cubertimer.setText("00:00:00");
                flag=true;
                interrupt=0;
                onetime=0;
                MillisecondTime=0L;
                StartTime=0L;
                TimeBuff=0L;
                UpdateTime=0L;
                Seconds=0;
                MilliSeconds=0;
                Minutes=0;
                StartTime = SystemClock.uptimeMillis();

                return true;
            }
        });

        mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(interrupt==0) {

                    if (flag == true) {
                        StartTime = SystemClock.uptimeMillis();
                        handler.postDelayed(inspection, 0);

                    } else {

                        if (onetime == 0) {
                            TimeBuff += MillisecondTime;
                            handler.removeCallbacks(runnable);
                            onetime++;

                        } else {
                            StartTime = SystemClock.uptimeMillis();
                            handler.postDelayed(runnable, 0);
                            onetime--;

                        }


                    }
                }

                else{

                    handler.removeCallbacks(inspection);

                    if (onetime == 0) {
                        StartTime = SystemClock.uptimeMillis();
                        handler.postDelayed(runnable, 0);
                        onetime++;



                    } else {
                        TimeBuff += MillisecondTime;
                        handler.removeCallbacks(runnable);
                        onetime--;
                    }





                }








            }
        });




    }
}
