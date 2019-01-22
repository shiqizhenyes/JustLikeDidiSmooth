package com.yisingle.didi.smooth.view;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yisingle.didi.smooth.R;
import com.yisingle.didi.smooth.utils.MoveUtils;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/9/26.
 */
public class MoveMarker {

    private MoveUtils moveUtils;

    private Marker marker;

    private AMap aMap;

    public MoveMarker(AMap aMap) {
        this.aMap = aMap;
        moveUtils = new MoveUtils();

        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.move_car))
                .anchor(0.5f, 0.5f);
        marker = aMap.addMarker(markerOptions);

        moveUtils.setCallBack(new MoveUtils.OnCallBack() {
            @Override
            public void onSetLatLng(LatLng latLng, float rotate) {
                if (!marker.isRemoved()) {
                    marker.setPosition(latLng);
                    //车辆方向
                    float carDirection = 360.0F - rotate + getAMap().getCameraPosition().bearing;
                    marker.setRotateAngle(carDirection);
                }


            }
        });
    }


    public void startMove(LatLng latLng, int time, boolean isContinue) {
        moveUtils.startMove(latLng, time, isContinue);

    }

    public void startMove(List<LatLng> list, int time, boolean isContinue) {
        moveUtils.startMove(list, time, isContinue);

    }


    public void stop() {
        moveUtils.stop();

    }


    public void destory() {
        moveUtils.destory();
        if (null != marker) {
            marker.remove();

        }


    }

    public AMap getAMap() {
        return aMap;
    }

}
