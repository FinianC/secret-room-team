package com.secret.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.mapper.MotorcadeMapper;
import com.secret.model.params.MotorcadeParam;
import com.secret.model.params.MotorcadeQueryParam;
import com.secret.model.vo.*;
import com.secret.service.MotorcadeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 车队 服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@Service
public class MotorcadeServiceImpl extends ServiceImpl<MotorcadeMapper, MotorcadeEntity> implements MotorcadeService {

    @Autowired
    private MotorcadeMapper motorcadeMapper;

    @Override
    public MotorcadeVo getMotorcadeVoById(Integer id) {
        MotorcadeVo motorcadeVoById = motorcadeMapper.getMotorcadeVoById(id);
        return motorcadeVoById;
    }

    @Override
    public Page<MotorcadeVo> getMotorcadeVoPage(MotorcadeQueryParam motorcadeQueryParam) {
        UserVerificationVo<UserVo> userInfoIsNull = UserLoginUtils.getUserInfoIsNull();
        Page page = new Page<>(motorcadeQueryParam.getCurrent(), motorcadeQueryParam.getPageSize());
        Page<MotorcadeVo> motorcadeVoPage = motorcadeMapper.getMotorcadeVoPage(page, motorcadeQueryParam);
        List<MotorcadeVo> records = motorcadeVoPage.getRecords();
        if(userInfoIsNull!=null){
            UserVo user = userInfoIsNull.getUser();
            records.forEach( re -> {
                setJoin(user, re);
            } );
        }

        return motorcadeVoPage;
    }

    private void setJoin(UserVo user, MotorcadeVo re) {
        List<JoinedMotorcadeVo> joinedMotorcadeVos = re.getJoinedMotorcadeVos();
        joinedMotorcadeVos.forEach( join -> {
           if( join.getUserId().equals(user.getId())){
               re.setJoined(1);
               return;
           }
        } );
        if(re.getJoined() == null){
            re.setJoined(0);
        }
    }

    @Override
    public MotorcadeVo getMotorcadeVo(Integer id) {
        UserVo user =(UserVo)UserLoginUtils.getUserInfo().getUser();
        MotorcadeVo motorcadeVo = motorcadeMapper.getMotorcadeVo(id);
        setJoin(user, motorcadeVo);

        return motorcadeVo;
    }

    /**
     * 获取聊天列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ChatListVo> getChatListVo(Integer userId) {



        return null;
    }
}
