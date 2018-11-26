package com.tinymonster.myview.Fall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.Random;

/**
 * Created by TinyMonster on 24/11/2018.
 */

public class FallObject {
    private int initX;
    private int initY;
    private Random random;
    private int parentWidth;//父容器宽度
    private int parentHeight;//父容器高度
    private float objectHeight;//物体高度
    private float objectWidth;//物体宽度

    public int initSpeed;//初始下落速度

    public float presentX;//当前位置X坐标
    public float presentY;//当前位置Y坐标
    public float presentSpeed;//当前下降速度

    private Bitmap bitmap;//物体形状
    public Builder builder;

    public boolean isSpeedRandom;
    public boolean isSizeRandom;

    public int initWindLevel;//初始风力
    public float angle;//物体下落角度
    private boolean isWindRandom;//物体初始风向和风力大小比例是否随机
    private boolean isWindChange;//物体下落过程中风向和风力是否随机变化

    private static final int defaultWindLevel=0;
    private static final int defaultWindSpeed=10;
    private static final float HALF_PI=(float) Math.PI/2;

    private static final int defaultSpeed=5;

    /**
     * 初始化随机坐标 初始化当前坐标 初始化速度  初始化物体宽度和高度  初始化bitmap
     * @param builder
     * @param parentWidth
     * @param parentHeight
     */
    public FallObject(Builder builder,int parentWidth,int parentHeight){
        random=new Random();
        this.parentWidth=parentWidth;
        this.parentHeight=parentHeight;

        this.builder=builder;

        initX=random.nextInt(parentWidth);//随机初始化坐标
        initY=random.nextInt(parentHeight);

        presentX=initX;//当前坐标
        presentY=initY;

        initSpeed=builder.initSpeed;//初始化速度

        presentSpeed=initSpeed;//初始化当前速度
        bitmap=builder.bitmap;

        isSpeedRandom=builder.isSpeedRandom;
        isSizeRandom=builder.isSizeRandom;

        isWindRandom=builder.isWindRandom;
        isWindChange=builder.isWindChange;

        initWindLevel=builder.initWindLevel;
        randomSpeed();
        randomSize();

    }
    private FallObject(Builder builder){
        this.builder=builder;
        initSpeed=builder.initSpeed;
        bitmap=builder.bitmap;
        isSpeedRandom=builder.isSpeedRandom;
        isSizeRandom=builder.isSizeRandom;
    }
    //封装speed 和 bitmap的builder
    public static final class Builder{
        private int initSpeed;
        private Bitmap bitmap;
        private boolean isSpeedRandom;
        private boolean isSizeRandom;
        private boolean isWindRandom;
        private boolean isWindChange;
        private int initWindLevel;
        public Builder(Bitmap bitmap){
            this.initSpeed=defaultSpeed;
            this.bitmap=bitmap;
            isSizeRandom=false;
            isSpeedRandom=false;
            isWindChange=false;
            isWindRandom=false;
        }
        public Builder(Drawable drawable){
            this.initSpeed=defaultSpeed;
            this.bitmap=drawableToBitmap(drawable);
            isSpeedRandom=false;
            isSizeRandom=false;
            isWindChange=false;
            isWindRandom=false;
        }

        public Builder setSpeed(int speed){
            this.initSpeed=speed;
            return this;
        }

        public Builder setSpeed(int speed,boolean isRandomSpeed){
            this.initSpeed=speed;
            this.isSpeedRandom=isRandomSpeed;
            return this;
        }

        public FallObject build(){
            return new FallObject(this);
        }

        public Builder setSize(int w,int h){
            this.bitmap=changeBitmapSize(this.bitmap,w,h);
            return this;
        }

        public Builder setSize(int w,int h,boolean isSizeRandom){
            this.bitmap=changeBitmapSize(this.bitmap,w,h);
            this.isSizeRandom=isSizeRandom;
            return this;
        }

        public Builder setWind(int level,boolean isWindRandom,boolean isWindChange){
            this.initWindLevel= level;
            this.isWindRandom = isWindRandom;
            this.isWindChange = isWindChange;
            return this;
        }

    }

    private void randomSpeed(){
        if(isSpeedRandom){
            presentSpeed=(float)((random.nextInt(3)+1)*0.1+1)* initSpeed;//随机生成速度
        }else{
            presentSpeed=initSpeed;
        }
    }

    private void randomSize(){
        if(isSizeRandom){
            float r = (random.nextInt(10)+1)*0.1f;
            float rW = r * builder.bitmap.getWidth();
            float rH = r * builder.bitmap.getHeight();
            bitmap = changeBitmapSize(builder.bitmap,(int)rW,(int)rH);
        }else {
            bitmap = builder.bitmap;
        }
        objectWidth = bitmap.getWidth();
        objectHeight = bitmap.getHeight();
    }

    /**
     *
     * @param canvas
     */
    public void drawObject(Canvas canvas) {
        moveObject();
        canvas.drawBitmap(bitmap,presentX,presentY,null);
    }

    /**
     * 移动物体
     */
    private void moveObject(){
        moveY();
        moveX();
        if(presentY>parentHeight || presentX<-bitmap.getWidth() || presentX>parentWidth+bitmap.getWidth()){
            reset();
        }
    }
    /**
     * Y轴移动
     */
    private void moveY(){
        presentY+=presentSpeed;
    }

    /**
     * X轴上的移动
     */
    private void moveX(){
        presentX+=defaultSpeed*Math.sin(angle);
        if(isWindChange){
            angle+=(float)(random.nextBoolean()?-1:1)*Math.random()*0.025;
        }
    }
    /**
     * 重置object位置
     */
    private void reset(){
        presentY=-objectHeight;
        presentX=initX;
        randomSpeed();
        randomSize();
        randomWind();
    }
    private void randomWind(){
        if(isWindRandom){
            angle=(float)((random.nextBoolean()?-1:1)*Math.random()*initWindLevel/50);
        }else {
            angle=(float)initWindLevel/50;
        }
        //限制angle的最大最小值
        if(angle>HALF_PI){
            angle = HALF_PI;
        }else if(angle<-HALF_PI){
            angle = -HALF_PI;
        }
    }
    /**
     * drawable图片转bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
                drawable.getOpacity()!= PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565);//获得drawable的宽度和高度（dp） 判断是不是不透明的
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 改变bitmap的大小
     * @param bitmap
     * @param newW
     * @param newH
     * @return
     */
    public static Bitmap changeBitmapSize(Bitmap bitmap,int newW,int newH){
        int oldW=bitmap.getWidth();
        int oldH=bitmap.getHeight();
        //计算缩放比例
        float scaleWidth=((float)newW)/oldW;
        float scaleHeight=((float)newH)/oldH;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        bitmap=Bitmap.createBitmap(bitmap,0,0,oldW,oldH,matrix,true);
        return bitmap;
    }
}
