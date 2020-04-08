package com.example.geometry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class DrawFigure extends View {
    Figure figure;
    private Paint mPaintNode;
    private Paint mPaintLine;
    boolean move;

    public DrawFigure(Context context) {
        super(context);
        figure = new Figure();

        mPaintNode = new Paint();
        mPaintNode.setStrokeWidth(10);
        mPaintNode.setColor(context.getColor(R.color.textColor));

        mPaintLine = new Paint();
        mPaintLine.setStrokeWidth(5);
        mPaintLine.setColor(context.getColor(R.color.lightThemeColor));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (figure.getNodes().size() > 1) {
            ArrayList<Node> nodes = figure.getNodes();
            for (int i = 0; i < nodes.size() - 1; i++) {
                canvas.drawLine(nodes.get(i).getX(), nodes.get(i).getY(),
                        nodes.get(i + 1).getX(), nodes.get(i + 1).getY(), mPaintLine);
            }
        }
        for (Node node : figure.getNodes()) {
            canvas.drawCircle(node.getX(), node.getY(), 20, mPaintNode);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mx = event.getX();
        float my = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (Node node : figure.getNodes()) {
                    node.containsRadius(mx, my);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                for (Node node : figure.getNodes()) {
                    if (node.isMove())
                        node.moveNode(mx, my);
                }
                break;

            case MotionEvent.ACTION_UP:
                figure.addNode(new Node(mx, my));
                figure.stopAllNode();
                break;
        }
        invalidate();
        return true;
    }
}
