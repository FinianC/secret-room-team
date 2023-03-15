package com.secret.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.secret.utils.UserLoginUtils;
import com.secret.vo.applet.UserVerificationVo;
import com.secret.vo.applet.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;

@Configuration
@EnableTransactionManagement
@MapperScan("com.secret.mapper")
public class MyBatisPlusConfig {

	/**
	 * MybatisPlus 拦截器
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 注册分页插件
		PaginationInnerInterceptor pii = new PaginationInnerInterceptor(DbType.MYSQL);
		// 最大单页限制数量
		pii.setMaxLimit(100L);

		interceptor.addInnerInterceptor(pii);
		return interceptor;
	}


	@Slf4j
	@Component
	public static class MyMetaObjectHandler implements MetaObjectHandler {
		@Override
		public void insertFill(MetaObject metaObject) {
			Integer userId = getUserId();

			log.info("start insert fill ....");
			this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
			this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
			this.strictInsertFill(metaObject, "createUser", Integer.class, userId);
			this.strictInsertFill(metaObject, "updateUser", Integer.class, userId);
		}
		@Override
		public void updateFill(MetaObject metaObject) {
			Integer userId = getUserId();
			log.info("start update fill ....");
			this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
			this.strictInsertFill(metaObject, "updateUser", Integer.class, userId);
		}

		public Integer  getUserId() {
			UserVerificationVo userInfoIsNull = UserLoginUtils.getUserInfoIsNull();
			if(userInfoIsNull == null ){
				return 1;
			}
			Object user = userInfoIsNull.getUser();
			if(user instanceof UserVo){
				UserVo userVo = (UserVo)user;
				return userVo.getId();
			}
			return 1;
		}
	}

}
