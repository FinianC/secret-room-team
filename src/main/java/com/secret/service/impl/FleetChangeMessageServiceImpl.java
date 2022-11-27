package com.secret.service.impl;

import com.secret.config.MiniAppBean;
import com.secret.model.dto.TemplateDataDTO;
import com.secret.model.dto.WxTemplateDTO;
import com.secret.model.entity.FleetChangeMessageEntity;
import com.secret.mapper.FleetChangeMessageMapper;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.model.entity.UserEntity;
import com.secret.model.enums.FleetChangesEnum;
import com.secret.model.vo.MotorcadeVo;
import com.secret.model.vo.WxAccessTokenVO;
import com.secret.service.FleetChangeMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.service.MotorcadeService;
import com.secret.service.UserService;
import com.secret.utils.DateUtil;
import com.secret.utils.WxTemplateUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenDi
 * @since 2022-11-26
 */
@Service
public class FleetChangeMessageServiceImpl extends ServiceImpl<FleetChangeMessageMapper, FleetChangeMessageEntity> implements FleetChangeMessageService {

    @Autowired
    private UserService userService;

    @Resource
    private MotorcadeService motorcadeService;

    @Resource
    private MiniAppBean miniAppBean;

    @Override
    @Transactional
    public void fleetChanges(Integer motorcadeId, Integer type,Integer userId) {

        UserEntity userEntity = userService.getById(userId);
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVo(motorcadeId);
        // 如果是成功 则发送成功通知模板
        if(FleetChangesEnum.SUCCESS.getCode().equals(type)){
            FleetChangeMessageEntity fleetChangeMessage = getFleetChangeMessage(FleetChangesEnum.JOIN.getCode(), userEntity, motorcadeVo);
            save(fleetChangeMessage);
            FleetChangeMessageEntity success = getFleetChangeMessage(type, userEntity, motorcadeVo);
            save(success);
            // todo 发送通知模板

//            sendTemplateToUsers()


            return;
        }
        FleetChangeMessageEntity fleetChangeMessage = getFleetChangeMessage(type, userEntity, motorcadeVo);
        save(fleetChangeMessage);
    }

   public FleetChangeMessageEntity getFleetChangeMessage(Integer code, UserEntity userEntity,MotorcadeVo motorcadeVo){
       // 保存成员加入或离开信息
       String format = MessageFormat.format(FleetChangesEnum.getMessage(code), userEntity.getNickname(), motorcadeVo.getTitle());
       FleetChangeMessageEntity fleetChangeMessageEntity = new FleetChangeMessageEntity();
       fleetChangeMessageEntity.setMessage(format);
       fleetChangeMessageEntity.setMotorcadeId(motorcadeVo.getId());
       fleetChangeMessageEntity.setUserId(motorcadeVo.getCreateUser());
       fleetChangeMessageEntity.setType(code);
       return fleetChangeMessageEntity;
    }

    public void sendTemplateToUsers(String formId ,String openId,MotorcadeVo motorcadeVo) {

        // 获取accessToken
        WxAccessTokenVO wxAccessTokenVO = WxTemplateUtil.getAccessToken(miniAppBean.getAppId(), miniAppBean.getAppSecret());

        // 消息模板对象
        WxTemplateDTO wxTemplateDTO = new WxTemplateDTO();

        // 用户openId
        wxTemplateDTO.setTouser(openId);
        // 表单ID （需要小程序前端传递）
        wxTemplateDTO.setForm_id(formId);
        // 模板ID
        wxTemplateDTO.setTemplate_id(miniAppBean.getSuccessTemplateId());
        // 跳转页面设置（仅限上线后的小程序，测试时可以忽略此属性）
        wxTemplateDTO.setPage("pages/****/****");

        Map<String, TemplateDataDTO> data = new HashMap<>();
        data.put("发起人", new TemplateDataDTO(motorcadeVo.getNickname(), "#173177"));
        data.put("车队主题", new TemplateDataDTO(motorcadeVo.getTypeName().toString(), "#173177"));
        data.put("车队名称", new TemplateDataDTO(motorcadeVo.getTitle(), "#173177"));
        data.put("发车时间", new TemplateDataDTO(DateUtil.DateToStrNotSS(motorcadeVo.getCompetitionDate()), "#173177"));
        int count = motorcadeVo.getJoinedMotorcadeVos() != null ? motorcadeVo.getJoinedMotorcadeVos().size() + 1 : 1;
        data.put("组队人数", new TemplateDataDTO(String.valueOf(count), "#173177"));
        // 模板内容
        wxTemplateDTO.setData(data);
        // 发送消息模板
        WxTemplateUtil.sendTemplateMsg(wxTemplateDTO, wxAccessTokenVO.getAccessToken());

    }
}
