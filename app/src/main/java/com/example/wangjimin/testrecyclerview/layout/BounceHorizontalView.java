package com.example.wangjimin.testrecyclerview.layout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

/**
 * Created by wangjimin on 16/7/26.
 */
public class BounceHorizontalView extends HorizontalScrollView {

    private View inner;
    private Rect mInitPosition = new Rect();
    private int preL = 0;
    private boolean isFirst = true;

    public BounceHorizontalView(Context context) {
        super(context);
    }

    public BounceHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0)
            inner = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        handleTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    private void handleTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("wjm HorizontalView","onTouchEvent down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("wjm HorizontalView","onTouchEvent move");
                float curL = ev.getX();
                int delta = (int)(curL - preL);
                if(isFirst)
                    delta = 0;
                if(isInnerNeedMove()){
                    if(mInitPosition.isEmpty())
                        mInitPosition.set(inner.getLeft(),inner.getTop(),inner.getRight(),inner.getBottom());
                    inner.layout(inner.getLeft() + delta/4,inner.getTop(),
                            inner.getRight() + delta/4,inner.getBottom());
                }
                preL = (int)curL;
                isFirst = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("wjm HorizontalView","onTouchEvent up");
                if(isNeedAnimation()){
                    animation();
                    mInitPosition.setEmpty();
                }
                isFirst = true;
                break;
        }
    }

    private void animation(){
        TranslateAnimation animation = new TranslateAnimation(inner.getLeft(),mInitPosition.left,0,0);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(400);
        inner.startAnimation(animation);
        inner.layout(mInitPosition.left, mInitPosition.top, mInitPosition.right, mInitPosition.bottom);
    }

    private boolean isNeedAnimation(){
        return !mInitPosition.isEmpty();
    }

    private boolean isInnerNeedMove(){
        int offset = inner.getMeasuredWidth() - getWidth();
        int scrollX = getScrollX();
//        Log.d("wjm","getScrollX:" + scrollX + "  measrueWidth:" + inner.getMeasuredWidth() + " getWidth:" + getWidth());
        return (scrollX == 0 || scrollX == offset) && inner.getMeasuredWidth() >= getWidth();
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Boolean b = (Boolean)this.getTag(R.string.intercept);
//        return b == null?false:b;
//        return isInnerNeedMove();
//        return super.onInterceptTouchEvent(ev);
//    }
}
