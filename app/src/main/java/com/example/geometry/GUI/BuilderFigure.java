package com.example.geometry.GUI;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.geometry.FigureModel.FigureUISingleton;
import com.example.geometry.R;

import java.util.Stack;

public class BuilderFigure extends View {


    private FigureUISingleton figureUISingleton = null;
    private InputHandler inputHandler;
    private Stack<StepInput> stepInputStack = new Stack<>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    public BuilderFigure(Context context) {
        super(context);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BuilderFigure(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BuilderFigure(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(Context context) {
        Paint mPaintNode = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNode.setStrokeWidth(10);
        mPaintNode.setColor(getContext().getColor(R.color.boldThemeColor));

        Paint mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(5);
        mPaintLine.setColor(getContext().getColor(R.color.lightThemeColor));


        figureUISingleton = FigureUISingleton.getInstance(mPaintLine, mPaintNode);
        inputHandler = new InputHandler(figureUISingleton);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        figureUISingleton.drawFigure(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        StepInput temp = inputHandler.catchTouch(event);
        if (temp != null)
            stepInputStack.push(temp);
        //Log.d("debug", Debuger.getStackTraceOfInputHandler(stepInputStack) + "1");
        invalidate();
        return true;
    }

    public FigureUISingleton getFigureUISingleton() {
        return figureUISingleton;
    }
}
