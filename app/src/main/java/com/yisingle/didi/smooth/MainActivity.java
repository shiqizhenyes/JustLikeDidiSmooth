package com.yisingle.didi.smooth;

import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.yisingle.didi.smooth.base.BaseMapActivity;
import com.yisingle.didi.smooth.test.TestRandomUtils;
import com.yisingle.didi.smooth.view.MoveMarker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 */
public class MainActivity extends BaseMapActivity {

    private List<MoveMarker> moveMarkerList = new ArrayList<>();


    private final LatLng center = new LatLng(30.546284, 104.06934);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextureMapView textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);


        //建立10个移动的车辆
        for (int i = 0; i < 10; i++) {
            moveMarkerList.add(new MoveMarker(getAmap()));
        }

        moveCamre(center);


    }


    public void testMove(View view) {
        for (MoveMarker moveMarker : moveMarkerList) {
            moveMarker.startMove(TestRandomUtils.getRandomMoveList(center), 5000, false);
        }

    }


    public void testStop(View view) {
        for (MoveMarker moveMarker : moveMarkerList) {
            moveMarker.stop();
        }

    }

    private void moveCamre(LatLng center) {


        getAmap().animateCamera(CameraUpdateFactory.newLatLngZoom(center, 14));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        for (MoveMarker moveMarker : moveMarkerList) {
            moveMarker.destory();
        }
    }
}
