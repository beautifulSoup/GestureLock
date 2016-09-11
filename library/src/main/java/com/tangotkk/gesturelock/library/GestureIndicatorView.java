package com.tangotkk.gesturelock.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by kris on 16/9/3.
 * 指示器
 *
 */
public class GestureIndicatorView extends View {
    protected static final int DEFAULT_CIRCLE_RADIUS = 5;
    protected static final int DEFAULT_CIRCLE_STROKE_WIDTH = 1;

    protected static final int ROW_COUNT = 3;
    protected static final int COLUMN_COUNT = 3;

    @ColorInt
    int mColorSelected;

    @ColorInt
    int mColorUnselected;

    int mCircleRadius;

    int mCircleStrokeWidth;

    int mSquareWidth;
    int mSquareHeight;

    Paint mSelectedPaint = new Paint();
    Paint mUnselectedPaint = new Paint();

    boolean [][] mCells = new boolean[ROW_COUNT][COLUMN_COUNT];


    public GestureIndicatorView(Context context) {
        this(context, null);
    }

    public GestureIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GestureIndicatorView);

        mColorSelected = ta.getColor(R.styleable.GestureIndicatorView_color_selected, Color.GREEN);
        mColorUnselected = ta.getColor(R.styleable.GestureIndicatorView_color_unselected, Color.WHITE);

        mCircleRadius = ta.getDimensionPixelSize(R.styleable.GestureIndicatorView_indicator_circle_radius, DEFAULT_CIRCLE_RADIUS);
        mCircleStrokeWidth = ta.getDimensionPixelSize(R.styleable.GestureIndicatorView_circle_stroke_width, DEFAULT_CIRCLE_STROKE_WIDTH);

        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setStyle(Paint.Style.FILL);
        mSelectedPaint.setColor(mColorSelected);
        mSelectedPaint.setStrokeWidth(mCircleStrokeWidth);

        mUnselectedPaint.setAntiAlias(true);
        mUnselectedPaint.setStyle(Paint.Style.STROKE);
        mUnselectedPaint.setColor(mColorUnselected);
    }

    public void setPattern(List<LockPatternView.Cell> cells){
        clear();
        for(LockPatternView.Cell cell : cells){
            mCells[cell.row][cell.column] = true;
        }

        invalidate();
    }


    public void clear(){
        for(int i=0;i<ROW_COUNT;i++){
            for(int j=0;j<COLUMN_COUNT;j++){
                mCells[i][j] = false;
            }
        }

        invalidate();
    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();


        int width = resolveMeasured(widthMeasureSpec, minimumWidth);
        int height = resolveMeasured(heightMeasureSpec, minimumHeight);
        setMeasuredDimension(width, height);

    }

    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.max(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }


    protected int getSuggestedMinimumWidth(){
        return 3 * mCircleRadius;
    }

    protected int getSuggestedMinimumHeight(){
        return 3 * mCircleRadius;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<ROW_COUNT;i++){
            for(int j=0;j<COLUMN_COUNT;j++){
                int x = getPaddingLeft() + j * mSquareWidth;
                int y = getPaddingTop() + i * mSquareHeight;
                drawableCircle(canvas, mCells[i][j], x, y);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        final int width = w - getPaddingLeft() - getPaddingRight();
        mSquareWidth = (int)(width / COLUMN_COUNT);

        final int height = h - getPaddingTop() - getPaddingBottom();
        mSquareHeight = (int)(height / ROW_COUNT);
    }


    protected void drawableCircle(Canvas canvas, boolean isSelected, int leftX, int topY){

        int radius = Math.min(mSquareHeight /2 , mCircleRadius);

        int centerX = leftX + mSquareWidth / 2;
        int centerY = topY + mSquareHeight / 2;

        if(isSelected){
            canvas.drawCircle(centerX, centerY, mCircleRadius, mSelectedPaint);
        } else {
            canvas.drawCircle(centerX, centerY, mCircleRadius, mUnselectedPaint);
        }


    }


}
