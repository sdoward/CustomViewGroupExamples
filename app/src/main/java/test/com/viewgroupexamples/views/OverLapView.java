package test.com.viewgroupexamples.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.*;
import android.util.AttributeSet;
import android.view.*;

import test.com.viewgroupexamples.R;

public class OverLapView extends ViewGroup implements View.OnClickListener {

    public static final String SELECTED_CHILD_POSITION = "selectedChildPosition";
    private int selectedChildPosition = -1;
    private int animationDuration = 0;
    private int overlap = 80;

    public OverLapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public OverLapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        setSaveEnabled(true);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OverLapView,
                0, 0);
        try {
            if (a.getBoolean(R.styleable.OverLapView_animate, false)) {
                animationDuration = 300;
            }
            overlap = a.getInteger(R.styleable.OverLapView_overlap, 80);
        } finally {
            a.recycle();
        }
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int childTop = b - ((childCount - i) * overlap);
            view.layout(l, childTop, r, b + childTop);
            view.setTag(i);
            view.setOnClickListener(this);
        }
        if (selectedChildPosition != -1) {
            View view = getChildAt(selectedChildPosition);
            ObjectAnimator.ofFloat(view, "translationY", -view.getTop())
                    .setDuration(0)
                    .start();
        }
    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == selectedChildPosition) {
            ObjectAnimator.ofFloat(view, "translationY", 0)
                    .setDuration(animationDuration)
                    .start();
            selectedChildPosition = -1;
        } else {
            ObjectAnimator.ofFloat(view, "translationY", -view.getTop())
                    .setDuration(animationDuration)
                    .start();
            if (selectedChildPosition != -1) {
                View selectedView = getChildAt(selectedChildPosition);
                ObjectAnimator.ofFloat(selectedView, "translationY", 0)
                        .setDuration(animationDuration)
                        .start();
            }
            selectedChildPosition = (int) view.getTag();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTED_CHILD_POSITION, this.selectedChildPosition);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.selectedChildPosition = bundle.getInt(SELECTED_CHILD_POSITION);
        }
        super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE);
    }
}