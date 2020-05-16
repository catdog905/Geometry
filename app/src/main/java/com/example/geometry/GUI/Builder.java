package com.example.geometry.GUI;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.geometry.Debuger;
import com.example.geometry.LinearAlgebra;
import com.example.geometry.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Builder extends View {


    private FigureUI figureUI;
    InputHandler inputHandler;
    public ArrayList<ArrayList<String>> global_facts = new ArrayList<>();
    Stack<StepInput> stepInputStack = new Stack<>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Builder(Context context) {
        super(context);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Builder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Builder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {
        Paint mPaintNode = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNode.setStrokeWidth(10);
        mPaintNode.setColor(getContext().getColor(R.color.boldThemeColor));

        Paint mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(5);
        mPaintLine.setColor(getContext().getColor(R.color.lightThemeColor));

        figureUI = new FigureUI(mPaintLine, mPaintNode);
        inputHandler = new InputHandler(figureUI);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        figureUI.drawFigure(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        StepInput temp = inputHandler.catchTouch(event);
        if (temp != null)
            stepInputStack.push(temp);
        invalidate();
        return true;
    }

    
}
