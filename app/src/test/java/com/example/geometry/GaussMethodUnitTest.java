package com.example.geometry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.geometry.LinearAlgebra.EPS;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GaussMethodUnitTest {
    private ArrayList<Matrix<Float>> testData = new ArrayList<>();
    private ArrayList<ArrayList<Float>> testDataRes = new ArrayList<>();

    @Before
    public void prepareTestData(){
        ArrayList<ArrayList<Float>> test = new ArrayList<>();
        test.add(new ArrayList<>(Arrays.asList(4f,2f,-1f,1f)));
        test.add(new ArrayList<>(Arrays.asList(5f,3f,-2f,2f)));
        test.add(new ArrayList<>(Arrays.asList(3f,2f,-3f,0f)));
        testData.add(new Matrix<Float>(test));
        testDataRes.add(new ArrayList<>(Arrays.asList(-1f,3f,1f)));

        test = new ArrayList<>();
        test.add(new ArrayList<>(Arrays.asList(2f,5f,4f,1f,20f)));
        test.add(new ArrayList<>(Arrays.asList(1f,3f,2f,1f,11f)));
        test.add(new ArrayList<>(Arrays.asList(2f,10f,9f,7f,40f)));
        test.add(new ArrayList<>(Arrays.asList(3f,8f,9f,2f,37f)));
        testData.add(new Matrix<Float>(test));
        testDataRes.add(new ArrayList<>(Arrays.asList(1f,2f,2f,0f)));
    } //TODO: add tests on INF values

    @Test
    public void testGaussMethod() {
        for (int i = 0; i < testData.size(); i++) {
            GaussMethod gaussMethod = new GaussMethod(testData.get(i));
            assertEquals(gaussMethod.gaussMethodSolve(), 1);
            assertArrayEquals(gaussMethod.getResults().stream().mapToDouble(d -> d).toArray(), testDataRes.get(i).stream().mapToDouble(d -> d).toArray(), EPS);
        }
    }
}
