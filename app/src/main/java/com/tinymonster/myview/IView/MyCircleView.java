package com.tinymonster.myview.IView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tinymonster.myview.R;


/**
 * Created by TinyMonster on 24/11/2018.
 */

public class MyCircleView extends View{
    private int circleColor;
    private int arcColor;
    private int textColor;
    private float textSize;
    private String text;
    private int startAngle;
    private int sweepAngle;
    private int mCircleXY;
    private float mRadius;
    private Paint mCirclePaint;
    private RectF mRecf;
    private Paint mArcPaint;
    private Paint mTextPaint;
    public MyCircleView(Context context) {
        this(context,null);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs,context);
    }
    private void initAttr(AttributeSet attrs,Context context){
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.MyArcView);
        if(ta!=null){
            circleColor=ta.getColor(R.styleable.MyArcView_circleColor,0);
            arcColor=ta.getColor(R.styleable.MyArcView_arcColor,0);
            textColor=ta.getColor(R.styleable.MyArcView_textColor,0);
            textSize=ta.getDimension(R.styleable.MyArcView_textSize,20);
            text=ta.getString(R.styleable.MyArcView_text);
            startAngle=ta.getInt(R.styleable.MyArcView_startAngle,0);
            sweepAngle=ta.getInt(R.styleable.MyArcView_sweepAngle,0);
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            width=dpToPx(getContext(),200);
        }
        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{
            height=dpToPx(getContext(),200);
        }
        int length=Math.min(width,height);
        mCircleXY=length/2;//圆形坐标
        mRadius=length*0.5f/2;
        mCirclePaint=new Paint(Paint.ANTI_ALIAS_FLAG);//绘制圆的paint
        mCirclePaint.setColor(circleColor);
        mRecf=new RectF(length*0.1f,length*0.1f,length*0.9f,length*0.9f);//矩形

        mArcPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(arcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(width*0.1f);

        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCirclePaint);
        canvas.drawArc(mRecf,startAngle,sweepAngle,false,mArcPaint);
        canvas.drawText(text,mCircleXY,mCircleXY+textSize/4,mTextPaint);
    }
    public void setText(String text){
        this.text=text;
        int temp_int= Integer.parseInt(text);
        sweepAngle=3*temp_int;
        invalidate();
    }
    public int dpToPx(Context context, float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
