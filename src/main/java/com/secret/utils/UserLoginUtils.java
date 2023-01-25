package com.secret.utils;

import com.secret.constant.RS;
import com.secret.exception.ServiceException;
import com.secret.model.vo.UserVerificationVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserLoginUtils {
    //获得当前HttpServletRequest对象
    protected static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes ;
        try{
            requestAttributes = RequestContextHolder.currentRequestAttributes();
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }catch (Exception e){
            return null;
        }
    }

    //获取heard中的参数
    public static Map<String, String> getRequestHeaderMap() {
        Map<String, String> headerMap = new HashMap<>(8);
        HttpServletRequest request = getRequest();
        if(request == null){
            return  headerMap;
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headerMap.put(name, request.getHeader(name));
        }
        return headerMap;
    }


    public static UserVerificationVo getUserInfo() {
        log.info("get into getUserInfo");
        String token = getRequestHeaderMap().get("token");
        log.info("token {}",token);
        return  getUserInfo(token);
    }

    public static UserVerificationVo getUserInfo(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(RS.LOGIN_FAIL);
        }
        UserVerificationVo user = RedisUtils.get(token, UserVerificationVo.class);
        if (user == null) {
            log.info("user is null token {}", token);
            throw new ServiceException(RS.TOKEN_NOT_FOUNT);
        }
        return user;
    }

    public static UserVerificationVo getUserInfoIsNull() {
        log.info("get into getUserInfo");
        String token = getRequestHeaderMap().get("token");
        log.info("token {}", token);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        UserVerificationVo user = RedisUtils.get(token, UserVerificationVo.class);
        if (user == null) {
            log.info("user is null token {}", token);
            return null;
        }
        return user;
    }

    public static String getToken() {
        return getRequestHeaderMap().get("token");
    }
}
