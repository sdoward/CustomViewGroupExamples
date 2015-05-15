package test.com.viewgroupexamples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.*;

import test.com.viewgroupexamples.R;
import test.com.viewgroupexamples.views.BottomSliderView;

public class BottomSliderActivity extends Activity implements BottomSliderView.TransitionController,
        ViewTreeObserver.OnScrollChangedListener {

    private BottomSliderView bottomSliderView;
    private LinearLayout contentView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(test.com.viewgroupexamples.R.layout.bottom_activity);
        bottomSliderView = (BottomSliderView) findViewById(R.id.bottomSliderView);
        bottomSliderView.setTransitionController(this);
        contentView = (LinearLayout) findViewById(R.id.contentView);
        for (int i = 0; i < 100; i++) {
            Button button = new Button(this);
            button.setPadding(0, 100, 0, 100);
            button.setText("Number: " + i);
            contentView.addView(button);
        }
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    @Override
    public boolean shouldTransition() {
        return scrollView.getScrollY() == 0;
    }

    @Override
    public void onScrollChanged() {

    }
}