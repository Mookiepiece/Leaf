package com.huojitang.leaf;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.huojitang.global.LeafApplication;
import com.huojitang.util.LeafDateSupport;

public class TagIconView extends View {

    Paint paint; //画背景颜色
    Bitmap bitmap; //缓存图标

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444);
    }

    public TagIconView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TagIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagIconView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i <= n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TagIconView_bgColor:
                    paint=new Paint();
                    paint.setColor(a.getColor(attr, Color.WHITE));
                    break;
                case R.styleable.TagIconView_fgIcon:
                    bitmap=BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,R.drawable.baby_trolley));
                    break;
            }
        }
    }

    /**
     * 更新背景
     * @param bgColor 传入color
     */
    public void setBgColor(int bgColor) {
        paint=new Paint();
        paint.setColor(bgColor);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        invalidate();
    }

    /**
     * 更新图标
     * @param resId 传入R，Id
     */
    public void setFgIcon(int resId) {
        bitmap=BitmapFactory.decodeResource(getResources(),resId);
        invalidate();
    }

//    /**
//     *
//     * @param widthMeasureSpec
//     * @param heightMeasureSpec
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        // 初始化bitmap,Canvas
//        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
//        canvasCache = new Canvas(bitmap);
//    }

    final double SCALE=0.6;
    final double SCALE_0=(1-SCALE)/2;
    final double SCALE_1=SCALE+SCALE_0;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(0,0,this.getMeasuredWidth(),this.getMeasuredHeight(),paint);
        canvas.drawBitmap(bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect((int)(SCALE_0*this.getMeasuredWidth()), (int)(SCALE_0*this.getMeasuredHeight()),
                        (int)(SCALE_1*this.getMeasuredWidth()),(int)(SCALE_1*this.getMeasuredHeight())), null);

    }
}
