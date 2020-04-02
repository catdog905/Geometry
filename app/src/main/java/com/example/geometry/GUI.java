package com.example.geometry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GUI extends View {

    private Figure figure;
    private Paint mPaint;
    Line tempLine;

    public GUI(Context context) {
        super(context);
        initPaintSettings();
        figure = new Figure();
    }

    private void initPaintSettings(){
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Line line:figure.lines) {
            canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Получаем точку касания
        float mx = event.getX();
        float my = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (true) { //TODO: We are touching free area or vertex of an angle
                    tempLine = new Line(figure.lines.size(),mx, my, mx, my);
                }
                invalidate();

            case MotionEvent.ACTION_MOVE:
                tempLine.setStop(mx, my);
                invalidate();

            case MotionEvent.ACTION_UP:
                tempLine.setStop(mx, my);
                figure.lines.add(tempLine);
                invalidate();
        }
        return true;
    }

}
