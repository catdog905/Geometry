package com.example.geometry.FigureModel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class GUIObjTitle<T> {
    public String text;
    PointF pos;
    float radius;
    private T host;

    public GUIObjTitle(String text, PointF pos, float radius, T host) {
        this.text = text;
        this.pos = pos;
        this.radius = radius;
        this.host = host;
    }

    public void drawTitle(Canvas canvas, Paint paint) {
        canvas.drawText(text, pos.x - paint.measureText(text) / 2, pos.y, paint);
    }

    public void addChar(char ch) {
        if (text == null)
            text = new String();
        text += ch;
    }

    public void removeLastChar() {
        if (text.length() > 0)
            text = text.substring(0, text.length() - 1);
    }

    public void setHostValue(float value) {
        if (host instanceof Line)
            ((Line) host).setValue(value);
        if (host instanceof Angle)
            ((Angle) host).valDeg = value;
    }
}
