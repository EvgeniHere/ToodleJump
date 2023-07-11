package com.evgeni.toodlejump;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Timer;
import java.util.TimerTask;

public class Platte {

    int x;
    double y;
    static int width = 100;
    static int height = 20;
    static double speed = 2;
    static double oldSpeed = 2;
    Timer timer1;

    public Platte(int x, int y) {
        this.x = x;
        this.y = y;
        start();
    }

    public void start() {
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (MainCanvas.loaded) {
                    if (y < MainCanvas.canvasHeight + height || y + height > 0) {
                        y += speed;
                    }
                }
            }
        }, 0, 5);
    }

    public void draw(Canvas canvas) {
        Paint p1 = new Paint();
        p1.setColor(Color.GREEN);
        p1.setStyle(Paint.Style.FILL);

        canvas.drawBitmap(MainCanvas.bitmapWolke, x, (int) y - (int) ((double) width / 5.0), p1);
    }

    public void getBack() {
        if (y + height > 0) {
            speed = -5;
        } else {
            timer1.cancel();
        }
    }
}