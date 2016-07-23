package com.example.wangjimin.testrecyclerview;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by wangjimin on 16/7/23.
 */
public class AnimatorUtils {

    private static float x = 0;
    private static float y = 0;

    private static float r = 0;

    private static long duration = 1500;

    public static void moveLeft(Object v,float end){
        x-=end;
        ObjectAnimator.ofFloat(v, "translationX", x).setDuration(duration).start();
    }

    public static void moveRight(Object v,int end){
        x+=end;
        ObjectAnimator.ofFloat(v, "translationX",x).setDuration(duration).start();
    }

    public static void moveTop(Object v,int end){
        y-=end;
        ObjectAnimator.ofFloat(v,"translationY",y).setDuration(duration).start();
    }

    public static void moveDown(Object v,int end){
        y+=end;
        ObjectAnimator.ofFloat(v,"translationY",y).setDuration(duration).start();
    }

    public static void rotate(Object v,float end){
        r+=end;
        ObjectAnimator.ofFloat(v,"rotation",r).setDuration(duration).start();
    }

    public static void scale(Object v,int scale){
        ObjectAnimator.ofInt(v,"width",scale).setDuration(duration).start();
    }
}
