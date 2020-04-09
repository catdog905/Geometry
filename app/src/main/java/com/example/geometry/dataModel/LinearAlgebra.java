package com.example.geometry.dataModel;

public class LinearAlgebra {

    final static double EPS = 1E-9;

    public LinearAlgebra() { }

    public static void findLineFrom2Nodes(Node start, Node stop, Line line) {
        line.A = 1/(stop.x-start.x);
        line.B = 1/(start.y-stop.y);
        line.C = start.y/(stop.y-start.y) - start.x/(stop.x-start.x);
    }

    public static float intersectionNodeCirce(Node node, Circle circle){
        float len = (float)(Math.pow((node.x - circle.Ox), 2) + Math.pow((node.y - circle.Oy), 2));
        if (len <= Math.pow(circle.R, 2))
            return (float) Math.sqrt(Math.pow((node.x - circle.Ox), 2) + Math.pow((node.y - circle.Oy), 2));
        else
            return 1000*1000*1000;
    }

    public static float triangleArea (Node node1, Node node2 ,Node node3) {
        return (node2.x - node1.x) * (node3.y - node1.y) - (node2.y - node1.y) * (node3.x - node1.x);
    }

    public static boolean projectionsIntersect (float a, float b, float c, float d) {
        if (a > b)  {
            float temp = a;
            a = b;
            b = temp;
        }
        if (c > d)  {
            float temp = c;
            c = d;
            d = temp;
        }
        return Math.max(a,c) <= Math.min(b,d);
    }

    public static float det (float a, float b, float c, float d) {
        return a * d - b * c;
    }

    public static boolean between (float a, float b, float c) {
        return Math.min(a,b) <= c + EPS && c <= Math.max(a,b) + EPS;
    }

    public static Node intersect (Line line1, Line line2) {
        Node a = line1.start;
        Node b = line1.stop;
        Node c = line2.start;
        Node d = line2.stop;
        float A1 = a.y-b.y,  B1 = b.x-a.x,  C1 = -A1*a.x - B1*a.y;
        float A2 = c.y-d.y,  B2 = d.x-c.x,  C2 = -A2*c.x - B2*c.y;
        float zn = det (A1, B1, A2, B2);
        boolean res = false;
        float x = 0;
        float y = 0;
        if (zn != 0) {
            x = -det(C1, B1, C2, B2) * 1 / zn;
            y = -det(A1, C1, A2, C2) * 1 / zn;
            res = between (a.x, b.x, x) && between (a.y, b.y, y)
                    && between (c.x, d.x, x) && between (c.y, d.y, y);
        }
        else
            res = det (A1, C1, A2, C2) == 0 && det (B1, C1, B2, C2) == 0
                    && projectionsIntersect(a.x, b.x, c.x, d.x)
                    && projectionsIntersect(a.y, b.y, c.y, d.y);
        if (res){
            return new Node(x, y);
        } else {
            return new Node();
        }
    }

    public static class Distance{
        public float dist;
        public Node node;

        public Distance() { }

        public Distance(float dist, Node node) {
            this.dist = dist;
            this.node = node;
        }
    }

    public static Distance findDistanceToLine(Line line, float mx, float my) {
        float a = line.A;
        float b = line.B;
        float c = line.C;
        float distance = (float) (Math.abs(a*mx+b*my+c)/Math.sqrt(a*a + b*b));
        float x = (b*(b*mx - a*my) - a*c)/(a*a + b*b);
        float y = (a*(-b*mx+a*my) - b*c)/(a*a + b*b);
        if (between(line.start.x, line.stop.x, x) && between (line.start.y, line.stop.y, y))
            return new Distance(distance, new Node(x, y));
        else
            return new Distance();
    }
}
