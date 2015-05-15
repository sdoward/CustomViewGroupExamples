package test.com.viewgroupexamples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.*;

import test.com.viewgroupexamples.R;

public class InterestedViewActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interested_view);
        button = (Button) findViewById(R.id.button);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        button.setText("y: " + motionEvent.getY() + " x: " + motionEvent.getX());
        return super.onTouchEvent(motionEvent);
    }
}