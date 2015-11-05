package cn.demo.verticalmarquee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private VerticalGestureDetector gesture;
    private View view;
    private ViewFlipper viewFlipper;
    private FrameLayout frameLayout;
    private int mCurrPos = -1;
    private ArrayList<String> titleList = new ArrayList<>();
    private int images[]=new int[]{R.mipmap.head,R.mipmap.ic_launcher,R.mipmap.icon_mycollection
            ,R.mipmap.icon_new_head_image,R.mipmap.icon_phone,R.mipmap.icon_recommend};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        gesture = (VerticalGestureDetector) findViewById(R.id.gesture);
        gesture.setOnGesture(new VerticalGestureDetector.OnGesture() {
            @Override
            public void up() {
                viewFlipper.stopFlipping();
                viewFlipper.setInAnimation(MainActivity.this, R.anim.in_bottomtop);
                viewFlipper.setOutAnimation(MainActivity.this, R.anim.out_bottomtop);
                viewFlipper.showNext();
                viewFlipper.startFlipping();
            }

            @Override
            public void down() {
                viewFlipper.stopFlipping();
                viewFlipper.setInAnimation(MainActivity.this, R.anim.in_topbottom);
                viewFlipper.setOutAnimation(MainActivity.this, R.anim.out_topbottom);
                viewFlipper.showPrevious();
                viewFlipper.startFlipping();
            }

            @Override
            public void left() {

            }

            @Override
            public void right() {

            }
        });
        view = getLayoutInflater().inflate(R.layout.flipp_layout, null);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.homepage_notice_vf);
        frameLayout.addView(view);
        for (int i = 0; i < images.length; i++) {
            titleList.add("这是第" + i + "个广告");
        }

        fristLoop();
//        secondLoop();
    }

    private void fristLoop() {
        addFilView();
        viewFlipper.setInAnimation(this, R.anim.in_bottomtop);
        viewFlipper.setOutAnimation(this, R.anim.out_bottomtop);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    private void secondLoop() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moveNext();
                    }
                });
            }
        };
        Timer timer = new Timer();
        if (images.length > 1)
            timer.schedule(task, 0, 2000);
        else
            setView(0, 0);
    }

    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        viewFlipper.setInAnimation(this, R.anim.in_bottomtop);
        viewFlipper.setOutAnimation(this, R.anim.out_bottomtop);
        viewFlipper.showNext();
    }

    private void addFilView() {
        for (int i = 0; i < images.length; i++) {
            View noticeView = getLayoutInflater().inflate(R.layout.notice_item,
                    null);
            LinearLayout ll01 = (LinearLayout) noticeView.findViewById(R.id.ll01);
            TextView notice_tv = (TextView) ll01.findViewById(R.id.notice_tv);
            ImageView imageView = (ImageView) ll01.findViewById(R.id.image);
            notice_tv.setText(titleList.get(i));
            imageView.setImageResource(images[i]);
            ll01.setTag(notice_tv.getText());
            ll01.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Log.e(TAG, arg0.getTag().toString());
                }
            });
            viewFlipper.addView(noticeView);
        }
    }

    private void setView(int curr, int next) {

        View noticeView = getLayoutInflater().inflate(R.layout.notice_item,
                null);
        LinearLayout ll01 = (LinearLayout) noticeView.findViewById(R.id.ll01);
        TextView notice_tv = (TextView) ll01.findViewById(R.id.notice_tv);
        ImageView imageView = (ImageView) ll01.findViewById(R.id.image);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        imageView.setImageResource(images[next]);
        ll01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.e(TAG, titleList.get(mCurrPos));
            }
        });
        if (viewFlipper.getChildCount() > 1) {
            viewFlipper.removeViewAt(0);
        }
        viewFlipper.addView(noticeView, viewFlipper.getChildCount());
        mCurrPos = next;

    }
}
