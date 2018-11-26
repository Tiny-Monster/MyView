package com.tinymonster.myview.IView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by TinyMonster on 22/11/2018.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView{
    Paint paint1;
    Paint paint2;
    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        paint1=new Paint();
        paint1.setColor(Color.BLUE);
        paint1.setStyle(Paint.Style.STROKE);
        paint2=new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.STROKE);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint1);//绘制最外侧矩形
        canvas.drawRect(20,20,getMeasuredWidth()-20,getMeasuredHeight()-20,paint2);
        canvas.save();
        super.onDraw(canvas);
        canvas.restore();
    }

}
