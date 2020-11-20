package com.thousand.labfive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Bounce extends View {
    private List<Ball> balls = new ArrayList<>();

    public Bounce(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Bounce(Context context) {
        super(context);
        init();
    }
    private void init(){
        //Add a new ball to the view
        balls.add(new Ball(50,50,100, Color.RED));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw the balls
        for(Ball ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.rect,ball.paint);
        }
        invalidate(); // See note
    }
}
