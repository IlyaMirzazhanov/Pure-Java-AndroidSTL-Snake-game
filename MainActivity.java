package com.example.snake;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.util.Random;

public class MainActivity extends Activity {
    private final int ALL_DOTS = 900;
    private int H_S;
    private int SCORE = 0;
    private int dots;
    public boolean inGame = true;
    SharedPreferences sPref;
    Vibrator vibe;
    int[] x = new int[900];
    int[] y = new int[900];

    private class myView extends View {
        int DOT_SIZE = ((int) ((2.0f * this.scale) + 0.5f));
        Display disp = ((WindowManager) getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int bx = (this.disp.getWidth() / 10);
        int by = (this.disp.getHeight() / 12);
        boolean mdn = false;
        boolean mlf = false;
        boolean mrt = false;
        boolean mup = false;
        Paint myPaint;
        final float scale = getResources().getDisplayMetrics().density;
        int sp = 5;
        private final int speed = 2;
        int sx = (this.disp.getWidth() / 3);
        int sy = (this.disp.getHeight() / 3);
        int wd = (this.disp.getWidth() / 5);
        int x1 = ((this.disp.getWidth() / 3) + (this.wd / 3));
        int x2 = (((this.disp.getWidth() / 3) + (this.wd * 2)) - (this.wd / 3));
        int x3 = ((this.disp.getWidth() / 3) + this.wd);
        int x4 = ((this.disp.getWidth() / 3) + this.wd);
        int y1 = ((this.disp.getHeight() / 6) * 4);
        int y2 = ((this.disp.getHeight() / 6) * 4);
        int y3 = (((this.disp.getHeight() / 6) * 4) - ((this.wd / 3) * 2));
        int y4 = (((this.disp.getHeight() / 6) * 4) + ((this.wd / 3) * 2));

        Rect up = new Rect(this.x3, this.y3, this.x3 + this.wd, this.y3 + this.wd);
        Rect upb = new Rect(0, 0, this.disp.getWidth(), this.disp.getHeight() / 45);
        Rect dnb = new Rect(0, (this.disp.getHeight() / 2) - (this.disp.getHeight() / 45), this.disp.getWidth(), this.disp.getHeight() / 2);
        Rect down = new Rect(this.x4, this.y4, this.x4 + this.wd, this.y4 + this.wd);
        Rect lfb = new Rect(0, 0, this.disp.getHeight() / 45, this.disp.getHeight() / 2);
        Rect lft = new Rect(this.x1, this.y1, this.x1 + this.wd, this.y1 + this.wd);
        Rect rght = new Rect(this.x2, this.y2, this.x2 + this.wd, this.y2 + this.wd);
        Rect rtb = new Rect(this.disp.getWidth() - (this.disp.getHeight() / 45), 0, this.disp.getWidth(), this.disp.getHeight() / 2);

        public myView(Context context) {
            super(context);
            MainActivity.this.vibe = (Vibrator) MainActivity.this.getSystemService(VIBRATOR_SERVICE);
            this.myPaint = new Paint();
            initGame();
        }

        private void initGame() {
            MainActivity.this.sPref = MainActivity.this.getSharedPreferences("MyPref", 0);
            MainActivity.this.H_S = MainActivity.this.sPref.getInt("load", MainActivity.this.H_S);
            MainActivity.this.dots = 5;
            for (int z = 0; z < MainActivity.this.dots; z++) {
                MainActivity.this.x[z] = 50 - (z * 10);
                MainActivity.this.y[z] = 50;
            }
        }

        private void checkCollision() {
            int z = MainActivity.this.dots;
            while (z > 0) {
                if (z > 6 && MainActivity.this.x[0] == MainActivity.this.x[z] && MainActivity.this.y[0] == MainActivity.this.y[z]) {
                    MainActivity.this.inGame = false;
                }
                z--;
            }
        }

        /* Access modifiers changed, original: protected */
        public void onDraw(Canvas canvas) {
            int z;
            canvas.drawColor(-16777216);
            this.myPaint.setColor(Color.WHITE);
            canvas.drawRect(this.lft, this.myPaint);
            canvas.drawRect(this.rght, this.myPaint);
            canvas.drawRect(this.up, this.myPaint);
            canvas.drawRect(this.down, this.myPaint);
            canvas.drawRect(this.upb, this.myPaint);
            canvas.drawRect(this.lfb, this.myPaint);
            canvas.drawRect(this.rtb, this.myPaint);
            canvas.drawRect(this.dnb, this.myPaint);
            this.myPaint.setColor(-16711936);
            Rect head = new Rect(x[0], y[0], x[0] + (disp.getHeight() / 40), y[0] + (disp.getHeight() / 40));
            for (z = 0; z < dots; z++) {
                canvas.drawRect(new Rect(x[z], y[z], x[z] + (disp.getHeight() / 40), MainActivity.this.y[z] + (this.disp.getHeight() / 40)), this.myPaint);
                //invalidate();
            }
            //invalidate();
            this.myPaint.setColor(-65536);
            Rect bonus = new Rect(this.bx, this.by, this.bx + (this.disp.getHeight() / 45), this.by + (this.disp.getHeight() / 45));
            Random rand = new Random();
            if (head.intersect(bonus)) {
                MainActivity.this.dots = MainActivity.this.dots + 2;
                MainActivity.this.SCORE = MainActivity.this.SCORE + 1;
                this.bx = rand.nextInt(this.disp.getWidth() - (this.disp.getWidth() / 4)) + (this.disp.getHeight() / 50);
                this.by = rand.nextInt(this.disp.getHeight() / 3) + (this.disp.getHeight() / 30);
            }
            canvas.drawRect(bonus, this.myPaint);
            for (z = dots; z > 0; z--) {
               x[z] = x[z - 1];
               y[z] = y[z - 1];
            }
            if (this.mlf) {
                x[0] -= this.DOT_SIZE;
            }
            if (this.mrt) {
                x[0] += this.DOT_SIZE;
            }
            if (this.mup) {
                y[0] -= this.DOT_SIZE;
            }
            if (this.mdn) {
                y[0] += this.DOT_SIZE;
            }
            if (MainActivity.this.inGame) {
                checkCollision();
            }
            if (head.intersect(this.upb) || head.intersect(this.dnb) || head.intersect(this.rtb) || head.intersect(this.lfb)) {
                MainActivity.this.inGame = false;
            }
            if (!MainActivity.this.inGame) {
                this.mlf = false;
                this.mrt = false;
                this.mup = false;
                this.mdn = false;
                initGame();
                if (MainActivity.this.SCORE > MainActivity.this.H_S) {
                    MainActivity.this.H_S = MainActivity.this.SCORE;
                }
                MainActivity.this.sPref = MainActivity.this.getSharedPreferences("MyPref", 0);
                Editor ed = MainActivity.this.sPref.edit();
                ed.putInt("load", MainActivity.this.H_S);
                ed.apply();
                MainActivity.this.SCORE = 0;
                MainActivity.this.inGame = true;
            }
            this.myPaint.setTextSize((float) (this.disp.getHeight() / 20));
            this.myPaint.setAntiAlias(true);
            this.myPaint.setColor(-1);
            canvas.drawText("Score: " + MainActivity.this.SCORE, 0.0f, (float) (this.disp.getHeight() - (this.disp.getHeight() / 20)), this.myPaint);
            canvas.drawText("Best: " + MainActivity.this.H_S, (float) ((this.disp.getHeight() / 20) * 7), (float) (this.disp.getHeight() - (this.disp.getHeight() / 20)), this.myPaint);
            invalidate();
        }

        public boolean onTouchEvent(MotionEvent event) {
            float xt = event.getX();
            float yt = event.getY();
           // switch (event.getAction()) {
                //case 0:
                    if (this.up.contains((int) xt, (int) yt) && !this.mdn) {
                        MainActivity.this.vibe.vibrate((long) 25);
                        this.mlf = false;
                        this.mrt = false;
                        this.mup = true;
                        //invalidate();
                        System.out.println("!!!");
                    } else if (this.down.contains((int) xt, (int) yt) && !this.mup) {
                        MainActivity.this.vibe.vibrate((long) 25);
                        this.mdn = true;
                        this.mlf = false;
                        this.mrt = false;
                        //invalidate();
                    } else if (this.lft.contains((int) xt, (int) yt) && !this.mrt) {
                        MainActivity.this.vibe.vibrate((long) 25);
                        this.mdn = false;
                        this.mlf = true;
                        this.mup = false;
                        //invalidate();
                    } else if (this.rght.contains((int) xt, (int) yt) && !this.mlf) {
                        MainActivity.this.vibe.vibrate((long) 25);
                        this.mdn = false;
                        this.mrt = true;
                        this.mup = false;
                        //invalidate();
                    }

            return true;
        }
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(new myView(this));
    }
}