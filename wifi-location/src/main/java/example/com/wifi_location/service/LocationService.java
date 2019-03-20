package example.com.wifi_location.service;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import example.com.wifi_location.algorithm.RssiTurnDistanceUtil;
import example.com.wifi_location.vo.PointInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: liuqi
 * @Date: 2019/3/19 15:30
 * @Description: 定位服务
 */
public class LocationService {
    private static List<PointInfo> pointInfoList = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param context
     */
    public LocationService(Context context) {
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING) {
                wifiManager.setWifiEnabled(true);
            }
        }
        init();

    }

    /**
     * 应用启动时加载
     * 已管控设备点坐标信息
     *
     * @return
     */
    public void init() {
        // TODO: 2019/3/20 后面需要改为先从手机获取，如果没有再从远端服务拉取
        pointInfoList.add(new PointInfo("  小米共享WiFi_DF1A", 3, 4));
        pointInfoList.add(new PointInfo("MIWIFI", 3, 5));
        pointInfoList.add(new PointInfo("simpo07-test", 6, 7));
        pointInfoList.add(new PointInfo("simpo04-south", 6, 8));
    }

    //wifi 管理类
    private WifiManager wifiManager;

    public WifiManager getWifiManager() {
        return wifiManager;
    }

    /**
     * 获取当前位置与设备点的信息
     *
     * @return
     */
    public List<PointInfo> getPointInfos() {
        List<PointInfo> pointInfos = new ArrayList<>();
        wifiManager.startScan();
        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult result : scanResults) {
            pointInfos.add(new PointInfo( result.level,RssiTurnDistanceUtil.getDistance(result.level,0,0),result.SSID));
        }

        return filter(pointInfos);
    }

    /**
     * 过滤并排序已管控的设备点信息
     *
     * @param pointInfos
     * @return
     */
    private List<PointInfo> filter(List<PointInfo> pointInfos) {
        List<PointInfo> pps = new ArrayList<>();
        for (PointInfo p : pointInfos) {
            PointInfo pp = notFind(p);
            if (pp != null) {
                p.setX(pp.getX());
                p.setY(pp.getY());
                pps.add(p);
            }
        }
        Collections.sort(pps, (o1, o2) -> {
            if(o2.getRssi() - o1.getRssi()>0){
                return 1;
            }else if(o2.getRssi() - o1.getRssi()==0){
                return 0;
            }
            else{
                return -1;
            }
        });
        return pps;
    }

    /**
     * 查找新的设备点是否属于管控设备
     *
     * @param p
     * @return
     */
    private PointInfo notFind(PointInfo p) {
        for (PointInfo pp : pointInfoList) {
            if (pp.getName().equals(p.getName())) {
                return pp;
            }
        }
        return null;
    }


}
