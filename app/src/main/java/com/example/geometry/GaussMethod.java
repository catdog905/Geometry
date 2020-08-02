package com.example.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.example.geometry.LinearAlgebra.EPS;
import static com.example.geometry.LinearAlgebra.INF;

public class GaussMethod {
    public Matrix<Float> matrix;
    public int status;
    public int[] where_vars;

    public GaussMethod(Matrix<Float> matrix) {
        this.matrix = matrix;
    }

    public int gaussMethodSolve() {

        int n = (int) matrix.matrix.size();
        int m = (int) matrix.matrix.get(0).size() - 1;

        double[][] a = new double[n][m+1];

        for (int i = 0; i < n; i++) {
            for (int j = 0 ; j < m + 1; j++) {
                a[i][j] = matrix.matrix.get(i).get(j);
            }
        }
        matrix.matrix.clear();

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
                double temp = a[sel][i];
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
        double[] ans = new double[m];
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
            if (Math.abs(sum - a[i][m]) < EPS){
                status = 1;
                where_vars = where;
                for(int k = 0; k < n; k++) {
                    ArrayList<Float> tempArr = new ArrayList<>();
                    for(int j = 0; j < m+1; j++) {
                        tempArr.add((float)a[k][j]);
                    }
                    matrix.matrix.add(tempArr);
                }
                return status;
            }
        }

        for (int i=0; i<m; ++i)
            if (where[i] == -1){
                status = INF;
                where_vars = where;
                for(int k = 0; k < n; k++) {
                    ArrayList<Float> tempArr = new ArrayList<>();
                    for(int j = 0; j < m+1; j++) {
                        tempArr.add((float)a[k][j]);
                    }
                    matrix.matrix.add(tempArr);
                }
                return status;
            }
        status = 0;
        where_vars = where;
        for(int i = 0; i < n; i++) {
            ArrayList<Float> tempArr = new ArrayList<>();
            for(int j = 0; j < m+1; j++) {
                tempArr.add((float)a[i][j]);
            }
            matrix.matrix.add(tempArr);
        }
        matrix.matrix = new ArrayList<ArrayList<Float>>();
        return status;
    }

    public ArrayList<Float> getResults() {
        if(status == 1) {
            ArrayList<Float> res = new ArrayList<>();
            int m = (int) matrix.matrix.get(0).size() - 1;
            for (int i=0; i<m; ++i)
                if (where_vars[i] != -1)
                    res.add(matrix.matrix.get(where_vars[i]).get(m)/matrix.matrix.get(where_vars[i]).get(i));
            return res;
        } else {
            return null;
        }
    }
}
