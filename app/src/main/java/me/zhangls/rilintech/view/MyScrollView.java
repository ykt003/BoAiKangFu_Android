package me.zhangls.rilintech.view;

/**
 * Created by YANG on 15/8/31.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);

    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mGestureDetector = new GestureDetector(getContext(), new YScrollDetector());
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    //这个方法如果返回 true 的话 两个手指移动，启动一个按下的手指的移动不能被传播出去。
    {
        //mGestureDetector.onTouchEvent(event);
        super.onInterceptTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    //这个方法如果 true 则整个Activity 的 onTouchEvent() 不会被系统回调
    {
        super.onTouchEvent(event);
        return false;
    }
    /*private GestureDetector mGestureDetector;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector = new GestureDetector(getContext(), new YScrollDetector());

        return super.dispatchTouchEvent(ev);
    }


    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            *
             * if we're scrolling more closer to x direction, return false, let subview to process it

            return (Math.abs(distanceY) > Math.abs(distanceX));
        }

    }*/

}

