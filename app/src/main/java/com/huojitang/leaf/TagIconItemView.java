package com.huojitang.leaf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TagIconItemView extends View {

    private static Paint paintBlack; //画背景颜色
    private static Paint paintWhite; //画背景颜色(active)
    private Bitmap bitmap; //缓存图标
    private boolean active;

    static{
        paintBlack =new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(5);
        paintBlack.setAntiAlias(true);
        paintWhite=new Paint();
        paintWhite.setColor(Color.WHITE);
        paintWhite.setStrokeWidth(5);
        paintWhite.setAntiAlias(true);
    }

    /**
     * 更新图标
     * @param resId 传入R，Id
     */
    public void setFgIcon(int resId) {
        bitmap= BitmapFactory.decodeResource(getResources(),resId);
        invalidate();
    }

    public TagIconItemView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TagIconItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setActive(boolean active){
        this.active=active;
    }

    //图标缩放比
    private final double SCALE=0.6;
    private final double SCALE_0=(1-SCALE)/2;
    private final double SCALE_1=SCALE+SCALE_0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(active) {
            canvas.drawOval(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight(), paintBlack);
            canvas.drawOval(4, 4, this.getMeasuredWidth() - 4, this.getMeasuredHeight() - 4, paintWhite);
        }
        canvas.drawBitmap(bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect((int)(SCALE_0*this.getMeasuredWidth()), (int)(SCALE_0*this.getMeasuredHeight()),
                        (int)(SCALE_1*this.getMeasuredWidth()),(int)(SCALE_1*this.getMeasuredHeight())), null);

    }
}
