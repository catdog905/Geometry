package com.example.geometry;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public class Line {
    Node start;
    Node stop;
    float value;
    int[] adjacentLines;
    float[] adjacentLinesAngel;
    Path mPath;
    Region r;

    public Line(Node start, Node stop) {
        this.start = start;
        this.stop = stop;
        r = createPathFromLine();

    }

    public void setStop(Node stop) {
        this.stop = stop;
        r = createPathFromLine();
    }

    public Region createPathFromLine() {
        //n(A;B) - вектор нормали к прямой
        float A = 1/(stop.x-start.x);
        float B = -1/(stop.y-start.y);
        //Обход особых случаев
        if (stop.x==start.x) A = 1;
        if (stop.y==start.y) B = 1;
        double len = Math.sqrt(A*A+B*B);
        //e(eA;eB) - еденичный вектор
        float eA = A/(float)len;
        float eB = B/(float)len;
        int d = 25;
        Path p = new Path();
        p.moveTo((int)(start.x+d*(-1)*eA), (int)(start.y+d*(-1)*eB));
        p.lineTo((int)(start.x+d*eA), (int)(start.y+d*eB));
        p.lineTo((int)(stop.x+d*eA), (int)(stop.y+d*eB));
        p.lineTo((int)(stop.x+d*(-1)*eA), (int)(stop.y+d*(-1)*eB));
        p.close();

        RectF rectF = new RectF();
        p.computeBounds(rectF, true);
        r = new Region();
        r.setPath(p, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return r;
    }


}
