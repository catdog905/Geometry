package com.example.geometry.FigureModel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class GUIObjTitle {
    public String text;
    PointF pos;
    float radius;

    public GUIObjTitle(String text, PointF pos, float radius) {
        this.text = text;
        this.pos = pos;
        this.radius = radius;
    }

    public void drawTitle(Canvas canvas, Paint paint) {
        canvas.drawText(text, pos.x - paint.measureText(text) / 2, pos.y, paint);
    }
}
