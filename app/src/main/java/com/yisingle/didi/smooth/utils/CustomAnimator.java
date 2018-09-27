package com.yisingle.didi.smooth.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.autonavi.amap.mapcore.IPoint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jikun
 * Created by jikun on 2018/6/6.
 */
public class CustomAnimator {

    private ExecutorService mThreadPools;


    private long startTime;

    private int duration = 1000;


    private List<IPoint> pointList = new ArrayList<>();


    private int index = 0;


    private CustomRunnable customRunnable;


    private OnTimeListener onTimeListener;


    public CustomAnimator() {
        mThreadPools = new ThreadPoolExecutor(1,
                1,
                200L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(1),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        return new Thread(r);
                    }
                }
                , new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public CustomAnimator ofIPoints(IPoint start, IPoint end) {

        List<IPoint> list = new ArrayList<>();
        list.add(start);
        list.add(end);
        return ofIPoints(list);

    }


    public CustomAnimator ofIPoints(IPoint... points) {
        List<IPoint> list = new ArrayList<>();
        for (IPoint point : points) {
            list.add(point);
        }
        return ofIPoints(list);

    }


    public CustomAnimator ofIPoints(List<IPoint> list) {
        this.pointList = list;
        return this;

    }


    public void start(int duration) {
        end();
        this.duration = duration;
        index = 0;
        startTime = System.currentTimeMillis();
        customRunnable = new CustomRunnable();
        customRunnable.exitFlag.set(false);
        mThreadPools.submit(customRunnable);
    }


    public void end() {
        if (null != customRunnable) {
            customRunnable.exitFlag.set(true);
            Log.e("测试代码", "测试代码停止=end");
        }
        //----cancale
    }

    public class CustomRunnable implements Runnable {
        AtomicBoolean exitFlag = new AtomicBoolean(false);


        private CustomRunnable() {


        }


        @Override
        public void run() {
            if (pointList.size() >= 2) {
                IPoint start = pointList.get(0);
                while (!exitFlag.get()) {

                    if (index + 1 < pointList.size()) {

                        long currentTime = System.currentTimeMillis();
                        long time = currentTime - startTime;
                        IPoint end = pointList.get(index + 1);
                        int plugX = end.x - start.x;
                        int plugY = end.y - start.y;


                        float percent = (float) time / duration / (pointList.size() - 1);

                        IPoint moveIPoint = new IPoint((int) ((double) start.x + (double) plugX * (percent - index)), (int) ((double) start.y + (double) plugY * (percent - index)));
                        if (percent - index >= 1) {
                            index = index + 1;
                            start = moveIPoint;
                        }


                        if (null != onTimeListener) {
                            onTimeListener.onUpdate(pointList.get(index), moveIPoint, pointList.get(index + 1));
                        }
                    } else {
                        exitFlag.set(true);
                    }

                }
            }
            //--线程结束

            pointList.clear();


        }
    }


    public CustomAnimator setOnTimeListener(OnTimeListener onTimeListener) {
        this.onTimeListener = onTimeListener;
        return this;
    }

    public interface OnTimeListener {
        /**
         * 更新
         *
         * @param moveIPoint IPoint
         */
        void onUpdate(IPoint start, IPoint moveIPoint, IPoint end);


    }


}