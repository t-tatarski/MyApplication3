package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import java.util.Random;


public class  MainActivity extends AppCompatActivity {
    private ImageView imgVw;
    private ImageView imgVw2;
    private ImageView imgVv3, imgVw4;
    private Random rndm = new Random();
    private Random direc = new Random();
    private SensorManager mSensorManager;
    public SoundPool mSoundpool;
    private float mAc;
    private float mACur;
    private float mAcL;
    public int cv=0;
    public int sound1 =1;
    public int sound2 =2;
    public int busy=0;
    public int dices=1;
    public static final String SHARED_PREFS = "myprefs";
    public static final String SHARED_VALUE = "status";
    public SharedPreferences sharedPreferences;


    public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one:
                dices=1;
                savePref(1);
                super.recreate();
                return (true);
            case R.id.two:
                dices=2;
                savePref(2);
                super.recreate();
                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }


    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAcL = mACur;
            mACur = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mACur - mAcL;
            mAc = mAc * 0.9f + delta;
            if (mAc > 15 && busy==0) {
                busy=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        losuj();
                        zakrec();
                        vi();
                        playsound();
                        busy=0;
                    }
                }, 450);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Application.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.contains(SHARED_VALUE)){
            editor.putInt(SHARED_VALUE,dices);
            editor.apply();
        }
        dices = sharedPreferences.getInt(SHARED_VALUE, 1);


        imgVw = findViewById(R.id.imageView);
        imgVw.setRotation(15f);
        imgVw2 = findViewById(R.id.imageView2);
        imgVw2.setRotation(15f);

        imgVv3 = findViewById(R.id.imageView3);
        imgVv3.setRotation(22f);
        imgVw4 = findViewById(R.id.imageView4);
        imgVw4.setRotation(22f);
        if (dices==1){
            imgVv3.setVisibility(View.INVISIBLE);
            imgVw4.setVisibility(View.INVISIBLE);
        }
        if (dices==2){
            imgVv3.setVisibility(View.VISIBLE);
            imgVw4.setVisibility(View.VISIBLE);
        }


//        sound
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSoundpool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();
       sound1= mSoundpool.load(this, R.raw.soundkostka3, 1);
        sound2= mSoundpool.load(this, R.raw.soundkostka2, 1);



        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAc = 0.00f;
        mACur = SensorManager.GRAVITY_EARTH;
        mAcL = SensorManager.GRAVITY_EARTH;

        imgVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        losuj();
                        zakrec();
                        playsound();
                        vi();

                    }
                }, 450);


            }
        });
        imgVv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        losuj();
                        zakrec();
                        playsound();
                        vi();

                    }
                }, 450);


            }
        });


    }

    public void losuj() {
        int cyfra = rndm.nextInt(6) + 1;

        switch (cyfra) {
            case 1:
                imgVw.setImageResource(R.drawable.b1);
                break;
            case 2:
                imgVw.setImageResource(R.drawable.b2);
                break;
            case 3:
                imgVw.setImageResource(R.drawable.b3);
                break;
            case 4:
                imgVw.setImageResource(R.drawable.b4);
                break;
            case 5:
                imgVw.setImageResource(R.drawable.b5);
                break;
            case 6:
                imgVw.setImageResource(R.drawable.b6);
                break;
        }
        if (dices == 2) {
            cyfra = rndm.nextInt(6) + 1;
            switch (cyfra) {
                case 1:
                    imgVv3.setImageResource(R.drawable.b1);
                    break;
                case 2:
                    imgVv3.setImageResource(R.drawable.b2);
                    break;
                case 3:
                    imgVv3.setImageResource(R.drawable.b3);
                    break;
                case 4:
                    imgVv3.setImageResource(R.drawable.b4);
                    break;
                case 5:
                    imgVv3.setImageResource(R.drawable.b5);
                    break;
                case 6:
                    imgVv3.setImageResource(R.drawable.b6);
                    break;
            }

        }
    }
    public void zakrec(){
        int kier=direc.nextInt(2);
        Animation rotation= AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        Animation rotationcv = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotatecv);
        if (kier==0){
            imgVw.startAnimation(rotation);
            imgVw2.startAnimation(rotation);
            if(dices==2){
                imgVv3.startAnimation(rotation);
                imgVw4.startAnimation(rotation);
            }
        }
        if (kier==1) {
            cv = 1080;

            imgVw.startAnimation(rotationcv);
            imgVw2.startAnimation(rotationcv);
            if(dices==2){
                imgVv3.startAnimation(rotation);
                imgVw4.startAnimation(rotation);
            }

        }
    }
    public void vi() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));

        } else {
            vibrator.vibrate(200);
        }


    }
    public void playsound() {


                if (dices==2){
                    mSoundpool.play(1, 1, 1, 1, 0, 1f);
                    mSoundpool.play(2, 1, 1, 1, 0, 1f);
                }else{
                    int soundid = direc.nextInt(2) + 1;

                    mSoundpool.play(soundid, 1, 1, 1, 0, 1f);
                }


            }

            public void savePref(int i){
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putInt(SHARED_VALUE, i);
                editor.apply();
            }

    }




