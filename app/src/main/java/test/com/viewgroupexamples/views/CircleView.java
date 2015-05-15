package test.com.viewgroupexamples.views;

import android.content.Context;
import android.graphics.*;
import android.util.*;
import android.view.View;

public class CircleView extends View {

    private int height;
    private int width;
    private Paint paint = new Paint();

    public CircleView(Context context) {
        super(context);
        setUp();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getHeight();
        width = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float diameter = width > height ? height : width;
        canvas.drawCircle(width/2, height/2, diameter/2, paint);
    }
}
