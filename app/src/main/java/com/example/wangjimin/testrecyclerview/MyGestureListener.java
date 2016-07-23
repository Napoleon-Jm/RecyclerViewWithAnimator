package com.example.wangjimin.testrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by wangjimin on 16/7/23.
 */
public class MyGestureListener implements GestureDetector.OnGestureListener {

    private static final String TAG = "MyGesture wjm";
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isTop = false;
    private boolean isDown = false;

    private boolean isHorizontal = false;
    private boolean isVertical = false;

    private int step = 150;

    private Context mContext;

    public MyGestureListener(Context context){
        mContext = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG,"onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        Log.d(TAG,"onScroll" + distanceX + " " + distanceY);
        float ratio = Math.abs(distanceX/distanceY);
        if(ratio > 4.0f)
            isHorizontal = true;
        else if(ratio < 0.25f)
            isVertical = true;
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG,"onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling" + velocityX + " " + velocityY);
        MainActivity activity = (MainActivity)mContext;
        if(isHorizontal){
            if(velocityX < 0){
                Log.d("wjm","move left");
                AnimatorUtils.moveLeft(activity.getTextView(),step);
            }else{
                Log.d("wjm","move right");
                AnimatorUtils.moveRight(activity.getTextView(),step);
            }
            isHorizontal = false;
        } else if(isVertical){
            if(velocityY > 0){
                Log.d("wjm","move down");
                AnimatorUtils.moveDown(activity.getTextView(),step);
            }else{
                Log.d("wjm","move top");
                AnimatorUtils.moveTop(activity.getTextView(),step);
            }
            isVertical = false;
        }
        return true;
    }
}
