package test.com.viewgroupexamples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

import test.com.viewgroupexamples.R;

public class InterceptActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interceptor_example);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contentView);
        for (int i = 0; i < 100; i++) {
            Button button = new Button(this);
            button.setPadding(0, 100, 0, 100);
            button.setText("Number: " + i);
            linearLayout.addView(button);
        }
    }


}
