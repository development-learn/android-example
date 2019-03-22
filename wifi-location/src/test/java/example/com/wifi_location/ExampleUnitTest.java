package example.com.wifi_location;

import example.com.wifi_location.algorithm.RssiTurnDistanceUtil;
import example.com.wifi_location.algorithm.TrilaterationLocationAlgorithm;
import example.com.wifi_location.vo.LocationInfo;
import example.com.wifi_location.vo.PointInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**
     * 定位计算测试
     */
    @Test
    public void testLocationAlgorithm() {
        TrilaterationLocationAlgorithm trilaterationLocationAlgorithm = new TrilaterationLocationAlgorithm();

        LocationInfo locationInfo = trilaterationLocationAlgorithm.getLogcation(create());
        System.out.println("*******x坐标：" + locationInfo.getxAxis() + ";y坐标：" + locationInfo.getyAxis() + "*******");

    }

    /**
     * rssi距离计算测试
     */
    @Test
    public void testDistance() {
        int rssi = -80;
        int a = 40;
        int n = 4;

        System.out.println("*******rssi：" + rssi + ";相隔1米强度：" + a + ";环境衰减因子：" + n + ";计算距离：" + RssiTurnDistanceUtil.getDistance(rssi, a, n) + "*******");

    }

    /**
     * x 轴一条直线
     *
     * @return
     */
    private PointInfo[] createX() {
        PointInfo[] ps = new PointInfo[3];
        ps[0] = new PointInfo("w1", 3, 0, 3);
        ps[1] = new PointInfo("w2", 4, 0, 4);
        ps[2] = new PointInfo("w3", 5, 0, 5);
        return ps;
    }

    /**
     * Y 轴一条直线
     *
     * @return
     */
    private PointInfo[] createY() {
        PointInfo[] ps = new PointInfo[3];
        ps[0] = new PointInfo("w1", 0, 3, 3);
        ps[1] = new PointInfo("w2", 0, 4, 4);
        ps[2] = new PointInfo("w3", 0, 5, 5);
        return ps;
    }

    /**
     * 不在一条直线
     *
     * @return
     */
    private PointInfo[] create() {
        PointInfo[] ps = new PointInfo[3];
        ps[0] = new PointInfo("w1", 0, 300, 300);
        ps[1] = new PointInfo("w2", 0, 400, 400);
        ps[2] = new PointInfo("w3", 300, 400, 500);
        return ps;
    }
}