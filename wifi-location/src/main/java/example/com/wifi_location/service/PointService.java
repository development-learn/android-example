package example.com.wifi_location.service;

import example.com.wifi_location.vo.LocationInfo;
import example.com.wifi_location.vo.PointInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liuqi
 * @Date: 2019/3/19 15:30
 * @Description: 设备服务
 */
public class PointService {

    /**
     * 获取所有的设备信息
     * @return
     */
    public List<PointInfo> getPointInfos(){
        List<PointInfo> pointInfos=new ArrayList<>();
        pointInfos.add(new PointInfo("w1",117.154820,31.84181,10));
        pointInfos.add(new PointInfo("w2",117.155044,31.841772,20));
        pointInfos.add(new PointInfo("w3",117.155439,31.84204,200));

        return pointInfos;
    }
    public LocationInfo getLocation(List<PointInfo> pointInfos){
        if(pointInfos==null||pointInfos.size()<3){
            return null;
        }
        return null;
    }
}
