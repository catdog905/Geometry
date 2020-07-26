package com.example.geometry;

import static com.example.geometry.LinearAlgebra.EPS;
import static com.example.geometry.LinearAlgebra.INF;

public class GaussMethod {
    public float[][] a;
    public int status;
    public int[] where_vars;

    public GaussMethod(float[][] a) {
        this.a = a;
    }

    public void gaussMethodSolve(float[][] a) {
        int n = (int) a.length;
        int m = (int) a[0].length - 1;

        int[] where = new int[m];
        for(int i = 0; i < where.length; i++){
            where[i] = -1;
        }
        for (int col=0, row=0; col<m && row<n; ++col) {
            int sel = row;
            for (int i=row; i<n; ++i)
                if (Math.abs(a[i][col]) > Math.abs(a[sel][col]))
                    sel = i;
            if (Math.abs(a[sel][col]) < EPS)
                continue;
            for (int i=col; i<=m; ++i) {
                float temp = a[sel][i];
                a[sel][i] = a[row][i];
                a[row][i] = temp;
            }
            where[col] = row;

            for (int i=0; i<n; ++i)
                if (i != row) {
                    double c = a[i][col] / a[row][col];
                    for (int j=col; j<=m; ++j)
                        a[i][j] -= a[row][j] * c;
                }
            ++row;
        }
        float[] ans = new float[m];
        for(int i = 0; i < ans.length; i++){
            ans[i] = 0;
        }
        for (int i=0; i<m; ++i)
            if (where[i] != -1)
                ans[i] = a[where[i]][m]/a[where[i]][i];
        for (int i=0; i<n; ++i) {
            double sum = 0;
            for (int j=0; j<m; ++j)
                sum += ans[j] * a[i][j];
            if (Math.abs(sum - a[i][m]) > EPS){
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
        return;
    }
}
