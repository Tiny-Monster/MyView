package com.tinymonster.myview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tinymonster.myview.Fall.FallObject;
import com.tinymonster.myview.Fall.FallingView;

public class MainActivity extends AppCompatActivity {
    private FallingView fallingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fallingView=(FallingView)findViewById(R.id.fallingview);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.xuehua);
        FallObject.Builder builder=new FallObject.Builder(bitmap);
        FallObject fallObject=builder.setSpeed(10,true).
                setWind(20,true,
                        true).setSize(50,50,true).build();
        fallingView.addFallObject(fallObject,50);
    }
}
