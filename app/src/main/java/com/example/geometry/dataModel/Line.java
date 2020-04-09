package com.example.geometry;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

public class Line extends Object{
    Node start;
    Node stop;
    float value;
    Path mPath;
    Region r = new Region();
    float A, B, C;

    public Line(Node start, Node stop) {
        this.start = start;
        this.stop = stop;
        r = createPathFromLine();
    }

    public void setStop(Node stop) {
        this.stop = stop;
        r = createPathFromLine();
        //Log.d("hello", ""+start.x);
    }

    public void setXYNodes(float startX, float startY, float stopX, float stopY) {
        start.setXY(startX, startY);
        stop.setXY(stopX, stopY);
        r = createPathFromLine();
    }

    public Region createPathFromLine() {
        LinearFunc(start.x, start.y, stop.x, stop.y);
        //Обход особых случаев
        if (stop.x==start.x) A = 1;
        if (stop.y==start.y) B = 1;
        double len = Math.sqrt(A*A+B*B);
        //e(eA;eB) - еденичный вектор
        float eA = A/(float)len;
        float eB = B/(float)len;
        Path p = new Path();
        p.moveTo((int)(start.x+GUI.delta*(-1)*eA), (int)(start.y+GUI.delta*(-1)*eB));
        p.lineTo((int)(start.x+GUI.delta*eA), (int)(start.y+GUI.delta*eB));
        p.lineTo((int)(stop.x+GUI.delta*eA), (int)(stop.y+GUI.delta*eB));
        p.lineTo((int)(stop.x+GUI.delta*(-1)*eA), (int)(stop.y+GUI.delta*(-1)*eB));
        p.close();
        mPath = p;
        RectF rectF = new RectF();
        p.computeBounds(rectF, true);
        r.setPath(p, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return r;
    }

    private void LinearFunc(float x1, float y1, float x2, float y2) {
        A = 1/(x2-x1);
        B = 1/(y1-y2);
        C =y1/(y2-y1) - x1/(x2-x1);
    }


    @Override
    public void moving() {

    }
}
