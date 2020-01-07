package example.com.android_example.service;

import android.content.Context;

import com.google.gson.internal.LinkedTreeMap;

import example.com.android_example.response.ResponseEnum;
import example.com.android_example.response.ResponseVo;
import example.com.android_example.utils.RestTemplateUtil;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
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
            LinkedTreeMap<String,Object> map= (LinkedTreeMap<String, Object>) responseVo.getResult();
            ArrayList<Object> list= (ArrayList<Object>) map.get("records");
            List<String> results = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                LinkedTreeMap<String,Object>  m = (LinkedTreeMap<String, Object>) list.get(i);
                results.add((String) m.get("userName"));
            }
            return results;
        }
        return null;
    }
}
