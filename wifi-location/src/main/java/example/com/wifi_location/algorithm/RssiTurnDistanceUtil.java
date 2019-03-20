package example.com.wifi_location.algorithm;

/**
 * @Auther: liuqi
 * @Date: 2019/3/20 17:13
 * @Description: 信号转距离工具类
 */
public class RssiTurnDistanceUtil {
    //    默认发射端和接收端相隔1米时的信号强度
    public static int A = 45;
    //    默认环境衰减因子
    public static int N = 4;

    /**
     * rssi 转距离
     *
     * @param rssi 信号强度
     * @param a    相隔一米信号强度
     * @param n    环境衰减因子
     * @return 返回保留两位小数的距离
     */
    public static double getDistance(int rssi, int a, int n) {
        if (n == 0) {
            n = N;
        }
        if (a == 0) {
            a = A;
        }
        return (double) Math.round((Math.pow(10, ((double) (Math.abs(rssi) - a)) / (10 * n))) * 100) / 100;
    }
}
