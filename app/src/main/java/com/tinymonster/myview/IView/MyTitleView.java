package com.tinymonster.myview.IView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tinymonster.myview.R;

/**
 * Created by TinyMonster on 23/11/2018.
 */

public class MyTitleView extends RelativeLayout implements View.OnClickListener{
    private String titleText;
    private float titleTextSize;
    private int titleTextColor;
    private String leftText;
    private Drawable leftBackground;
    private int leftTextColor;
    private String rightText;
    private Drawable rightBackground;
    private int rightTextColor;

    private TextView title;
    private Button leftButton;
    private Button rightButton;

    private LayoutParams mCenterLayoutParams ;
    private LayoutParams mLeftLayoutParams;
    private LayoutParams mRightLayoutParams ;

    public static final int LEFT_BUTTON=0;
    public static final int RIGHT_BUTTON=1;

    private ButtonClickListener listener;
    public MyTitleView(Context context) {
        this(context,null);
    }

    public MyTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initView();
        initLayout();
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
    }

    private void initAttr(AttributeSet attrs){
        TypedArray ta=this.getContext().obtainStyledAttributes(attrs, R.styleable.titleBar);//
        if(ta!=null){
            titleText=ta.getString(R.styleable.titleBar_titleText);
            titleTextColor=ta.getColor(R.styleable.titleBar_titleTextColor,0);
            titleTextSize=ta.getDimension(R.styleable.titleBar_titleTextSize,16);

            leftText=ta.getString(R.styleable.titleBar_leftText);
            leftBackground=ta.getDrawable(R.styleable.titleBar_leftTextBackground);
            leftTextColor=ta.getColor(R.styleable.titleBar_leftTextColor,0);

            rightText=ta.getString(R.styleable.titleBar_rightText);
            rightBackground=ta.getDrawable(R.styleable.titleBar_rightTextBackground);
            rightTextColor=ta.getColor(R.styleable.titleBar_rightTextColor,0);
            ta.recycle();
        }
    }
    private void initView(){
        title=new TextView(getContext());
        leftButton=new Button(getContext());
        rightButton=new Button(getContext());

        title.setText(titleText);
        title.setTextSize(titleTextSize);
        title.setTextColor(titleTextColor);

        leftButton.setText(leftText);
        leftButton.setBackgroundDrawable(leftBackground);
        leftButton.setTextColor(leftTextColor);

        rightButton.setText(rightText);
        rightButton.setBackgroundDrawable(rightBackground);
        rightButton.setTextColor(rightTextColor);
    }
    private void initLayout(){
        mCenterLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);//width  height
        mCenterLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(title,mCenterLayoutParams);

        mLeftLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(leftButton,mLeftLayoutParams);

        mRightLayoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(rightButton,mRightLayoutParams);
    }

    @Override
    public void onClick(View view) {
        if(view==leftButton){
            listener.onClick(LEFT_BUTTON);
        }else if(view==rightButton){
            listener.onClick(RIGHT_BUTTON);
        }
    }

    public void addListener(ButtonClickListener listener){
        this.listener=listener;
    }
    public interface ButtonClickListener{
        void onClick(int TAG);
    }
}
