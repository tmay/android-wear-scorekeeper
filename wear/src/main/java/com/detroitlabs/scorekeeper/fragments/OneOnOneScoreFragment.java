package com.detroitlabs.scorekeeper.fragments;

import android.app.Fragment;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.detroitlabs.scorekeeper.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Terry on 9/30/14.
 */
@EFragment(R.layout.one_on_one_fragment)
public class OneOnOneScoreFragment extends Fragment {

    final public static String TAG = "score_view";

    public static OneOnOneScoreFragment newInstance() {
        return new OneOnOneScoreFragment_();
    }

    @ViewById(R.id.txt_left_score)
    TextView txtLeftScore;

    @ViewById(R.id.txt_right_score)
    TextView txtRightScore;

    private int viewWidth;
    private PointF down = new PointF(0.0f,0.0f);
    private PointF up = new PointF(0.0f,0.0f);

    private int leftScore = 0;
    private int rightScore = 0;

    @AfterViews
    void onAfterViews() {
        //txtLeftScore = (TextView) stub.findViewById(R.id.txt_left_score);
        //txtRightScore = (TextView) stub.findViewById(R.id.txt_right_score);
        //this.setOnTouchListener(MainWear.this);
        txtLeftScore.setText("00");
        txtRightScore.setText("00");

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        viewWidth = size.x;
    }


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
        txtRightScore.setText(rightScore + "");
    }
}
