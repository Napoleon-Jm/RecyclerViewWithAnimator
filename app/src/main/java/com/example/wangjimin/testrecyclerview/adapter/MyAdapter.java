package com.example.wangjimin.testrecyclerview.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangjimin.testrecyclerview.R;

/**
 * Created by wangjimin on 16/7/22.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mData;

    public MyAdapter(String[] data){
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView)LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.v.setText(mData[position]);
        setOnClickEvent(holder.v,position);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView v;

        public ViewHolder(View itemView) {
            super(itemView);
            v = (TextView)itemView;
        }
    }

    private void setOnClickEvent(View v, final int position){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,mData[position],Snackbar.LENGTH_SHORT)
                        .setAction("dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                Log.d("wjm","click:" + position);
            }
        });
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("wjm","onTouch down");
                        Log.d("wjm", ((RecyclerView)(v.getParent())).getMeasuredWidth() + "" );
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("wjm","onTouch move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("wjm","onTouch up");
                        break;
                }
                Log.d("wjm TextView","onTouch:" + position);
                return true;
            }
        });
    }

}
