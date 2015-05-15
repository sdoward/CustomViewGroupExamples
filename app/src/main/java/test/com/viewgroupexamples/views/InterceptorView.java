package test.com.viewgroupexamples.views;

import android.content.Context;
import android.util.*;
import android.view.*;
import android.widget.*;

public class InterceptorView extends ViewGroup implements View.OnClickListener{

    private Button button;
    private TextView textView;
    private boolean buttonState = false;

    public InterceptorView(Context context) {
        super(context);
    }

    public InterceptorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (buttonState == false) {
          super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        button = (Button) getChildAt(0);
        button.layout(l, t, r, 150);
        View view = getChildAt(1);
        view.layout(l, 150, r, b-150);
        textView = (TextView) getChildAt(2);
        textView.layout(l, b - 150, r, b);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        String string = buttonState ? "Calling super" : "Not calling super";
        textView.setText(string + System.getProperty ("line.separator") +
                "y: " + motionEvent.getY() + "x: " + motionEvent.getX());
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void onClick(View v) {
        buttonState = !buttonState;

    }
}
