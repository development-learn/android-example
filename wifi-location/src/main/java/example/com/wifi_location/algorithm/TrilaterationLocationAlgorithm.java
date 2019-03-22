package example.com.wifi_location.algorithm;

import example.com.wifi_location.vo.LocationInfo;
import example.com.wifi_location.vo.PointInfo;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Auther: liuqi
 * @Date: 2019/3/19 16:59
 * @Description: 三边定位算法
 */
public class TrilaterationLocationAlgorithm implements LocationAlgorithm {


    /**
     * 根据参考点计算位置
     *
     * @param ps3 三个设备点
     * @return
     */
    @Override
    public LocationInfo calculate(PointInfo[] ps3) {
        LocationInfo locationInfo = cal(ps3[0], ps3[1], ps3[2]);
        if (!isOk(locationInfo)) {

            //结果不有效，开始进行纠正
//            匹配纠正方式,true进行xy纠正，false进行距离纠正
            boolean isXy = isXy(ps3);
            //最大尝试纠正次数
            int pollingBound = MAX_ADJUST;
            do {
                PointInfo[] temp_ps3 = new PointInfo[3];
                if (isXy) {
                    //坐标轴纠正，三点在同一条直线的时候
                    boolean isX = byDiff(ps3);//优先纠正x还是y
                    temp_ps3 = correctXY(temp_ps3, ps3, isX, pollingBound);
                    locationInfo = cal(temp_ps3[0], temp_ps3[1], temp_ps3[2]);
//         返回的坐标不符合三边定位,尝试适当纠正
                    if (!isOk(locationInfo)) {
                        temp_ps3 = correctXY(temp_ps3, ps3, !isX, pollingBound);
                        locationInfo = cal(temp_ps3[0], temp_ps3[1], temp_ps3[2]);

                    }
                } else {
//                    距离纠正,三点不在一条直线的时候
                    temp_ps3 = correctD(temp_ps3, ps3, pollingBound);
                    locationInfo = cal(temp_ps3[0], temp_ps3[1], temp_ps3[2]);
                    if (!isOk(locationInfo)) {
                        temp_ps3 = correctD(temp_ps3, ps3, -1 * pollingBound);
                        locationInfo = cal(temp_ps3[0], temp_ps3[1], temp_ps3[2]);

                    }
                }
                pollingBound--;
            } while ((!isOk(locationInfo)) && pollingBound > 0);

        }


        return locationInfo;
    }


    /**
     * 根据三点是否在一条直线上判断纠正方式
     *
     * @param ps3
     * @return 在一条直线 true
     * 不在一条直线 false
     */
    private boolean isXy(PointInfo[] ps3) {
        if ((ps3[0].getX() == ps3[1].getX() && ps3[0].getX() == ps3[2].getX())
                || (ps3[0].getY() == ps3[1].getY() && ps3[0].getY() == ps3[2].getY())) {
            return true;
        }
        return false;
    }


    private LocationInfo cal(PointInfo p1, PointInfo p2, PointInfo p3) {
        LocationInfo locationInfo = new LocationInfo();
        double a11 = 2 * (p1.getX() - p3.getX());
        double a12 = 2 * (p1.getY() - p3.getY());
        double b1 = Math.pow(p1.getX(), 2) - Math.pow(p3.getX(), 2) + Math.pow(p1.getY(), 2) - Math.pow(p3.getY(), 2) + Math.pow(p3.getD(), 2) - Math.pow(p1.getD(), 2);
        double a21 = 2 * (p2.getX() - p3.getX());
        double a22 = 2 * (p2.getY() - p3.getY());
        double b2 = Math.pow(p2.getX(), 2) - Math.pow(p3.getX(), 2) + Math.pow(p2.getY(), 2) - Math.pow(p3.getY(), 2) + Math.pow(p3.getD(), 2) - Math.pow(p2.getD(), 2);
        locationInfo.setxAxis((b1 * a22 - a12 * b2) / (a11 * a22 - a12 * a21));
        locationInfo.setyAxis((a11 * b2 - b1 * a21) / (a11 * a22 - a12 * a21));
        return locationInfo;
    }

    /**
     * 通过xy的差值来决定位移那个坐标
     *
     * @param ps3
     * @return
     */
    private boolean byDiff(PointInfo[] ps3) {
        double diff_x = 0;
        double diff_y = 0;
        Collections.sort(Arrays.asList(ps3), (o1, o2) -> {
            return new Double(o1.getX() - o2.getX()).intValue();
        });
        diff_x = (ps3[2].getX() - ps3[1].getX()) + (ps3[1].getX() - ps3[0].getX());
        Collections.sort(Arrays.asList(ps3), (o1, o2) -> {
            return new Double(o1.getX() - o2.getX()).intValue();
        });
        diff_y = (ps3[2].getY() - ps3[1].getY()) + (ps3[1].getY() - ps3[0].getY());
        if (diff_x >= diff_y) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 进行xy坐标纠正
     *
     * @param ps3
     * @param isX
     * @return
     */
    private PointInfo[] correctXY(PointInfo[] temp_ps3, PointInfo[] ps3, boolean isX, int pollingBound) {
        temp_ps3 = clone(temp_ps3, ps3);
        if (isX) {
            temp_ps3[2].setX(temp_ps3[2].getX() - (CORRECT_UNIT * (MAX_ADJUST - pollingBound+1)));
        } else {
            temp_ps3[2].setY(temp_ps3[2].getY() - (CORRECT_UNIT * (MAX_ADJUST - pollingBound+1)));
        }
        return temp_ps3;
    }

    /**
     * 距离纠正
     *
     * @param ps3
     * @return
     */
    private PointInfo[] correctD(PointInfo[] temp_ps3, PointInfo[] ps3, int pollingBound) {
        temp_ps3 = clone(temp_ps3, ps3);
        temp_ps3[2].setD(temp_ps3[2].getD() - (CORRECT_UNIT * (MAX_ADJUST - pollingBound+1)));
        return temp_ps3;
    }


}
