package com.evgeni.toodlejump;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player {

    static int x;
    static double y;
    static int width;
    static int height;
    static boolean loaded = false;
    static Timer timer1;
    static Timer timer2;
    static Timer timer3;
    static double velY = 0;
    static ArrayList<Timer> timers1 = null;
    static ArrayList<Timer> timers2 = null;
    static ArrayList<Timer> timers3 = null;
    static boolean jumped = false;
    static int distance = 0;
    static boolean dead = false;
    static int leben;
    static int oldLeben = 3;
    static boolean restart = true;
    static long highscore = 0;

    public Player() {
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
        if (restart) {
            leben = oldLeben;
            width = 100;
            height = 100;
            x = MainCanvas.canvasWidth / 2 - width / 2;
            y = MainCanvas.canvasHeight - height;
            dead = false;
            jumped = false;
            restart = false;
            velY = 0;
            timer3 = null;
            if (timers1 != null) {
                clearAllTimers();
                timers1.clear();
                timers2.clear();
                timers3.clear();
            }
            timers1 = null;
            loaded = true;
            preparation();
        }
    }

    public static void clearAllTimers() {
        if (timer1 != null) {
            timer1.cancel();
        }
        if (timer2 != null) {
            timer2.cancel();
        }
        if (timer3 != null) {
            timer3.cancel();
        }
        if (!timers1.isEmpty()) {
            for (int i = 0; i < timers1.size(); i++) {
                timers1.get(i).cancel();
            }
        }
        if (!timers2.isEmpty()) {
            for (int i = 0; i < timers2.size(); i++) {
                timers2.get(i).cancel();
            }
        }
        if (!timers3.isEmpty()) {
            for (int i = 0; i < timers3.size(); i++) {
                timers3.get(i).cancel();
            }
        }
    }

    public static void preparation() {
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                int tx = FullscreenActivity.tx;

                if (tx != 0 && tx > width/2 && tx + width/2 < MainCanvas.canvasWidth) {
                    if (timers1 == null) {
                        start();
                    }
                    x = tx - width/2;
                }
            }
        }, 0, 10);
    }

    public static void start() {
        World wl = new World();
        distance = (int) highscore;
        restart = false;
        intersection();
        timers1 = new ArrayList<>();
        timers2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            timers1.add(new Timer());
        }
        for (int i = 0; i < timers1.size(); i++) {
            timers1.get(i).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!dead) {
                        if (y + getVelY() < 0) {
                            velY = 0;
                            y = 0;
                        }
                        y += getVelY();
                        if (y + height >= MainCanvas.canvasHeight) {
                            if (!jumped) {
                                jump();
                            } else {
                                damageTaken();
                            }
                        }
                    }
                }
            }, i, 20);
        }

        for (int i = 0; i < 10; i++) {
            timers2.add(new Timer());
        }
        for (int i = 0; i < timers2.size(); i++) {
            timers2.get(i).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!dead) {
                        if (getVelY() < 4) {
                            velY += World.g;
                        }
                    }
                }
            }, 0, 20);
        }

        timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                if (jumped && !dead) {
                    distance++;
                }
            }
        }, 0, 5);
    }

    public static void intersection() {
        timers3 = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            timers3.add(new Timer());
        }
        for (int i = 0; i < timers3.size(); i++) {
            timers3.get(i).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!dead) {
                        for (int i = 0; i < World.platten.size(); i++) {
                            Platte pl = World.platten.get(i);
                            if (pl != null) {
                                int py = (int) pl.y;
                                if (py > 0 && py < MainCanvas.canvasHeight) {
                                    Rect r1 = new Rect(pl.x, (int) pl.y, pl.x + pl.width, (int) pl.y + pl.height);
                                    Rect r2 = new Rect(x, (int) y, x + width, (int) y + height);
                                    if (r2.intersect(r1)) {
                                        if (velY < 0) {
                                            if (distance >= World.distGreen) {
                                                Rect upperPart = new Rect(x, (int) y, x + width, (int) y + height / 10);
                                                if (upperPart.intersect(r1)) {
                                                    velY = 0;
                                                    if (distance >= World.distRedDeath) {
                                                        damageTaken();
                                                    }
                                                }
                                            }
                                        } else if (velY > 0) {
                                            Rect deeperPart = new Rect(x, (int) y + height - (height / 10), x + width, (int) y + height);
                                            if (deeperPart.intersect(r1)) {
                                                jumped = true;
                                                jump();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }, i, 20);
        }
    }

    public static void damageTaken() {
        MainCanvas.damageTaken = true;
        System.out.println("Damage taken");
        if (leben > 0) {
            leben--;
            jumped = false;
        } else {
            dead();
        }

        Timer timer4 = new Timer();
        timer4.schedule(new TimerTask() {
            @Override
            public void run() {
                MainCanvas.damageTaken = false;
            }
        }, 1000);
    }

    public static void jump() {
        velY = -5;
    }

    public static double getVelY() {
        return velY;
    }

    public static void dead() {
        dead = true;
        restart = true;
        if (distance > highscore) {
            highscore = distance;
        }
        World.dead();
    }

    public static void returned() {
        if (timer3 != null) {
            timer3.cancel();
        }
        constructorStuff();
    }

    public static int getCurrentColor() {
        if (distance >= World.distRedDeath) {
            return Color.BLACK;
        } else if (distance >= World.distGreen) {
            return Color.GREEN;
        } else {
            return Color.rgb(245, 91, 188);
        }
    }
}
