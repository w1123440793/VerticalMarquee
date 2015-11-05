package cn.demo.verticalmarquee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/5.
 * Description
 */
public class VerticalGestureDetector extends LinearLayout implements GestureDetector.OnGestureListener {

    private GestureDetector mGesture;
    public VerticalGestureDetector(Context context) {
        super(context);
        init();
    }

    public VerticalGestureDetector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalGestureDetector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mGesture=new GestureDetector(this);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getY()-e2.getY()>10&& Math.abs(velocityY)>10){
            //向上滑动
            if (onGesture!=null)
                onGesture.up();
            return true;
        }else if (e1.getY()-e2.getY()<-10&& Math.abs(velocityY)>10){
            if (onGesture!=null)
                onGesture.down();
            return true;
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mGesture.onTouchEvent(ev);
    }

    private OnGesture onGesture;
    public static interface OnGesture{
        public void up();
        public void down();
        public void left();
        public void right();
    }

    public void setOnGesture(OnGesture onGesture){
        this.onGesture=onGesture;
    }
}
