package com.absjbd.bubbledrawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by abs pc1 on 2017-09-08.
 */

public class BubbleView extends ImageView implements View.OnTouchListener{

    private ArrayList<Bubble> bubbleList;
    private final int DELAY = 16;
    private Paint myPaint = new Paint();
    private Handler handler;

    public BubbleView(Context context, AttributeSet attrs){
        super(context, attrs);

        bubbleList = new ArrayList<Bubble>();
        myPaint.setColor(Color.WHITE);
        handler = new Handler();

        this.setOnTouchListener(this);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // update all the bubbles
            for(Bubble bubble: bubbleList){
                bubble.update();
            }
            // redraw the screen
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas){

        for(Bubble bubble:bubbleList){
            myPaint.setColor(bubble.color);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawOval(bubble.x - bubble.size/2,
                        bubble.y - bubble.size/2,
                        bubble.x + bubble.size/2,
                        bubble.y + bubble.size/2, myPaint);
            }else{
                RectF oval = new RectF(bubble.x - bubble.size/2,
                        bubble.y - bubble.size/2,
                        bubble.x + bubble.size/2,
                        bubble.y + bubble.size/2);
                canvas.drawOval(oval,myPaint);
            }
        }

        myPaint.setColor(Color.WHITE);
        myPaint.setTextSize(15);
        canvas.drawText(""+bubbleList.size(),40,40,myPaint);

        handler.postDelayed(runnable, DELAY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // handle multi-touch events
        if(event.getPointerCount() > 1) { // for multiple finger
            for (int n = 0; n < event.getPointerCount(); n++) {
                bubbleList.add(new Bubble((int) event.getX(n),
                        (int) event.getY(n),
                        (int) (Math.random() * 50 + 50)));
            }
        }else{  // for single finger
            bubbleList.add(new Bubble((int) event.getX(),
                    (int) event.getY(),
                    (int) (Math.random() * 50 + 50)));
            if(bubbleList.size() > 1){
                // set xspeed and yspeed of the bubbles to the previous speed
                bubbleList.get(bubbleList.size() - 1).xspeed =
                        bubbleList.get(bubbleList.size() - 2).xspeed;

                bubbleList.get(bubbleList.size() - 1).yspeed =
                        bubbleList.get(bubbleList.size() - 2).yspeed;
            }
        }
            return true;
        }

    private class Bubble{
        /*Bubble properties*/
        public int x;
        public int y;
        public int size;
        public int bubbleOrRectangle = 1; // 0 for bubble 1 for rectangle
        public int color;
        public int xspeed;
        public int yspeed;
        private final int MAX_SPEED = 15;

        public Bubble(int x, int y, int size, int bubbleRectangle) {
            this.x = x;
            this.y = y;
            bubbleOrRectangle = bubbleRectangle;
            this.size = size;
            color = Color.argb( (int) (Math.random()*256),
                                (int) (Math.random()*256),
                                (int) (Math.random()*256),
                                (int) (Math.random()*256));
            xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);

            if(xspeed == 0 && yspeed == 0){
                xspeed = 2;
                yspeed = 2;
            }

        }

        public Bubble(int x, int y, int size) {
            this.x = x;
            this.y = y;
            bubbleOrRectangle = 0;
            this.size = size;
            color = Color.argb( (int) (Math.random()*256),
                    (int) (Math.random()*256),
                    (int) (Math.random()*256),
                    (int) (Math.random()*256));
            xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);

            if(xspeed == 0 && yspeed == 0){
                xspeed = 2;
                yspeed = 2;
            }

        }

        public void update(){
            //y -= 5; //float each bubble up 5 pixels per frame...
            x += xspeed;
            y += yspeed;

            // collision detection with the edges of the View
            if(x < size / 2 || x + size / 2 >= getWidth() | y < size / 2 || y + size / 2 >= getHeight()){
                xspeed = -1*xspeed;
                yspeed = -1*yspeed;
            }

        }


    }

}
