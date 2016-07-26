package com.example.wangjimin.testrecyclerview.layout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjimin.testrecyclerview.R;

/**
 * Created by wangjimin on 16/7/25.
 */
public class MyLayoutManager extends LinearLayoutManager {

    public boolean upToLeftSide;
    public boolean upToRightSide;

    View outer;

    public MyLayoutManager(Context context) {
        super(context);
    }

    public MyLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int range = super.scrollHorizontallyBy(dx, recycler,state);
        if(range == 0){
            outer.setTag(R.string.intercept,true);
            upToLeftSide = upToRightSide = true;
        }
        return range;
    }
}
