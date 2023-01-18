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

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * MyBatis-Plus 的代码生成器
 *
 * @author Myles Yang
 */
public class CodeAutoGenerator {
	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入" + tip + "：");
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
//		String[] tableNamesToGenerate = scanner("表名，多个英文逗号分割").split(",");
		 String[] tableNamesToGenerate = {
				"s_theme"
		 };
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 项目根目录
		String projectPath = "E:\\idea_code\\card-voucher\\secret-room-team";

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
				.setPassword("1804123028");
		mpg.setDataSource(dsc);


		//// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(moduleName)
				.setParent("com")
				.setEntity("model.entity")
				.setController("controller")
				.setMapper("mapper")
				.setXml("mapper.xml")
				.setService("service")
				.setServiceImpl("service.impl");
		mpg.setPackageInfo(pc);

//		// --------------------自定义配置----------------
//		InjectionConfig cfg = new InjectionConfig() {
//			@Override
//			public void initMap() {
//				// to do nothing
//			}
//		};
//
//		// 如果模板引擎是 velocity
//		String templatePath = "/templates/mapper.xml.vm"; //自带的模板之一
//		// 自定义输出配置
//		List<FileOutConfig> focList = new ArrayList<>();
//		// 自定义配置会被优先输出（自定义文件输出配置）
//		focList.add(new FileOutConfig(templatePath) {
//			@Override
//			public String outputFile(TableInfo tableInfo) {
//				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//				return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//						+ "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//			}
//		});
//
//		cfg.setFileOutConfigList(focList);
//		mpg.setCfg(cfg);

//		// -----------------配置模板-----------------------------
//		TemplateConfig templateConfig = new TemplateConfig();
//
//		// 把已有的xml生成置空（xml位置我们一般放在resource目录下，但默认会在mapper，service等同级目录下生成）
//		templateConfig.setXml(null);
//		mpg.setTemplate(templateConfig);


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
