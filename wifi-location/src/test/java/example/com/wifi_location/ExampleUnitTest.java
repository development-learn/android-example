package example.com.wifi_location;

import example.com.wifi_location.algorithm.TrilaterationLocationAlgorithm;
import example.com.wifi_location.vo.LocationInfo;
import example.com.wifi_location.vo.PointInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

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

    @Test
    public void algorithm() {
        TrilaterationLocationAlgorithm trilaterationLocationAlgorithm=new TrilaterationLocationAlgorithm();

        PointInfo p1=new PointInfo("w1",3,0,3);
        PointInfo p2=new PointInfo("w2",0,4,4);
        PointInfo p3=new PointInfo("w3",3,4,5);
        PointInfo p4=new PointInfo("w4",0,-9,10);
//        PointInfo p5=new PointInfo("w5",1,0,1);
//        PointInfo p6=new PointInfo("w6",0,2,2);
        PointInfo[] ps=new PointInfo[6];
        ps[0]=p1;
        ps[1]=p2;
        ps[2]=p3;
        ps[3]=p4;
//       ps[4]=p5;
//        ps[5]=p6;
            LocationInfo locationInfo = trilaterationLocationAlgorithm.getLogcation(ps);
            System.out.println("*******x坐标："+locationInfo.getxAxis()+";y坐标："+locationInfo.getyAxis()+"*******");

    }
}