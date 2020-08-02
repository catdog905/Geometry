package com.example.geometry;

import java.util.ArrayList;

import static com.example.geometry.LinearAlgebra.EPS;
import static com.example.geometry.LinearAlgebra.INF;

public class GaussMethod {
    public Matrix<Float> matrix;
    public int status;
    public int[] where_vars;

    public GaussMethod(Matrix<Float> matrix) {
        this.matrix = matrix;
    }

    public void gaussMethodSolve() {

        ArrayList<ArrayList<Float>> a = matrix.matrix;
        int n = (int) a.size();
        int m = (int) a.get(0).size() - 1;

        int[] where = new int[m];
        for(int i = 0; i < where.length; i++){
            where[i] = -1;
        }
        for (int col=0, row=0; col<m && row<n; ++col) {
            int sel = row;
            for (int i=row; i<n; ++i)
                if (Math.abs(a.get(i).get(col)) > Math.abs(a.get(sel).get(col)))
                    sel = i;
            if (Math.abs(a.get(sel).get(col)) < EPS)
                continue;
            for (int i=col; i<=m; ++i) {
                float temp = a.get(sel).get(i);
                a.get(sel).set(i, a.get(row).get(i));
                a.get(row).set(i, temp);
            }
            where[col] = row;

            for (int i=0; i<n; ++i)
                if (i != row) {
                    float c = a.get(i).get(col) / a.get(row).get(col);
                    for (int j=col; j<=m; ++j)
                        a.get(i).set(j, a.get(row).get(j) * c);
                }
            ++row;
        }
        float[] ans = new float[m];
        for(int i = 0; i < ans.length; i++){
            ans[i] = 0;
        }
        for (int i=0; i<m; ++i)
            if (where[i] != -1)
                ans[i] = a.get(where[i]).get(m)/a.get(where[i]).get(i);
        for (int i=0; i<n; ++i) {
            double sum = 0;
            for (int j=0; j<m; ++j)
                sum += ans[j] * a.get(i).get(j);
            if (Math.abs(sum - a.get(i).get(m)) > EPS){
                status = 1;
                where_vars = where;
                return;
            }
        }

        for (int i=0; i<m; ++i)
            if (where[i] == -1){
                status = INF;
                where_vars = where;
                return;
            }
        status = 0;
        where_vars = where;
    }
}
