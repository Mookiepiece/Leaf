package com.huojitang.leaf;

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
import androidx.core.content.res.ResourcesCompat;

import com.huojitang.global.LeafApplication;

public class TagColorItemView extends View {

    private Paint paint; //画背景颜色
    private static Paint borderPaint=new Paint();
    private boolean active;

    static{
        borderPaint.setColor(ResourcesCompat.getColor(LeafApplication.getContext().getResources(),R.color.GoldenColor, null));
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

    public TagColorItemView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TagColorItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setActive(boolean active){
        this.active=active;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(active)
            canvas.drawOval(0,0,this.getMeasuredWidth(),this.getMeasuredHeight(),paint);
        canvas.drawOval(4,4,this.getMeasuredWidth()-4,this.getMeasuredHeight()-4,paint);
    }
}
