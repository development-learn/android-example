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

    public PointInfo(String name, double x, double y, double d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.name = name;
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
}
