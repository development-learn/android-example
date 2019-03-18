package example.com.android_example.service;

import example.com.android_example.response.ResponseVo;
import example.com.android_example.utils.RestTemplateUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @Auther: liuqi
 * @Date: 2019/3/13 16:09
 * @Description:
 */
public class LoginService {

    public ResponseVo login(String userName, String password) {
        LinkedMultiValueMap<String, String> loginMap = new LinkedMultiValueMap<>();

        loginMap.add("username", userName);
        loginMap.add("password", password);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        return RestTemplateUtil.post(RestTemplateUtil.LOGIN, loginMap, httpHeaders);

    }
}
