package com.example.clockanimation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RegPoly {

    private Canvas canvas;
    private Paint paint;

    private int n;
    private float x0, y0, r;
    private float x[], y[];

    public RegPoly(int n, float x0, float y0, int r, Canvas canvas, Paint paint){
        this.n = n;
        this.x0 = x0; this.y0 = y0;
        this.r = r;

        this.canvas = canvas; this.paint = paint;

        // calculate the x, y coords
        x = new float[this.n]; y = new float[this.n];
        for(int i=0;i<this.n;i++){
            x[i] = (float)(x0 + this.r * Math.cos(2*Math.PI*i/this.n));
            y[i] = (float)(y0 + this.r * Math.sin(2*Math.PI*i/this.n));
        }

    }

    public float getX(int i) {
        return x[i%this.n];
    }
    public float getY(int i){
        return y[i%this.n];
    }

    // draw methods
    public void drawNodes(){
        for(int i=0;i<this.n;i++){
            this.canvas.drawCircle(getX(i), getY(i), 3,this.paint);
        }
    }

    public void drawNumbers(){
        paint.setTextSize(40f);
        paint.setColor(Color.RED);
        for(int i=0;i<this.n;i++){
            if(i == 10)
                this.canvas.drawText("1", getX(i), getY(i), this.paint);
                else
                if(i == 11)
                    this.canvas.drawText("2", getX(i), getY(i), this.paint);
                else
                    this.canvas.drawText(((i + 3) % (n + 1)) + "", getX(i), getY(i), this.paint);
            //this.canvas.drawText((i + 1) + "", (int)getX(i) + 10, (int)getY(i) + 10, getX(i), getY(i), this.paint);
        }
    }

    public void drawRadius(int i){
        this.canvas.drawLine(this.x0, this.y0, getX(i%this.n), getY(i%this.n), this.paint);
    }
}
