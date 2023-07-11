package com.evgeni.toodlejump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainCanvas extends View {

    static int canvasWidth;
    static int canvasHeight;
    static boolean loaded = false;
    Timer timer1;
    Bitmap bitmapHase;
    Bitmap bitmapHaseFallend;
    Bitmap bitmapHaseHalbFallend;
    Bitmap charBitmap;
    static Bitmap bitmapWolke;
    Bitmap bitmapLeeresHerz;
    Bitmap bitmapVollesHerz;
    Bitmap bitmapWolke_Ground_Red;
    Bitmap bitmapWolke_Ground_Pink;
    Bitmap bitmapWolke_Ground;
    Bitmap bitmapWolke_Red_Death;
    static boolean damageTaken = false;
    Rect rectFullscreen;

    Bitmap bitmapWolke_White;
    Bitmap bitmapWolke_Yellow;
    Bitmap bitmapWolke_Blue;
    Bitmap bitmapWolke_Black;
    Bitmap bitmapWolke_FullBlack;
    Bitmap bitmapWolke_Violett;
    Bitmap bitmapWolke_Orange;
    Bitmap bitmapWolke_Green;
    static Bitmap bitmapWolke_Pink;
    static Bitmap bitmapWolke_Red;

    Resources res;

    public MainCanvas(Context context) {
        super(context);

        this.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        this.setBackgroundColor(Color.CYAN);

        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                invalidate();
            }
        }, 0, 15);

        res = getResources();
        bitmapHase = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.hase), 100, 200);
        bitmapHaseFallend = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.hasefallend), 100, 200);
        bitmapHaseHalbFallend = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.hasehalbfallend), 100, 200);
        bitmapWolke = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke), 200, 100);
        bitmapLeeresHerz = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.emptyheart), 56, 48);
        bitmapVollesHerz = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.fullheart), 56, 48);
        bitmapWolke_Pink = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke), Platte.width, Platte.width / 2);
        bitmapWolke_Red = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_red), Platte.width, Platte.width / 2);
        bitmapWolke_Green = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_green), Platte.width, Platte.width / 2);
        charBitmap = bitmapHase;

        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Player.distance >= World.distRedDeath && bitmapWolke != bitmapWolke_Red_Death) {
                    bitmapWolke_Red_Death = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_red_death), Platte.width, (int) ((double) Platte.width * 0.8));
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Red_Death;
                    setBackgroundColor(Color.rgb(143, 64, 61));
                } else if (Player.distance >= World.distFullBlack && bitmapWolke != bitmapWolke_FullBlack && Player.distance < World.distRedDeath) {
                    bitmapWolke_FullBlack = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_fullblack), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_FullBlack;
                    setBackgroundColor(Color.WHITE);
                } else if (Player.distance >= World.distBlack && bitmapWolke != bitmapWolke_Black && Player.distance < World.distFullBlack) {
                    bitmapWolke_Black = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_black), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Black;
                    setBackgroundColor(Color.GRAY);
                } else if (Player.distance >= World.distWhite && bitmapWolke != bitmapWolke_White && Player.distance < World.distBlack) {
                    bitmapWolke_White = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_white), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_White;
                    setBackgroundColor(Color.LTGRAY);
                } else if (Player.distance >= World.distViolett && bitmapWolke != bitmapWolke_Violett && Player.distance < World.distWhite) {
                    bitmapWolke_Violett = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_violett), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Violett;
                    setBackgroundColor(Color.MAGENTA);
                } else if (Player.distance >= World.distYellow && bitmapWolke != bitmapWolke_Yellow && Player.distance < World.distViolett) {
                    bitmapWolke_Yellow = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_yellow), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Yellow;
                    setBackgroundColor(Color.GREEN);
                } else if (Player.distance >= World.distOrange && bitmapWolke != bitmapWolke_Orange && Player.distance < World.distYellow) {
                    bitmapWolke_Orange = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_orange), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Orange;
                    setBackgroundColor(Color.YELLOW);
                } else if (Player.distance >= World.distBlue && bitmapWolke != bitmapWolke_Blue && Player.distance < World.distOrange) {
                    bitmapWolke_Blue = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_blue), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Blue;
                    setBackgroundColor(Color.GRAY);
                } else if (Player.distance >= World.distGreen && bitmapWolke != bitmapWolke_Green && Player.distance < World.distBlue) {
                    bitmapWolke_Green = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_green), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Green;
                    setBackgroundColor(Color.LTGRAY);
                } else if (Player.distance >= World.distRed && bitmapWolke != bitmapWolke_Red && Player.distance < World.distGreen) {
                    bitmapWolke_Red = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke_red), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Red;
                    bitmapWolke = bitmapWolke_Red;
                    setBackgroundColor(Color.CYAN);
                } else if (Player.distance >= World.distPink && bitmapWolke != bitmapWolke_Pink && Player.distance < World.distRed) {
                    bitmapWolke_Pink = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.wolke), Platte.width, Platte.width / 2);
                    bitmapWolke_Ground = bitmapWolke_Ground_Pink;
                    bitmapWolke = bitmapWolke_Pink;
                    setBackgroundColor(Color.CYAN);
                }
            }
        }, 0, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = getWidth();
        canvasHeight = getHeight();

        if (!loaded) {
            bitmapWolke_Ground_Pink = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wolke_ground), canvasWidth, canvasWidth / 4);
            bitmapWolke_Ground_Red = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wolke_ground_red), canvasWidth, canvasWidth / 4);
            bitmapWolke_Ground = bitmapWolke_Ground_Pink;
            bitmapWolke = bitmapWolke_Pink;
            rectFullscreen = new Rect(0, 0, canvasWidth, canvasHeight);
            loaded = true;
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);

        for (int i = 0; i < Firebase.players.size(); i++) {
            OnlinePlayer pl = Firebase.players.get(i);
            if (!pl.name.equals(FullscreenActivity.name)) {
                int x = (int) (pl.x * (double) canvasWidth);
                int y = (int) (pl.y * (double) canvasHeight) - (int) (((double) pl.d * Platte.speed) - ((double) Player.distance * Platte.speed));
                canvas.drawBitmap(bitmapHase, x, y, paint);
                canvas.drawText(pl.name, x, y + 230, paint);
            }
        }

        paint.setTextSize(100);

        World.draw(canvas);

        if (Player.velY > 0) {
            if (Player.velY > 1) {
                charBitmap = bitmapHaseFallend;
            } else {
                charBitmap = bitmapHaseHalbFallend;
            }
        } else {
            charBitmap = bitmapHase;
        }

        for (int i = 0; i < Player.leben; i++) {
            canvas.drawBitmap(bitmapVollesHerz, canvasWidth - (i+1) * 60, 0, paint);
        }
        for (int j = Player.leben-1; j < 3; j++) {
            canvas.drawBitmap(bitmapLeeresHerz, canvasWidth - (j+1) * 60, 0, paint);
        }

        canvas.drawBitmap(charBitmap, Player.x, (int) Player.y - 100, paint);

        paint.setAlpha(Player.leben * 80);
        canvas.drawBitmap(bitmapWolke_Ground, 0, canvasHeight - bitmapWolke_Ground.getHeight(), paint);
        paint.setAlpha(250);
        canvas.drawText("" + Player.distance, 0, 100, paint);
        canvas.drawText("" + Player.highscore, 0, 200, paint);

        paint.setTextSize(50);
        paint.setColor(Color.RED);
        canvas.drawText(FullscreenActivity.highscoreName + ": " + FullscreenActivity.highscore, 0, 250, paint);

        if (damageTaken) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Player.getCurrentColor());
            paint.setStrokeWidth(40);
            rectFullscreen.set(0, 0, canvasWidth, canvasHeight);
            canvas.drawRect(rectFullscreen, paint);
            paint.setStrokeWidth(10);
            rectFullscreen.set(50, 50, canvasWidth - 50, canvasHeight - 50);
            canvas.drawRect(rectFullscreen, paint);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
