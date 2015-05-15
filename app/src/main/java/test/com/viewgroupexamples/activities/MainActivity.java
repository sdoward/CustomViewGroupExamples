package test.com.viewgroupexamples.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import test.com.viewgroupexamples.R;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void circleViewOnClick(View view) {
        startActivity(new Intent(this, CircleActivity.class));
    }

    public void overLapViewOnClick(View view) {
        startActivity(new Intent(this, OverLapActivity.class));
    }

    public void altOverLapViewOnClick(View view) {
        startActivity(new Intent(this, AltOverlapActivity.class));
    }

    public void uninterestedViewOnClick(View view) {
        startActivity(new Intent(this, UninterestedViewActivity.class));
    }

    public void interestedViewOnClick(View view) {
        startActivity(new Intent(this, InterestedViewActivity.class));
    }

    public void interceptOnClick(View view) {
        startActivity(new Intent(this, InterceptActivity.class));
    }

    public void bottomSliderOnClick(View view) {
        startActivity(new Intent(this, BottomSliderActivity.class));
    }

    public void oppositeBottomSliderOnClick(View view) {
        startActivity(new Intent(this, OppositeBottomSliderActivity.class));
    }

}
