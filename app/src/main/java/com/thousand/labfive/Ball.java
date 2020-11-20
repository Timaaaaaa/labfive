package com.thousand.labfive;

import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Rect;

import android.graphics.RectF;

import android.graphics.Paint;

public class Ball{

    public Paint paint;
    public Paint paintR;
    public RectF rect;

    public Ball(int wallX, int wallY, int ballSize, int color){
        this.wallX = wallX;
        this.wallY = wallY;
        this.ballSize = ballSize;
        this.paint = new Paint();
        this.paint.setColor(color);

    }

    public void move(Canvas canvas) {
        editRect();
        Rect bounds = new Rect();
        paintR  = new Paint();
        paintR.setColor(Color.YELLOW);
        canvas.drawRect(bounds, paint);
        canvas.drawOval(rect, paintR);
        this.rect.roundOut(bounds);
        BounceByTheWall(canvas, bounds);
    }

    public void directMinusX(){
        direction[0] = direction[0]*-1;
    }
    public void directMinusY(){
        direction[1] = direction[1]*-1;
    }
    public int[] direction = new int[]{1,1};
    public int wallX,wallY,ballSize;
    public void editRect(){
        this.wallX += speed*direction[0];
        this.wallY += speed*direction[1];
        this.rect = new RectF(wallX-ballSize/2,wallY-ballSize/2,wallX+ballSize/2,wallY+ballSize/2);
    }

    public void BounceByTheWall(Canvas canvas, Rect bounds){
        if(!canvas.getClipBounds().contains(bounds)){
            if(this.wallX-ballSize<0 || this.wallX+ballSize > canvas.getWidth()){
                directMinusX();
            }
            if(this.wallY-ballSize<0 || this.wallY+ballSize > canvas.getHeight()){
                directMinusY();
            }
        }
    }


    public int speed = 10;
}