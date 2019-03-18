package example.com.android_example.websocket;

/**
 * @Auther: liuqi
 * @Date: 2019/3/18 13:26
 * @Description: 消息处理接口
 */
public interface MessageHandle {

    void handle(SdpMessage sdpMessage);
}
