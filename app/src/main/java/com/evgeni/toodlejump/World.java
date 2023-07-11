package com.evgeni.toodlejump;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class World {

    static float g = 0.02f;
    static ArrayList<Platte> platten = new ArrayList<>();
    Timer timer1;
    static Timer timer2;
    static int distPink = 0;
    static int distRed = 5000;
    static int distGreen = 10000;
    static int distBlue = 15000;
    static int distOrange = 30000;
    static int distYellow = 40000;
    static int distViolett = 50000;
    static int distWhite = 65000;
    static int distBlack = 80000;
    static int distFullBlack = 100000;
    static int distRedDeath = 150000;

    public World() {
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                while(!MainCanvas.loaded) {
                    System.out.print("");
                }
                constructorStuff();
            }
        }, 0);
    }

    public static void constructorStuff() {
        platten.clear();
        platten = new ArrayList<>();
        Platte.speed = Platte.oldSpeed;
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                platten.add(new Platte((int) (Math.random()*(MainCanvas.canvasWidth - 200)), -20));
                for (int i = 0; i < platten.size(); i++) {
                    if (platten.get(i).y > MainCanvas.canvasHeight) {
                        platten.get(i).timer1.cancel();
                        platten.remove(i);
                    }
                }
            }
        }, 1000, 1000);
    }

    public static void draw(Canvas canvas) {
        if (platten != null) {
            for (int i = 0; i < platten.size(); i++) {
                platten.get(i).draw(canvas);
            }
        }
    }

    public static void dead() {
        timer2.cancel();

        for (int i = 0; i < platten.size(); i++) {
            platten.get(i).getBack();
        }

        Timer timer1;
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                Player.returned();
            }
        }, 2000);
    }
}
