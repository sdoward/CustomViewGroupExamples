package test.com.viewgroupexamples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import test.com.viewgroupexamples.views.CircleView;

public class CircleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CircleView(this));
        Toast.makeText(this, "Japanese Flag!", Toast.LENGTH_LONG).show();
    }
}
