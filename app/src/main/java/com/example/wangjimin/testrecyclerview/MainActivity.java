package com.example.wangjimin.testrecyclerview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wangjimin.testrecyclerview.adapter.MyAdapter;
import com.example.wangjimin.testrecyclerview.layout.MyLayoutManager;
import com.example.wangjimin.testrecyclerview.listener.MyGestureListener;
import com.example.wangjimin.testrecyclerview.utils.AnimatorUtils;

/**
 * @author : jimin.wjm@alipay.com
 * This is an animator demo using ObjectAnimator and RecycleView.
 */

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter myAdapter;

    private TextView textView;

    private RecyclerView recyclerViewWithNoAni;
    private MyLayoutManager myLayoutManager;

    private String[] mData;

    //the item width.
    private int itemWidth;
    //single text view with.
    private int singleTextViewWidth;

    //for multi gesture.
    private float pointer1Y;
    private float pointer2Y;

    private final String PROPERTY_NAME = "width";
    //max distance to animate.
    private final int MAX_DIS = 80;
    //min distance to animate.
    private final int MIN_DIS = 20;
    //scale of dx to distance.
    private final int SCALE = 3;
    //animate time.
    private final long ANI_TIME = 200;
    //reverse time.
    private final long ANI_REVERSE_TIME = 300;

    private float pointerDis;

    private GestureDetector gestureDetector;
    private MyGestureListener myGestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_card);
        setTitle("Ant Mini Talk");
        init();
    }

    private void init(){
        initData();
        initView();
    }

    private void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);

        recyclerViewWithNoAni = (RecyclerView)findViewById(R.id.recycler_view_noani);
        myLayoutManager = new MyLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewWithNoAni.setLayoutManager(myLayoutManager);
        recyclerViewWithNoAni.setAdapter(myAdapter);

        textView = (TextView)findViewById(R.id.single_textview);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int dx = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int index;
                    int direction;
                    int distance = dx * SCALE;
                    index = distance < 0 ? 0 : 2;
                    direction = distance < 0 ? -1 : 1;
                    distance = resizeDistance(distance) * direction;
                    WrapperView wrapper = new WrapperView(recyclerView.getChildAt(index));
                    ObjectAnimator animator = ObjectAnimator
                            .ofInt(wrapper, PROPERTY_NAME, itemWidth, itemWidth + distance);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    ObjectAnimator animator1 = ObjectAnimator
                            .ofInt(wrapper, PROPERTY_NAME, itemWidth);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(ANI_TIME);
                    animator1.setDuration(ANI_REVERSE_TIME);
                    AnimatorSet set = new AnimatorSet();
                    set.playSequentially(animator, animator1);
                    set.start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                this.dx = dx;
            }
        });

        myGestureListener = new MyGestureListener(this);
        gestureDetector = new GestureDetector(this,myGestureListener);
        textView.setOnTouchListener(this);
        recyclerViewWithNoAni.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initData(){
        mData = new String[]{
                "I'm KaiXin",
                "This is an",
                "Animator Demo",
                "Thanks for",
                "Watching."};
        itemWidth = getLayoutInflater()
                .inflate(R.layout.recycler_item, new FrameLayout(this), false)
                .getLayoutParams().width;
        singleTextViewWidth = itemWidth;
        myAdapter = new MyAdapter(mData);
    }

    /**
     * Restrict the distance between MAX_DIS and MIN_DIS.
     * @param distance :
     * @return :
     */
    private int resizeDistance(int distance){
        int dis = Math.abs(distance);
        if(dis > MAX_DIS)
            dis = MAX_DIS;
        else if(dis < MIN_DIS)
            dis = MIN_DIS;
        return dis;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                Log.d("wjm","action down");
                return gestureDetector.onTouchEvent(event);
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("wjm","action pointer down");
                pointer1Y = event.getY(0);
                pointer2Y = event.getY(1);
                pointerDis = spaceing(event);
                return false;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("wjm", "action pointer up");
                dispatchPointerEvent(event);
                return true;
            default:
                return gestureDetector.onTouchEvent(event);
        }
    }

    private void dispatchPointerEvent(MotionEvent event) {
        boolean isConsume = scaleAni(event);
        if (!isConsume)
            rotateAni(event);
    }

    private void rotateAni(MotionEvent event){
        float deltaY1 = event.getY(0) - pointer1Y;
        float deltaY2 = event.getY(1) - pointer2Y;
        if(deltaY1*deltaY2 < 0){
                AnimatorUtils.rotate(textView, deltaY1);
        }
    }

    private boolean scaleAni(MotionEvent event){
        float spac = spaceing(event);
        float dis = Math.abs(spac - pointerDis);
        if(dis > 80){
            if(spac < pointerDis){
                singleTextViewWidth-=dis;
            }else{
                singleTextViewWidth+=dis;
            }
            AnimatorUtils.scale(new WrapperView(textView),singleTextViewWidth);
            return true;
        }
        return false;
    }

    private float spaceing(MotionEvent event){
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x*x+y*y);
    }

    /**
     * This is a wrapper class to wrap view, which can
     * add a setter and getter functions.
     */
    public static class WrapperView{
        View v;
        public WrapperView(View v){
            this.v = v;
        }

        /**
         * The origin setWidth of TextView only can set max width
         * of View. So we must create a setWidth function.
         * @param width :
         */
        public void setWidth(int width){
            v.getLayoutParams().width = width;
            v.requestLayout();
        }

        public int getWidth(){
            return v.getLayoutParams().width;
        }

        public void setHeight(int height){
            v.getLayoutParams().height = height;
            v.requestLayout();
        }

        public int getHeight(){
            return v.getLayoutParams().height;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //for test
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item1){
            Log.d("wjm","refresh is down");
            ObjectAnimator.ofFloat(textView, "translationX", 0, 300).setDuration(2000).start();
        }
        if(item.getItemId() == R.id.item2){
            textView.setTranslationX(0);
            textView.setTranslationY(0);
            textView.setRotation(0);
            textView.requestLayout();
        }
        return super.onOptionsItemSelected(item);
    }

    public TextView getTextView(){
        return textView;
    }
}
