package com.whn.whn.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by whn on 2016/12/15.
 */

public class QuickIndexBar extends View {

    int ColorDefault = Color.WHITE;//默认颜色
    int ColorPressed = Color.BLACK;//按下颜色

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Paint paint;


    public QuickIndexBar(Context context) {
        this(context,null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    float cellHeight ;//一个格子的高度
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f/indexArr.length;
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ColorDefault);
        int size = getResources().getDimensionPixelSize(R.dimen.text_size);
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            String text  = indexArr[i];
            float x = getMeasuredWidth()/2;
            //文字的高度 = 格子的一半高度 + 文字一半的高度 + i * 格子高度
            int textHeight = getTextHeight(text);
            float y = cellHeight/2 + textHeight/2 + i*cellHeight;
            //变色
            paint.setColor(i==index?ColorPressed:ColorDefault);
            canvas.drawText(text,x,y,paint);
        }
    }


    /**
     * 触摸事件
     */
    int index = -1;//用来记录触摸的索引
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int temp = (int) (event.getY()/cellHeight);
                if(temp!=index){
                    index = temp;
                    if(index>=0 && index<indexArr.length){
                        String word = indexArr[index];
                        if(listener!=null){
                            listener.onLetterChange(word);
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                index = -1;
                break;
        }
        invalidate();
        return true;
    }



    /**
     * 获取文字的高度
     */
    private int getTextHeight(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        return rect.height();
    }


    /**
     * 接口回调
     */
    private onLetterChangeListener listener;

    public void setonLetterChangeListener(onLetterChangeListener listener){
        this.listener = listener;
    }

    public interface onLetterChangeListener{
        void onLetterChange(String word);
    }




}
