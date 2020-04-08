package com.example.geometry.ruca;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class GUI extends View {

    private Figure figure;
    private Paint mPaint;
    Line tempLine;
    Node startTempNode;
    Node stopTempNode;


    public GUI(Context context) {
        super(context);
        initPaintSettings();
        figure = new Figure();
    }

    private void initPaintSettings(){
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(10, 0, 10, 100, mPaint);
        for (Line line:figure.lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaint);
        }
        for (Node node:figure.nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Получаем точку касания
        float mx = event.getX();
        float my = event.getY();

        for (Line line : figure.lines) {
            if (line.r.contains((int) mx, (int) my)) {
                Log.d("hello", "hello");
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startTempNode = new Node(mx, my);
                stopTempNode = new Node(mx, my);
                tempLine = new Line(startTempNode, stopTempNode);
                figure.nodes.add(startTempNode);
                figure.nodes.add(stopTempNode);
                invalidate();

            case MotionEvent.ACTION_MOVE:
                stopTempNode.setXY(mx, my);
                tempLine.setStop(stopTempNode);
                invalidate();

            case MotionEvent.ACTION_UP:
                stopTempNode.setXY(mx, my);
                tempLine.setStop(stopTempNode);
                figure.lines.add(tempLine);
                invalidate();
        }
        return true;
    }

}
