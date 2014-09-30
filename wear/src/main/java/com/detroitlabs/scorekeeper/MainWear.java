package com.detroitlabs.scorekeeper;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainWear extends Activity implements View.OnTouchListener {

    final public static String TAG = "score_view";

    private TextView txtLeftScore;
    private TextView txtRightScore;

    private int viewWidth;
    private PointF down = new PointF(0.0f,0.0f);
    private PointF up = new PointF(0.0f,0.0f);

    private int leftScore = 0;
    private int rightScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                txtLeftScore = (TextView) stub.findViewById(R.id.txt_left_score);
                txtRightScore = (TextView) stub.findViewById(R.id.txt_right_score);
                stub.setOnTouchListener(MainWear.this);
                txtLeftScore.setText("00");
                txtRightScore.setText("00");
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        viewWidth = size.x;

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = MotionEventCompat.getActionMasked(motionEvent);
            float touchY = MotionEventCompat.getY(motionEvent, 0);
            float touchX = MotionEventCompat.getX(motionEvent, 0);


            switch(action) {
                case (MotionEvent.ACTION_DOWN) :
                    //Log.d(TAG,"Action was DOWN x:"+touchX+" y:"+touchY);
                    down = new PointF(touchX,touchY);
                    return true;
                case (MotionEvent.ACTION_MOVE) :
                    //Log.d(TAG,"Action was MOVE");
                    return true;
                case (MotionEvent.ACTION_UP) :
                    //Log.d(TAG,"Action was UP: x:"+touchX+" y:"+touchY);
                    up = new PointF(touchX, touchY);
                    adjustScore();
                    return true;
                case (MotionEvent.ACTION_CANCEL) :
                    //Log.d(TAG,"Action was CANCEL");
                    return true;
                case (MotionEvent.ACTION_OUTSIDE) :
                    Log.d(TAG, "Movement occurred outside bounds " +
                            "of current screen element");

                default :
                    return false;
            }

        }

        private void adjustScore() {
            boolean isLeft = (down.x < viewWidth/2);
            boolean shouldIncrement = (Float.compare(down.y, up.y) > 0);

            if (isLeft) {
                if (shouldIncrement) leftScore++;
                else leftScore--;
            } else {
                if (shouldIncrement) rightScore++;
                else rightScore--;
            }

            txtLeftScore.setText(leftScore+"");
            txtRightScore.setText(rightScore+"");
        }
}
