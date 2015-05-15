package test.com.viewgroupexamples.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;

import test.com.viewgroupexamples.R;
import test.com.viewgroupexamples.views.BottomSliderView;

public class OppositeBottomSliderActivity extends Activity implements BottomSliderView.TransitionController,
        OnMapReadyCallback, GoogleMap.OnCameraChangeListener {

    private BottomSliderView bottomSliderView;
    private LinearLayout contentView;
    private boolean allowMapDrag = false;
    private double initialLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opposite_bottom_slider_activity);
        bottomSliderView = (BottomSliderView) findViewById(R.id.bottomSliderView);
        bottomSliderView.setTransitionController(this);
        contentView = (LinearLayout) findViewById(R.id.contentView);
        for (int i = 0; i < 100; i++) {
            Button button = new Button(this);
            button.setPadding(0, 100, 0, 100);
            button.setText("Number: " + i);
            contentView.addView(button);
        }
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean shouldTransition() {
        return allowMapDrag;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnCameraChangeListener(this);
        initialLatitude = googleMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        allowMapDrag = cameraPosition.target.latitude > initialLatitude;
    }
}
