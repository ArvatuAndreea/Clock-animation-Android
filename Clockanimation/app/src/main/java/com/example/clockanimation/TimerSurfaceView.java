package com.example.clockanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TimerSurfaceView extends SurfaceView implements Runnable{

    private int length;

    private Thread thread;
    private boolean isRunning = false;

    private SurfaceHolder holder;

    public TimerSurfaceView(Context context, int length) {
        super(context);
        this.length = length;
        holder = getHolder();
    }

    // methods to manage the thread
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

    public void onResumeTimer(){
        thread = new Thread(this);thread.start();
        isRunning = true;
    }

    @Override
    public void run() {
        int sec = 0;

        while (isRunning){
            if(holder.getSurface().isValid()){

                // get canvas from holder
                Canvas canvas = holder.lockCanvas();

                // clear screen
                Paint bgPaint = new Paint(); bgPaint.setColor(Color.RED);
                canvas.drawPaint(bgPaint);

                // paint new screen
                Paint fgPaint = new Paint(); fgPaint.setColor(Color.BLACK);
                RegPoly secMarks = new RegPoly(60, getWidth()/2, getHeight()/2,this.length, canvas, fgPaint);
                secMarks.drawNodes();

                Paint anotherfgPaint = fgPaint;
                anotherfgPaint.setColor(Color.CYAN);
                anotherfgPaint.setStrokeWidth(10);

                RegPoly secHand = new RegPoly(60, getWidth()/2, getHeight()/2,this.length-20, canvas, anotherfgPaint);
                secHand.drawRadius(sec + 45);

                // delay 1 sec
                try{
                    Thread.sleep(1000);
                }catch (Exception e){}
                sec++;

                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
