package example.com.wifi_location.algorithm;

import example.com.wifi_location.vo.LocationInfo;
import example.com.wifi_location.vo.PointInfo;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Auther: liuqi
 * @Date: 2019/3/19 16:56
 * @Description: 定位算法
 */
public interface LocationAlgorithm {

    //  定位计算误差最小单位
    double CORRECT_UNIT = 0.01;
    //  最大尝试纠正次数
    int MAX_ADJUST = 10;


    /**
     * 获取当前坐标
     *
     * @param ps 参考设备点集合
     * @return
     */
    default LocationInfo getLogcation(PointInfo... ps) {
        if (!checkPoint(ps)) {
            return null;
        }
        PointInfo[] ps3 = getPoints(ps);
//        System.out.println("**点1--->"+ps3[0].getName()+" **坐标--->"+ps3[0].getX()+","+ps3[0].getY()+" **距离--->"+ps3[0].getD());
//        System.out.println("**点2--->"+ps3[1].getName()+" **坐标--->"+ps3[1].getX()+","+ps3[1].getY()+" **距离--->"+ps3[1].getD());
//        System.out.println("**点3--->"+ps3[2].getName()+" **坐标--->"+ps3[2].getX()+","+ps3[2].getY()+" **距离--->"+ps3[2].getD());
        boolean isNext = false;
        //最大尝试纠正次数
        int pollingBound = MAX_ADJUST;
        LocationInfo locationInfo;
        boolean isX = byDiff(ps3);
        do {
            PointInfo[] temp_ps3 = correctXY(ps3, pollingBound, isX);
            locationInfo = calculate(temp_ps3[0], temp_ps3[1], temp_ps3[2]);
//         返回的坐标不符合三边定位,尝试适当纠正
            isNext = locationInfo.getxAxis() == null || locationInfo.getyAxis() == null || Double.isNaN(locationInfo.getxAxis()) || Double.isNaN(locationInfo.getyAxis()) || Double.isInfinite(locationInfo.getxAxis()) || Double.isInfinite(locationInfo.getyAxis());
            if (isNext) {
                temp_ps3 = correctXY(ps3, pollingBound, !isX);
                locationInfo = calculate(temp_ps3[0], temp_ps3[1], temp_ps3[2]);

            }
            pollingBound--;

        } while (isNext && pollingBound > 0);

        if(!isNext){
            if(locationInfo.getxAxis()==0){
                locationInfo.setxAxis(0.0);
            }
            if(locationInfo.getyAxis()==0){
                locationInfo.setyAxis(0.0);
            }
        }
        return locationInfo;
    }

    /**
     * 通过xy的差值来决定位移那个坐标
     *
     * @param ps3
     * @return
     */
    default boolean byDiff(PointInfo[] ps3) {
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
     * 进行坐标纠正
     *
     * @param ps3
     * @param pollingBound
     * @return
     */
    default PointInfo[] correctXY(PointInfo[] ps3, int pollingBound, boolean isX) {
        PointInfo[] temp_ps3 = new PointInfo[3];
        for (int i = 0; i < ps3.length; i++) {
            PointInfo t_p = new PointInfo(ps3[i].getName(), ps3[i].getX(), ps3[i].getY(), ps3[i].getD());
            temp_ps3[i] = t_p;
        }
        if (pollingBound != MAX_ADJUST) {
            //不是第一次，开始正坐标
            if (isX) {
                temp_ps3[2].setX(temp_ps3[2].getX() - (CORRECT_UNIT * (MAX_ADJUST - pollingBound)));
            } else {
                temp_ps3[2].setY(temp_ps3[2].getY() - (CORRECT_UNIT * (MAX_ADJUST - pollingBound)));
            }
        }
        return temp_ps3;
    }

    /**
     * 获取距离最近的三个设备点
     *
     * @param ps
     * @return
     */
    default PointInfo[] getPoints(PointInfo... ps) {
        PointInfo[] ps3 = new PointInfo[3];
        Collections.sort(Arrays.asList(ps), (o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 1;
            }
            return new Double(o1.getD() - o2.getD()).intValue();
        });
        ps3[0] = ps[0];
        ps3[1] = ps[1];
        ps3[2] = ps[2];
        return ps3;
    }

    /**
     * 根据参考点计算位置
     *
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    LocationInfo calculate(PointInfo p1, PointInfo p2, PointInfo p3);

    /**
     * 检查设备点是否满足定位条件
     *
     * @param ps
     * @return
     */
    default boolean checkPoint(PointInfo... ps) {
        if (ps == null || ps.length < 3) {
            return false;
        }
        return true;
    }

}
