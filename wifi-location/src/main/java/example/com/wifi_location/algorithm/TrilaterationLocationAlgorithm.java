package example.com.wifi_location.algorithm;

import example.com.wifi_location.vo.LocationInfo;
import example.com.wifi_location.vo.PointInfo;

/**
 * @Auther: liuqi
 * @Date: 2019/3/19 16:59
 * @Description: 三边定位算法
 */
public class TrilaterationLocationAlgorithm implements LocationAlgorithm {


    /**
     * 根据参考点计算位置
     *
     * @param p1 点1
     * @param p2 点2
     * @param p3 点3
     * @return
     */
    @Override
    public LocationInfo calculate(PointInfo p1, PointInfo p2, PointInfo p3) {
        LocationInfo locationInfo=new LocationInfo();
        double a11 = 2*(p1.getX()-p3.getX());
        double a12 = 2*(p1.getY()-p3.getY());
        double b1 = Math.pow(p1.getX(),2)-Math.pow(p3.getX(),2) +Math.pow(p1.getY(),2)-Math.pow(p3.getY(),2) +Math.pow(p3.getD(),2)-Math.pow(p1.getD(),2);
        double a21 = 2*(p2.getX()-p3.getX());
        double a22 = 2*(p2.getY()-p3.getY());
        double b2 = Math.pow(p2.getX(),2)-Math.pow(p3.getX(),2) +Math.pow(p2.getY(),2)-Math.pow(p3.getY(),2) +Math.pow(p3.getD(),2)-Math.pow(p2.getD(),2);
        locationInfo.setxAxis((b1*a22-a12*b2)/(a11*a22-a12*a21));
        locationInfo.setyAxis((a11*b2-b1*a21)/(a11*a22-a12*a21));
        return locationInfo;
    }



}
