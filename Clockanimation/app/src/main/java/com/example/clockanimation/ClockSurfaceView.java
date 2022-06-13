package com.example.clockanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

public class ClockSurfaceView extends SurfaceView implements Runnable{

    private int length;

    private Thread thread;
    private boolean isRunning = false;

    private SurfaceHolder holder;

    public ClockSurfaceView(Context context, int length){
        super(context);
        this.length = length;

        holder = getHolder();
    }

    // methods to manage the thread
    public void onResumeTimer(){
        thread = new Thread(this);thread.start();
        isRunning = true;
    }

    public void onPauseTimer(){
        isRunning = false;

        boolean reentry = true;
        while(reentry){
            try {
                thread.join();
                reentry = false;

            }catch(Exception e){}
        }
    }

    @Override
    public void run() {

        while (isRunning){
            if(holder.getSurface().isValid()){

                // get canvas from holder
                Canvas canvas = holder.lockCanvas();

                // clear screen
                Paint bgPaint = new Paint(); bgPaint.setColor(Color.CYAN);
                canvas.drawPaint(bgPaint);

                // paint new screen
                Paint fgPaint = new Paint(); fgPaint.setColor(Color.BLACK);
                fgPaint.setStrokeWidth(5);
                RegPoly secMarks = new RegPoly(60, getWidth()/2, getHeight()/2,this.length, canvas, fgPaint);
                secMarks.drawNodes();

                RegPoly hourMarks = new RegPoly(12, getWidth()/2, getHeight()/2,this.length-10, canvas, fgPaint);
                hourMarks.drawNumbers();

                Paint anotherfgPaint = fgPaint;
                anotherfgPaint.setColor(Color.MAGENTA);
                anotherfgPaint.setStrokeWidth(10);

                RegPoly secHand  = new RegPoly(60, getWidth()/2, getHeight()/2,this.length-30, canvas, anotherfgPaint);
                RegPoly minHand  = new RegPoly(60, getWidth()/2, getHeight()/2,this.length-60, canvas, anotherfgPaint);
                RegPoly hourHand = new RegPoly(60, getWidth()/2, getHeight()/2,this.length-90, canvas, anotherfgPaint);

                // get current time
                Calendar calendar = Calendar.getInstance();
                int sec  = calendar.get(Calendar.SECOND);
                int min  = calendar.get(Calendar.MINUTE);
                int hour = calendar.get(Calendar.HOUR);

                secHand.drawRadius(sec+45);
                minHand.drawRadius(min+45);
                hourHand.drawRadius(hour*5+min/12+45);

                // delay 1 sec
                try{
                    Thread.sleep(1000);
                }catch (Exception e){}


                holder.unlockCanvasAndPost(canvas);
            }
        }

    }
}
