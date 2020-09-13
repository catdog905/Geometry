package com.example.geometry;

import android.graphics.Paint;

import com.example.geometry.FigureModel.FigureUISingleton;
import com.example.geometry.FigureModel.Line;
import com.example.geometry.FigureModel.Node;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.geometry.LinearAlgebra.EPS;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpertSystemUnitTest {
    private ArrayList<ExpertSystem> testData = new ArrayList<>();
    private ArrayList<ArrayList<Fact>> testDataRes = new ArrayList<>();

    @Before
    public void prepareTestData(){
        FigureUISingleton figureUISingleton = FigureUISingleton.getInstance(new Paint(), new Paint(), new Paint());
        Node nodeA = new Node(0, 0);
        Node nodeB = new Node(0, 1);
        Node nodeC = new Node(1, 0);
        figureUISingleton.nodes = new ArrayList<>(Arrays.asList(nodeA, nodeB, nodeC));
        Line lineAB = new Line(nodeA, nodeB);
        Line lineAC = new Line(nodeA, nodeC);
        Line lineBC = new Line(nodeB, nodeC);
        figureUISingleton.lines = new ArrayList<>(Arrays.asList(lineAB, lineAC, lineBC));
        MathModel mathModel = new MathModel(figureUISingleton);
        ExpertSystem expertSystemTest = new ExpertSystem(mathModel);
        testData.add(expertSystemTest);
        testDataRes.add(new ArrayList<>(Arrays.asList(new Fact("A(belong)AB"), new Fact("A(belong)AC"),
                new Fact("B(belong)AB"), new Fact("B(belong)BC"),
                new Fact("C(belong)AC"), new Fact("C(belong)BC"))));
    }

    @Test
    public void testAddNewFactsMethod() {
        for (int i = 0; i < testData.size(); i++) {
            testData.get(i).addNewFactsFromExist();
            ArrayList<Fact> out = testData.get(i).getFactsFromMathModel();
            for (Fact checkFact:testDataRes.get(i)) {
                boolean checkTrue = false;
                for (Fact outFact:out) {
                    if (checkFact.statements.equals(outFact.statements)){
                        checkTrue = true;
                        break;
                    }
                }
                assertTrue(checkTrue);
            }
        }
    }
}
