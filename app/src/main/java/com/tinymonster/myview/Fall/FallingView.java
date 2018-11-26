package com.tinymonster.myview.Fall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.tinymonster.myview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TinyMonster on 24/11/2018.
 */

public class FallingView extends View{
    private Context mContext;
    private AttributeSet mAttrs;

    private int viewWidth;
    private int viewHeight;

    private static final int defaultWidth=600;//默认宽度
    private static final int defaultHeight=1000;//默认高度
    private static final int intervalTime=5;//重绘间隔时间

    private Paint testPaint;//绘制雪花的paint
    private int snowY;//雪花Y坐标

    private List<FallObject> fallObjects;
    public FallingView(Context context) {
        this(context,null);
    }

    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        mAttrs=attrs;
        init();
    }

    private void init(){
        testPaint=new Paint();
        testPaint.setColor(Color.WHITE);
        testPaint.setStyle(Paint.Style.FILL);
//        snowY=0;
        fallObjects=new ArrayList<>();
    }

    /**
     * 测量宽高 复制给viewWidth  viewHeight
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height=measureSize(defaultHeight,heightMeasureSpec);
        int width=measureSize(defaultWidth,widthMeasureSpec);
        setMeasuredDimension(defaultWidth,defaultHeight);
        viewWidth=width;
        viewHeight=height;
    }

    private int measureSize(int defaultSize,int measureSpec){
        int result=defaultSize;
        int mode=MeasureSpec.getMode(measureSpec);
        int size=MeasureSpec.getSize(measureSpec);
        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else if(mode==MeasureSpec.AT_MOST){
            result=Math.min(size,defaultSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(getResources().getColor(R.color.colorBule));
        if(fallObjects.size()>0){
            for(int i=0;i<fallObjects.size();i++){
                fallObjects.get(i).drawObject(canvas);//初始化画上好多个
            }
        }
        getHandler().postDelayed(runnable,intervalTime);
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    /**
     * 制图绘制前会被调用
     * @param fallObject
     * @param num
     */
    public void addFallObject(final FallObject fallObject,final int num){
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                for(int i=0;i<num;i++){
                    FallObject newFallObject=new FallObject(fallObject.builder,viewWidth,viewHeight);
                    fallObjects.add(newFallObject);
                }
                invalidate();
                return true;
            }
        });
    }
}
