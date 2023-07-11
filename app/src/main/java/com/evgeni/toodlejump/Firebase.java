package com.evgeni.toodlejump;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class Firebase {

    Timer timer1;
    Timer timer2;
    static ArrayList<OnlinePlayer> players = new ArrayList<>();

    public Firebase() {
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                downloadData();
            }
        }, 0);

        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                while(!Player.loaded) {
                    System.out.print("");
                }
                uploadData();
            }
        }, 1000, 30);
    }

    public void uploadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("player").child(FullscreenActivity.name);

        while (players.size() <= 0) {
            System.out.print("");
        }

        myRef.child("location").child("x").setValue(Player.x / (double) MainCanvas.canvasWidth);
        myRef.child("location").child("y").setValue(Player.y / (double) MainCanvas.canvasHeight);
        myRef.child("location").child("d").setValue(Player.distance);

        boolean in = false;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).name.equals(FullscreenActivity.name)) {
                in = true;
                OnlinePlayer pl = players.get(i);
                if (Player.highscore > pl.h) {
                    myRef.child("highscore").setValue(Player.highscore);
                } else {
                    Player.highscore = pl.h;
                }
            }
        }

        if (!in) {
            myRef.child("highscore").setValue(0);
        }
    }

    public void downloadData() {
        System.out.println("Downloading...");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("player");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    boolean in = false;
                    int currentIndex = 0;
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).name.equals(String.valueOf(snap.getKey()))) {
                            in = true;
                            currentIndex = i;
                            i = players.size();
                        }
                    }
                    if (in) {
                        players.get(currentIndex).setPos(Double.valueOf(String.valueOf(snap.child("location").child("x").getValue())),
                                Double.valueOf(String.valueOf(snap.child("location").child("y").getValue())),
                                (long) snap.child("location").child("d").getValue());
                        players.get(currentIndex).setHighscore((long)snap.child("highscore").getValue());
                    } else {
                        if (snap.child("location").child("x").getValue() != null
                                && snap.child("location").child("y").getValue() != null
                                && snap.child("location").child("d").getValue() != null
                                && snap.child("highscore").getValue() != null) {
                            players.add(new OnlinePlayer(snap.getKey(),
                                    Double.valueOf(String.valueOf(snap.child("location").child("x").getValue())),
                                    Double.valueOf(String.valueOf(snap.child("location").child("y").getValue())),
                                    (long) snap.child("location").child("d").getValue(),
                                    (long) snap.child("highscore").getValue()));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
