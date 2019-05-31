package com.huojitang.leaf.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.R;

public class TagColorItemView extends View {

    private Paint paint; //画背景颜色
    private static final Paint PAINT_1 =new Paint();
    private boolean active;

    static{
        PAINT_1.setColor(ResourcesCompat.getColor(LeafApplication.getContext().getResources(), R.color.GoldenColor, null));
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
        if(active) {
            canvas.drawOval(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight(), PAINT_1);
        }
            canvas.drawOval(16,16,this.getMeasuredWidth()-16,this.getMeasuredHeight()-16,paint);
    }
}
