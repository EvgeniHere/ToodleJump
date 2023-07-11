package com.evgeni.toodlejump;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    static int tx;
    static int ty;
    Context con;
    static String name;
    static long highscore = 0;
    static String highscoreName = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        con = this;

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.editText3);
                name = String.valueOf(et.getText());
                Player pl = new Player();
                Firebase fu = new Firebase();
                setContentView(new MainCanvas(con));
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        tx = (int) e.getX();
        ty = (int) e.getY();

        return true;
    }
}
