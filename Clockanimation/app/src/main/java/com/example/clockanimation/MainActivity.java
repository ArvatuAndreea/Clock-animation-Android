package com.example.clockanimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TimerSurfaceView timerView = null;
    private ClockSurfaceView clockView = null;
    private Button timerBtn, clockBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        timerBtn = findViewById(R.id.timer_btn);
        clockBtn = findViewById(R.id.clock_btn);

        timerView = new TimerSurfaceView(getApplicationContext(), 300);
        clockView = new ClockSurfaceView(getApplicationContext(), 300);

        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
                addNotification("timer");

                setContentView(timerView);
            }
        });
        
        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
                addNotification("simple clock");

                setContentView(clockView);
            }
        });
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = new TimerSurfaceView(getApplicationContext(), 300);   //pentru timer
        clockView = new ClockSurfaceView(getApplicationContext(), 300);   //pentru ceas
        setContentView(timerView);
    }*/

    @Override
    protected void onResume(){
        super.onResume();
        clockView.onResumeTimer();
        timerView.onResumeTimer();
    }

    @Override
    protected void onPause(){
        super.onPause();
        clockView.onPauseTimer();
        timerView.onPauseTimer();
    }

    private void createNotification() {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            CharSequence name="Clock animation";
            String desc="This is about what type of view has been chosen";

            NotificationChannel notificationChannel=new NotificationChannel("simple_notification", name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(desc);

            NotificationManager notificationManager=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    private void addNotification(String type) {

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"simple_notification");
        builder.setSmallIcon(R.drawable.logo_ceas);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo_ceas));
        builder.setContentTitle("A type of clock has been chosen");
        builder.setContentText("The " + type + " has been chosen");
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Add as notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, builder.build());

    }
}