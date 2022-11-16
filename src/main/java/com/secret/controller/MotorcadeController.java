package com.secret.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.constant.RS;
import com.secret.model.entity.MotorcadeEntity;
import com.secret.model.params.MotorcadeModifyParam;
import com.secret.model.params.MotorcadeParam;
import com.secret.model.params.MotorcadeQueryParam;
import com.secret.model.vo.MotorcadeVo;
import com.secret.model.vo.R;
import com.secret.model.vo.UserVerificationVo;
import com.secret.model.vo.UserVo;
import com.secret.service.MotorcadeService;
import com.secret.utils.TransferUtils;
import com.secret.utils.UserLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 车队 前端控制器
 * </p>
 *
 * @author chenDi
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/secret/motorcade")
public class MotorcadeController {

    @Autowired
    private MotorcadeService motorcadeService;

    @ApiOperation(value = "发布车队信息", httpMethod = "POST")
    @PostMapping("/user/add")
    public R<MotorcadeVo> updateInformation(@RequestBody MotorcadeParam motorcadeParam) {
        UserVo user = (UserVo)UserLoginUtils.getUserInfo().getUser();
        Assert.isTrue(user.getRole() == 2, RS.NO_PUBLISHING_PERMISSION.message());
        MotorcadeEntity motorcadeEntity = new MotorcadeEntity();
        TransferUtils.transferBean(motorcadeParam,motorcadeEntity);
        motorcadeService.save(motorcadeEntity);
        MotorcadeEntity byId = motorcadeService.getById(motorcadeEntity.getId());
        MotorcadeVo motorcadeVo = new MotorcadeVo();
        TransferUtils.transferBean(byId,motorcadeVo);
        return R.success(motorcadeVo);
    }

    @ApiOperation(value = "删除车队信息")
    @GetMapping("/user/delete/{id}")
    public R<Integer> delete(@PathVariable String id) {
        motorcadeService.removeById(id);
        return R.success(id);
    }
    @ApiOperation(value = "更新车队信息", httpMethod = "POST")
    @PostMapping("/user/update")
    public R<MotorcadeVo> update(@RequestBody MotorcadeModifyParam motorcadeModifyParam) {
        MotorcadeEntity motorcadeEntity = new MotorcadeEntity();
        TransferUtils.transferBean(motorcadeModifyParam,motorcadeEntity);
        motorcadeService.updateById(motorcadeEntity);
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVoById(motorcadeEntity.getId());
        return R.success(motorcadeVo);
    }

    @ApiOperation(value = "分页查询大厅车队信息", httpMethod = "POST")
    @PostMapping("/page")
    public R<Page<MotorcadeVo>> page(@RequestBody MotorcadeQueryParam motorcadeQueryParam) {
        Page<MotorcadeVo> motorcadeVoPage = motorcadeService.getMotorcadeVoPage(motorcadeQueryParam);
        return R.success(motorcadeVoPage);
    }

    @ApiOperation(value = "车队详情", httpMethod = "POST")
    @PostMapping("/detail/{id}")
    public R<Page<MotorcadeVo>> detail( @PathVariable Integer id) {
        MotorcadeVo motorcadeVo = motorcadeService.getMotorcadeVo(id);
        return R.success(motorcadeVo);
    }

}

