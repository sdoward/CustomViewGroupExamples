package test.com.viewgroupexamples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import test.com.viewgroupexamples.R;

public class UninterestedViewActivity extends Activity {

    private TextView onTouchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uninterested_view);
        onTouchTextView = (TextView) findViewById(R.id.onTouchTextView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        onTouchTextView.setText("y: " + motionEvent.getY() + " x: " + motionEvent.getX());
        return super.onTouchEvent(motionEvent);
    }
}
