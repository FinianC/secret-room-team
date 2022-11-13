//package com.secretroom.utils;
//
//import com.secretroom.constant.RS;
//import com.secretroom.exception.ServiceException;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class UserUtils {
//    //获得当前HttpServletRequest对象
//    protected static  HttpServletRequest getRequest(){
//        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//    }
//
//    //获取heard中的参数
//    protected static  Map<String, String> getRequestHeaderMap() {
//        HttpServletRequest request = getRequest();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        Map<String, String> headerMap = new HashMap<>(8);
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            headerMap.put(name, request.getHeader(name));
//        }
//        return headerMap;
//    }
//
//
//    public static JmWxUserEntity getUserInfo(){
//        String token = getRequestHeaderMap().get("token");
//        if(StringUtils.isEmpty(token)){
//            throw new ServiceException(RS.LOGIN_FAIL);
//        }
//        Object user=RedisUtils.get(token);
//        if(user==null){
//           throw new ServiceException(RS.TOKEN_NOT_FOUNT);
//        }
//        return (JmWxUserEntity) user;
//    }
//    public static String createToken(){
//        String token = UUID.randomUUID().toString();
//        if(RedisUtils.get(token) != null){
//            return createToken();
//        }
//        return token;
//    }
//
//    public static String getToken(){
//        return getRequestHeaderMap().get("token");
//    }
//}
