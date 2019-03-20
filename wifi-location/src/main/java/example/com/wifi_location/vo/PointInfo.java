package example.com.wifi_location.vo;

/**
 * @Auther: liuqi
 * @Date: 2019/3/19 14:52
 * @Description: 设备点信息
 */
public class PointInfo {

    //  设备唯一名称
    private String name;

    //   x
    private double x;
    //   y
    private double y;
    //   距离
    private double d;
    //信号强度
    private double rssi;

    /**
     * 构造函数，设置设备点
     * @param name
     * @param x
     * @param y
     */
    public PointInfo(String name, double x, double y) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * 构造函数，设置实时设备信息
     * @param rssi
     * @param d
     * @param name
     */
    public PointInfo(double rssi,double d,String name) {
        this.d = d;
        this.rssi=rssi;
        this.name = name;
    }
    public PointInfo(String name, double x, double y,double d) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.d=d;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getRssi() {
        return rssi;
    }

    public void setRssi(double rssi) {
        this.rssi = rssi;
    }
}
