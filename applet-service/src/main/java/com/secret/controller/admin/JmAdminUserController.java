//package com.secretroom.controller.admin;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.secretroom.constant.RS;
//import com.secretroom.model.entity.JmAdminEntity;
//import com.secretroom.model.params.LoginParam;
//import com.secretroom.model.vo.R;
//import com.secretroom.utils.MyMD5Util;
//import com.secretroom.utils.RedisUtils;
//import com.secretroom.utils.UserUtils;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@ApiOperation("管理员用户")
//@RequestMapping("/admin/card/jm-user")
//public class JmAdminUserController {
//    @Autowired
//    JmAdminService jmAdminService;
//    @ApiOperation(value = "管理员登陆")
//    @PostMapping("/login")
//    public R login(@RequestBody LoginParam user)  {
//        JmAdminEntity jmAdminEntity=jmAdminService.getOne(new LambdaQueryWrapper<JmAdminEntity>()
//                .eq(JmAdminEntity::getUserName,user.getUserName()));
//        if(jmAdminEntity==null){
//            return R.fail(RS.USER_NAME_ERROR);
//        }
//        String pass=jmAdminEntity.getPassWord();
//        String userMd5Pass=MyMD5Util.getMD5(user.getPassWord());
//        if(pass==null || !(pass.equals(userMd5Pass))){
//            return R.fail(RS.PASS_WORD_ERROR);
//        }
//        String token = (String) RedisUtils.get("admin_login_user_"+jmAdminEntity.getAdminId());
//        if(StringUtils.isEmpty(token)){
//            token = UserUtils.createToken();
//            RedisUtils.set(token,jmAdminEntity,86400);
//            RedisUtils.set("admin_login_user_"+jmAdminEntity.getAdminId(),token,86400);
//        }
//        return R.success(token);
//    }
//
//
//}