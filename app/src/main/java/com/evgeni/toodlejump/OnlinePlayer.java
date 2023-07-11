package com.evgeni.toodlejump;

public class OnlinePlayer {

    double x, y;
    long d, h;
    String name;

    public OnlinePlayer(String name, double x, double y, long d, long h) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.name = name;
        setHighscore(h);
    }

    public void setPos(double x, double y, long d) {
        this.x = x;
        this.y = y;
        this.d = d;
    }

    public void setHighscore(long highscore) {
        this.h = highscore;
        if (h > FullscreenActivity.highscore) {
            FullscreenActivity.highscore = (int) h;
            FullscreenActivity.highscoreName = name;
            System.out.println("New Highscore of " + h + " by " + name + "!");
        }
    }
}
