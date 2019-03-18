package example.com.android_example.service;

import android.content.Context;
import example.com.android_example.response.ResponseEnum;
import example.com.android_example.response.ResponseVo;
import example.com.android_example.utils.RestTemplateUtil;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

/**
 * @Auther: liuqi
 * @Date: 2019/3/13 15:35
 * @Description:
 */
public class MyFriendsService {
    /**
     * 好友列表
     *
     * @param context
     * @return
     */
    public List<String> myFriends(Context context) {
        LinkedMultiValueMap<String, String> loginMap = new LinkedMultiValueMap<>();
        ResponseVo responseVo = RestTemplateUtil.postWithJwt(context, RestTemplateUtil.MY_FRIENDS, loginMap);
        if (responseVo.getCode() == ResponseEnum.SUCCESS.getCode()) {
            return (List<String>) responseVo.getResult();
        }
        return null;
    }
}
