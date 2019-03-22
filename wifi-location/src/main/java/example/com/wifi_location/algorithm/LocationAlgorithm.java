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
        LocationInfo locationInfo= calculate(getPoints(ps));
        if(isOk(locationInfo)){
            if(locationInfo.getxAxis()==0){
                locationInfo.setxAxis(0.0);
            }
            if(locationInfo.getyAxis()==0){
                locationInfo.setyAxis(0.0);
            }
            locationInfo.setxAxis((double) Math.round((locationInfo.getxAxis()) * 100) / 100);
            locationInfo.setyAxis((double) Math.round((locationInfo.getyAxis()) * 100) / 100);

        }
        return locationInfo;
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
     * @param ps3 三个设备点
     * @return
     */
    LocationInfo calculate(PointInfo[] ps3);

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

    /**
     * 检查定位结果是否合理
     *
     * @param locationInfo
     * @return
     */
    default boolean isOk(LocationInfo locationInfo) {
        return !(locationInfo.getxAxis() == null || locationInfo.getyAxis() == null || Double.isNaN(locationInfo.getxAxis()) || Double.isNaN(locationInfo.getyAxis()) || Double.isInfinite(locationInfo.getxAxis()) || Double.isInfinite(locationInfo.getyAxis()));
    }

    /**
     * 深度克隆
     *
     * @param temp_ps3
     * @param ps3
     * @return
     */
    default PointInfo[] clone(PointInfo[] temp_ps3, PointInfo[] ps3) {
        for (int i = 0; i < ps3.length; i++) {
            PointInfo t_p = new PointInfo(ps3[i].getName(), ps3[i].getX(), ps3[i].getY(), ps3[i].getD());
            temp_ps3[i] = t_p;
        }
        return temp_ps3;
    }
}
