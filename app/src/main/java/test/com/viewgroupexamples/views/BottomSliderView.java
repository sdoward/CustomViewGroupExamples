package test.com.viewgroupexamples.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.DecelerateInterpolator;

public class BottomSliderView extends ViewGroup {

    private boolean minimised = true;
    private View foreGroundView;
    private View backgroundView;
    private float down;
    private float overlap = 300;
    private float snapBack = 200;
    private boolean parallax = true;
    private GestureDetectorCompat gestureDetctor;
    private TransitionController transitionController;

    public BottomSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public BottomSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        gestureDetctor = new GestureDetectorCompat(context, flingListener);
    }

    public void setTransitionController(TransitionController transitionController) {
        this.transitionController = transitionController;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wspec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
        int hspec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        backgroundView = getChildAt(0);
        backgroundView.measure(wspec, hspec);
        this.foreGroundView = getChildAt(1);
        this.foreGroundView.measure(wspec, hspec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = (int) (b - overlap);
        View view = getChildAt(1);
        view.layout(l, top, r, b + (top));
        view = getChildAt(0);
        view.layout(l, t, r, top);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                down = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (minimised == false) {
                    if (transitionController.shouldTransition()) {
                        if (down < motionEvent.getY()) {
                            return true;
                        }
                    }
                }
                down = motionEvent.getY();
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                down = motionEvent.getY();
                if (isOnChildView(motionEvent)) {
                    if (minimised) {
                        return true;
                    }
                }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean isOnChildView(MotionEvent motionEvent) {
        return motionEvent.getY() > foreGroundView.getTop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (gestureDetctor.onTouchEvent(motionEvent)) {
            return true;
        }
        int action = motionEvent.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                down = motionEvent.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (down != 0) {
                    float newY = foreGroundView.getTranslationY() + (motionEvent.getY() - down);
                    if (-newY > foreGroundView.getTop()) {
                        foreGroundView.setTranslationY(-foreGroundView.getTop());
                        if (parallax) {
                            backgroundView.setTranslationY(-foreGroundView.getTop() / 2);
                        }
                    } else if (newY > 0) {
                        foreGroundView.setTranslationY(0);
                        if (parallax) {
                            backgroundView.setTranslationY(0);
                        }
                    } else {
                        foreGroundView.setTranslationY(newY);
                        if (parallax) {
                            backgroundView.setTranslationY(newY / 2);
                        }
                    }
                }
                down = motionEvent.getY();
                return true;
            case MotionEvent.ACTION_UP:
                int translateY;
                if (minimised) {
                    if (foreGroundView.getTranslationY() < -snapBack) {
                        translateY = -foreGroundView.getTop();
                        minimised = false;
                    } else {
                        translateY = 0;
                    }
                } else {
                    if (foreGroundView.getTranslationY() > (-foreGroundView.getTop()) + snapBack) {
                        translateY = 0;
                        minimised = true;
                    } else {
                        translateY = -foreGroundView.getTop();
                    }
                }
                performTransition(translateY);
                down = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                int translateYCancel = minimised ? 0 : -foreGroundView.getTop();
                performTransition(translateYCancel);
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    private void performTransition(int translationY) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(foreGroundView, "translationY", translationY);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        if (parallax) {
            ObjectAnimator backgroundAnimator = ObjectAnimator.ofFloat(backgroundView, "translationY", translationY / 2);
            backgroundAnimator.setInterpolator(new DecelerateInterpolator());
            backgroundAnimator.start();
        }
    }

    private GestureDetector.SimpleOnGestureListener flingListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (minimised && (velocityY < -2000)) {
                performTransition(-foreGroundView.getTop());
                minimised = false;
                return true;
            } else if ((minimised == false) && (velocityY > 2000)) {
                performTransition(0);
                minimised = true;
                return true;
            } else {
                return false;
            }
        }
    };

    public interface TransitionController {

        boolean shouldTransition();

    }

}
