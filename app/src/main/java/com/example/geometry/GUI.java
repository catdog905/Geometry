package com.example.geometry;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GUI extends View {

    Figure figure;
    Paint mPaintNode;
    Paint mPaintLine;
    Line tempLine;
    Node startTempNode;
    Node stopTempNode;


    public GUI(Context context) {
        super(context);
        initPaintSettings(context);
        figure = new Figure();
    }

    // Перегруженный конструктор для копирования с изменением контекста
    public GUI(GUI gui, Context context) {
        super(context);
        this.figure = gui.figure;
        this.mPaintNode = gui.mPaintNode;
        this.mPaintLine = gui.mPaintLine;
        this.tempLine = gui.tempLine;
        this.startTempNode = gui.startTempNode;
        this.stopTempNode = gui.stopTempNode;
    }

    private void initPaintSettings(Context context) {
        mPaintNode = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNode.setStrokeWidth(10);
        mPaintNode.setColor(context.getColor(R.color.boldThemeColor));

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(5);
        mPaintLine.setColor(context.getColor(R.color.lightThemeColor));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(10, 0, 10, 100, mPaintLine);
        for (Line line : figure.lines) {
            canvas.drawLine(line.start.x, line.start.y, line.stop.x, line.stop.y, mPaintLine);
        }
        for (Node node : figure.nodes) {
            canvas.drawCircle(node.x, node.y, 10, mPaintNode);
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
