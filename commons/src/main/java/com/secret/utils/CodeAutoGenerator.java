package com.secret.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatis-Plus 的代码生成器
 *
 * @author Myles Yang
 */
@Slf4j
public class CodeAutoGenerator {
	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		log.info("请输入" + tip + "：");
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotBlank(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	public static void main(String[] args) {
		String moduleName = "secret";
		//表名，多个英文逗号分割
		 String[] tableNamesToGenerate = {
				"s_sys_config"
		 };
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 项目根目录
		String projectPath = "D:\\ideaCode\\secret-room-team\\applet-service";

		//// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setAuthor("chenDi")
				.setOutputDir(projectPath + "/src/main/java")
				.setSwagger2(true)
				// id生成策略
				.setIdType(IdType.AUTO)
				.setOpen(false);
		gc.setEntityName("%sEntity")
				.setControllerName("%sController")
				.setMapperName("%sMapper")
				.setXmlName("%sMapper")
				.setServiceName("%sService")
				.setServiceImplName("%sServiceImpl");
		mpg.setGlobalConfig(gc);


		//// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL)
				.setDriverName("com.mysql.cj.jdbc.Driver")
				.setUrl("jdbc:mysql://127.0.0.1:3306/secret_room?useSSL=false&useUnicode=true"
						+ "&characterEncoding=UTF8&serverTimezone=Asia/Shanghai")
				.setUsername("root")
				.setPassword("123456");
		mpg.setDataSource(dsc);


		//// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(moduleName)
				.setParent("com")
				.setEntity("entity")
				.setController("controller")
				.setMapper("mapper")
				.setXml("mapper.xml")
				.setService("service")
				.setServiceImpl("service.impl");
		mpg.setPackageInfo(pc);


		// 自动填充字段
		List<TableFill> tableFillList = Arrays.asList(
				new TableFill("create_time", FieldFill.INSERT)
				, new TableFill("update_time", FieldFill.INSERT_UPDATE)
		);

		//// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel)
				.setColumnNaming(NamingStrategy.underline_to_camel)
				.setEntityLombokModel(true)
				.setRestControllerStyle(true)
				// 字段自动填充
				.setTableFillList(tableFillList)
				// 逻辑删除字段名
				.setLogicDeleteFieldName("delete_state")
				// 生成字段注解
				.setEntityTableFieldAnnotationEnable(true)
				// 需要生成的表的名字
				.setInclude(tableNamesToGenerate)
				.setTablePrefix("s_")
		// 驼峰转连字符：@RequestMapping("/addUser") -> @RequestMapping("/add-user")
				.setControllerMappingHyphenStyle(false);

		mpg.setStrategy(strategy);
		mpg.execute();
	}
}
