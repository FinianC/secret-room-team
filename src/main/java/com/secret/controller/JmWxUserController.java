//package com.secretroom.controller;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.secretroom.model.constant.WxConstant;
//import com.secretroom.model.entity.JmWxUserEntity;
//import com.secretroom.utils.RedisUtils;
//import com.secretroom.utils.UserUtils;
//import com.github.kevinsawicki.http.HttpRequest;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.net.URLEncoder;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author wenxinji
// * @since 2022-09-27
// */
//@RestController
//@ApiOperation("用户管理")
//@RequestMapping("/card/jm-wx-user")
//public class JmWxUserController{
//    @Autowired
//    JmWxUserService jmWxUserService;
//    @Value("${spring.redirect_uri}")
//    private String sym ;
//
//
//
//    @ApiOperation(value = "微信登录接口")
//    @GetMapping("wx_login")
//    public void wxLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        //这里是回调的url
//        String redirect_uri = URLEncoder.encode(sym+"/card/jm-wx-user/callBack?uri="+request.getParameter("url"), "UTF-8");
//        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
//                "appid=APPID" +
//                "&redirect_uri=REDIRECT_URI"+
//                "&response_type=code" +
//                "&scope=SCOPE" +
//                "&state=123#wechat_redirect";
//        response.sendRedirect(url.replace("APPID",WxConstant.APP_ID).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo"));
//    }
//
////    @ApiOperation(value = "微信code登录", notes = "用户登录信息在header里面获取", httpMethod = "POST")
////    @PostMapping("/getOpenId")
////    public R<UserVerificationVo<UserVo>> getOpenId(@RequestBody UserGetOpenIdParam userGetOpenIdParam) throws Exception {
////        JSONObject jsonObject = new JSONObject();
////        if (!isDebug) {
////            jsonObject = WechatUtil.getOpenid(wxConfiguration.getAppId(), wxConfiguration.getAppSecret(), userGetOpenIdParam.getCode());
////        } else {
////            jsonObject.put("openid", openid);
////        }
////        String openId = jsonObject.getString("openid");
////        if (StringUtils.isEmpty(openId)) {
////            log.error("getOpenid error {}",JSONObject.toJSONString(jsonObject));
////            return R.fail(RSEnum.MENU_NOT_FOUNT);
////        }
////        UserEntity user = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getOpenid, openId));
////        if (user == null) {
////            user = new UserEntity();
////            BeanUtils.copyProperties(userGetOpenIdParam, user);
////            user.setOpenid(openId);
////            userService.save(user);
////
////            //用户余额账户
////            AccountEntity accountEntity = new AccountEntity();
////            accountEntity.setAccountCode("YE" + TimeUtil.defLocalDateTimeToStr());
////            accountEntity.setAccountKey(AccountEnum.MEMBER_BALANCE_ACCOUNT.getCode());
////            accountEntity.setAccountName(AccountEnum.MEMBER_BALANCE_ACCOUNT.getMessage());
////            accountEntity.setBalance(new BigDecimal("0"));
////            accountEntity.setUserId(user.getId());
////            accountService.save(accountEntity);
////
////            //添加赠送账户
////            accountEntity = new AccountEntity();
////            accountEntity.setAccountCode("ZS" + TimeUtil.defLocalDateTimeToStr());
////            accountEntity.setAccountKey(AccountEnum.MEMBER_GIVE_ACCOUNT.getCode());
////            accountEntity.setAccountName(AccountEnum.MEMBER_GIVE_ACCOUNT.getMessage());
////            accountEntity.setBalance(new BigDecimal("0"));
////            accountEntity.setUserId(user.getId());
////            accountService.save(accountEntity);
////
////            //添加卡券账户
////            accountEntity = new AccountEntity();
////            accountEntity.setAccountCode("KQ" + TimeUtil.defLocalDateTimeToStr());
////            accountEntity.setAccountKey(AccountEnum.MEMBER_CARD_ACCOUNT.getCode());
////            accountEntity.setAccountName(AccountEnum.MEMBER_CARD_ACCOUNT.getMessage());
////            accountEntity.setBalance(new BigDecimal("0"));
////            accountEntity.setUserId(user.getId());
////            accountService.save(accountEntity);
////        }
////        String token = RedisUtils.get(openId, String.class);
////        if (StringUtils.isEmpty(token)) {
////            token = RedisUtils.createToken();
////            UserVerificationVo<UserVo> userVerificationVo = new UserVerificationVo<UserVo>();
////            UserVo userVo = new UserVo();
////            BeanUtils.copyProperties(user, userVo);
////            userVerificationVo.setUser(userVo);
////            userVerificationVo.setToken(token);
////            userVerificationVo.setIsAuthentication(true);
////            RedisUtils.set(token, userVerificationVo, 86400);
////            RedisUtils.set(openId, token, 86400);
////        }
////        return R.success(RedisUtils.get(token, UserVerificationVo.class));
////    }
//
//
//    @ApiOperation(value = "微信授权回调接口")
//    @GetMapping("/callBack")
//    public void callBack(HttpServletRequest request, HttpServletResponse response)throws Exception {
//        //获取回调地址中的code
//        String code = request.getParameter("code");
//        String uri = request.getParameter("uri");
//        //拼接url
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxConstant.APP_ID + "&secret="
//                + WxConstant.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
//        JSONObject jsonObject = JSONObject.parseObject(HttpRequest.get(url).body());
//        //1.获取微信用户的openid
//        String openid = jsonObject.getString("openid");
//        if(StringUtils.isEmpty(openid)||"null".equals(openid)){
//            response.sendRedirect("/index.html");
//            return;
//        }
//        JmWxUserEntity jmWxUserEntity=jmWxUserService.getOne(new LambdaQueryWrapper<JmWxUserEntity>().eq(JmWxUserEntity::getOpenId,openid));
//        if(jmWxUserEntity==null){
//            jmWxUserEntity=new JmWxUserEntity();
//            jmWxUserEntity.setUserBalance(new BigDecimal(0));
//            jmWxUserEntity.setOpenId(openid);
//            jmWxUserService.save(jmWxUserEntity);
//        }
//        String token=null;
//        if(RedisUtils.get(openid)==null||
//                StringUtils.isEmpty((CharSequence) RedisUtils.get(openid))||
//                "null".equals(RedisUtils.get(openid))){
//            token = UserUtils.createToken();
//            RedisUtils.set(token , jmWxUserEntity , 86400);
//            RedisUtils.set(openid , token , 86400);
//        }
//        token = (String) RedisUtils.get(openid);
//        if(StringUtils.isEmpty(uri)||"null".equals(uri)){
//            response.sendRedirect("/index.html?token="+token);
//            return;
//        }
//        response.sendRedirect("/#/"+uri+"?token="+token);
//    }
//
//    @ApiOperation(value = "获取用户信息")
//    @PostMapping("/get-uset")
//    @ResponseBody
//    public JmWxUserEntity getUser()throws Exception{
//        return UserUtils.getUserInfo();
//    }
//
//}
//
