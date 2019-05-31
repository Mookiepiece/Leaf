package com.huojitang.leaf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import com.huojitang.leaf.R;

public class TagIconResultView extends View {

    private Paint paint; //画背景颜色
    private Bitmap bitmap; //缓存图标

    public TagIconResultView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TagIconResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagIconResultView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i <= n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TagIconResultView_bgColor:
                    paint=new Paint();
                    paint.setColor(a.getColor(attr, Color.WHITE));
                    paint.setStrokeWidth(5);
                    paint.setAntiAlias(true);
                    break;
                case R.styleable.TagIconResultView_fgIcon:
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

    //图标缩放比
    private final double SCALE=0.6;
    private final double SCALE_0=(1-SCALE)/2;
    private final double SCALE_1=SCALE+SCALE_0;

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
