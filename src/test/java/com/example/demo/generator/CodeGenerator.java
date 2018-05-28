package com.example.demo.generator;

import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CodeGenerator {
    /**
     * 参见：
     * https://blog.csdn.net/whs_321/article/details/75112803
     */
    private static final String AUTHOR = "syy";//@author
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date

    public static final String BASE_PACKAGE = "com.example.demo";//项目基础包名称，根据自己公司的项目修改
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//Mapper所在包
    public static final String SERVICE_BASE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//Controller所在包

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/boot?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "tju1895";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static final String PROJECT_NAME = "/springboot-mybatis-activiti-demo";
    private static final String JAVA_PATH = PROJECT_NAME + "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = PROJECT_NAME + "/src/main/resources";//资源文件路径

    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + PROJECT_NAME+"/src/test/resources/generator/template";//模板位置

    private static final String PACKAGE_BASE_PATH_SERVICE =
            PROJECT_PATH+
            JAVA_PATH+
            packageConvertPath(SERVICE_BASE_PACKAGE);//生成的Service存放路径

    private static final String PACKAGE_PATH_CONTROLLER =
            PROJECT_PATH+
            JAVA_PATH+
            packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

    public static void main(String[] args) {
//        genController("criteria_test", "","");
//        genController("criteria_test", "","controller-restful.ftl");
//        genModelAndMapper("criteria_test","");
        genCode("fl_logs");

    }
    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     * @param tableNames 数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByCustomModelName(tableName, null);
        }
    }

    public static void genCodeByCustomModelName(String tableName, String modelName){
        genModelAndMapper(tableName, modelName);
        genService(tableName, modelName);
        //"controller-restful.ftl"
//        genController(tableName, modelName,"controller-restful.ftl");
    }
    /**
     *  生成Controller
     * @param tableName
     * @param modelName 自定义的 model
     */
    public static void genController(String tableName, String modelName,String template) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("className", modelNameUpperCamel);
            data.put("entityName", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);
            data.put("servicePackage",SERVICE_BASE_PACKAGE+"."+modelNameUpperCamel);
            data.put("modelPackage",MODEL_PACKAGE);
            String package_path_controller = modelNameUpperCamel+"/";
            File file = new File(PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
//            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));
            template =StringUtils.isEmpty(template)?"controller.ftl":template;
            cfg.getTemplate(template).process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }
    /**
     * 生成service
     * @param tableName  表名
     * @param modelName  自定义的model  可为空
     */
    public static void genService(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("className", modelNameUpperCamel);
            data.put("entityName", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);
            data.put("servicePackage",SERVICE_BASE_PACKAGE+"."+modelNameUpperCamel);
            data.put("modelPackage",MODEL_PACKAGE);
            data.put("mapperPackage",MAPPER_PACKAGE);


            String package_path_service = modelNameUpperCamel+"/";
            File file = new File(PACKAGE_BASE_PATH_SERVICE +package_path_service+ modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            String package_path_service_impl = modelNameUpperCamel+"/impl/";
            File file1 = new File( PACKAGE_BASE_PATH_SERVICE+ package_path_service_impl + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }
    public static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("DB2Tables");
        context.setTargetRuntime("MyBatis3");
//        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
//        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
        commentGenerator.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE,"true");
        commentGenerator.addProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS,"true");
        context.setCommentGeneratorConfiguration(commentGenerator);

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        //model
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
         // 在targetPackage的基础上，根据数据库的schema再生成一层package，最终生成的类放在这个package下，默认为false
        javaModelGeneratorConfiguration.addProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES,"true");
        javaModelGeneratorConfiguration.addProperty(PropertyRegistry.MODEL_GENERATOR_TRIM_STRINGS,"true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        //sqlmap.xml
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mappers");
        sqlMapGeneratorConfiguration.addProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES,"true");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        //dao
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.addProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES,"true");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

//        PluginConfiguration pluginConfiguration = new PluginConfiguration();
//        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
//        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
//        context.addPluginConfiguration(pluginConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)){
            tableConfiguration.setDomainObjectName(modelName);
        }
        //设置相关参数
        tableConfiguration.setCountByExampleStatementEnabled(false);
       // tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            org.mybatis.generator.config.Configuration config = new org.mybatis.generator.config.Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)) modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }
    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }
    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }
    /**
     * t_user -->tUser
     * 转换 实体名
     * @param tableName
     * @return
     */
    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    /**t_user -->TUser
     *转换类名
     * @param tableName
     * @return
     */
    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    /**
     *  com.company.project.model --> /com/company/project/model/
     * @param packageName
     * @return
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     *freemarker 配置
     * @return
     * @throws IOException
     */
    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(Configuration.VERSION_2_3_28);
        //设置模板位置
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }
}
