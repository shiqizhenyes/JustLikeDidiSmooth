# 仿滴滴多个车辆平滑移动效果 

我们先看下效果图: gif 图片有点卡 但是apk上的效果很好 很流畅。

![image](https://github.com/jikun2008/JustLikeDidiSmooth/blob/master/pic/%E4%BB%BF%E6%BB%B4%E6%BB%B4%E5%A4%9A%E4%B8%AA%E8%BD%A6%E8%BE%86%E7%A7%BB%E5%8A%A8.gif?raw=true)


[apk下载地址](https://github.com/jikun2008/JustLikeDidiSmooth/blob/master/pic/didiSmoothMove.apk)



扫我下载apk:

![image](https://github.com/jikun2008/JustLikeDidiSmooth/blob/master/pic/apk%E4%B8%8B%E8%BD%BD%E4%BA%8C%E7%BB%B4%E7%A0%81.png?raw=true)


优势: 
1. 可以使用普通的Marker类,不用再使用高德提供的SmoothMarker。
2. 可以返回的车辆行驶角度。(这个角度是根据两个点坐标来计算出来)。
3. 跟Marker类解耦




> 关键类 MoveUtils 提供的方法如下。

```


    /**
     *  
     * @param list 坐标数组
     * @param time 时间   毫秒 多长时间走完这些数组
     * @param isContinue 是否在以上次停止后的坐标点继续移动 当list.size()=1 
     * 注意:如果调用 startMove(list,time,isContinue) 如果list.size=1 只传递了一个点并且isContinue=false
     那么 onSetGeoPoint回调方法返回的角度是0 因为只有一个点是无法计算角度的
     */
    public void startMove(List<LatLng> list, long time, boolean isContinue)
    
    
    
    
    /**
     * 
     * @param latLng 坐标
     * @param time   时间 毫秒
     * @param isContinue 是否在以上次停止后的坐标点继续移动 当list.size()=1 isContinue 就会变的非常有用
     * 注意:如果调用 startMove(list,time,isContinue) 如果list.size=1 只传递了一个点并且isContinue=false
     那么 onSetGeoPoint回调方法返回的角度是0 因为只有一个点是无法计算角度的
     */
    public void startMove(LatLng latLng, long time, boolean isContinue)
    
    
    /**
     *  停止移动
     */
    public void stop()
    
    
    
    /**
     *  释放工具类 
     */
    public void destory()
    
    
    /**
     * 设置监听回调
     * @param callBack OnCallBack
     */
    public void setCallBack(OnCallBack callBack)
    
    
    
     public interface OnCallBack {

        /**
         * 设置坐标IPoint
         * <p>
         * 角度返回  这里的角度返回是根据两个点的坐标来计算的
         * 并不是传感器返回的 
         * 
         *
         *
         * @param point  IPoint
         * @param rotate 角度
         */
        void onSetGeoPoint(LatLng latLng, float rotate);
    }
```    

## 如何使用  

> Demo代码

```java

    moveUtils = new MoveUtils();
      
      
    moveUtils.setCallBack(new MoveUtils.OnCallBack() {
            @Override
            public void onSetGeoPoint(LatLng latLng, float rotate) {
                if (!marker.isRemoved()) {
                    marker.setPosition(point);
                    //获取实际车辆方向。
                    float carDirection = 360.0F - rotate + getAMap().getCameraPosition().bearing;
                    marker.setRotateAngle(carDirection);
                }


            }
        });
        
        
          moveUtils.startMove(list, 5000, fasle);



```


    



## 源码   你只需要下载两个类就可以完成 平滑移动的效果 如下图:所示

> MoveUtils



[MoveUtils源码](https://github.com/jikun2008/JustLikeDidiSmooth/blob/master/app/src/main/java/com/yisingle/didi/smooth/utils/MoveUtils.java)




> CustomAnimator

[CustomAnimator源码](https://github.com/jikun2008/JustLikeDidiSmooth/blob/master/app/src/main/java/com/yisingle/didi/smooth/utils/CustomAnimator.java)



> 源码下载地址

[源码下载地址](https://github.com/jikun2008/JustLikeDidiSmooth)




