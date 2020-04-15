//package com.feiniaojin.earlyhome.generator.biz.core;
//
//import com.google.gson.Gson;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.util.List;
//
//@Component
//@Slf4j
//public class CoreGenerator {
//
//    @Resource
//    private JdbcComponent jdbcComponent;
//
//    private Gson gson = new Gson();
//
//
//
//    @Resource
//    private Configuration configuration;
//
//    public void generate(String outputDirectoryPath) {
//
//        try {
//
//            String basePath = outputDirectoryPath.substring(0, outputDirectoryPath.indexOf("target")) + "src/main/java/";
//
//            //1. 加载eden.json
//            if (log.isDebugEnabled()) {
//                log.debug("Start to load eden.json");
//            }
//            BizGeneratorConfig edenConfig = loadJsonConfig(outputDirectoryPath);
//            edenConfig.setBasePath(basePath);
//            if (log.isDebugEnabled()) {
//                log.debug("finished loading eden.json, eden.json=[{}]", gson.toJson(edenConfig));
//            }
//
//            List<BizGeneratorConfig.Entity> entities = edenConfig.getEntities();
//
//            for (BizGeneratorConfig.Entity entity : entities) {
//                generateEachEntity(edenConfig, entity);
//            }
//            //5. 创建文件写磁盘
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void generateEachEntity(BizGeneratorConfig edenConfig, BizGeneratorConfig.Entity entity) throws Exception {
//
//        //2. 加载数据库表的元信息
//        if (log.isDebugEnabled()) {
//            log.debug("Start to load table meta");
//        }
//        List<MetaProperty> metaProperties = jdbcComponent.tableMeta(entity.getTableName());
//        if (log.isDebugEnabled()) {
//            log.debug("finished loading table meta, metaProperties=[{}]", gson.toJson(metaProperties));
//        }
//
//        //3. 创建实体的配置
//        if (log.isDebugEnabled()) {
//            log.debug("Start to create entityConfig");
//        }
//        EntityConfig entityConfig = createEntityConfig(entity, metaProperties);
//        entityConfig.setBasePath(edenConfig.getBasePath());
//        entityConfig.setBasePackage(edenConfig.getBasePackage());
//
//        if (log.isDebugEnabled()) {
//            log.debug("Finished  creating entityConfig, entityConfig=[{}]", gson.toJson(entityConfig));
//        }
//
////            generateEntity(entityConfig);
//        generateDTO(entityConfig);
//
//        generateService(entityConfig);
//
//        generateServiceImpl(entityConfig);
//
//        generateController(entityConfig);
//
//        log.debug("");
//    }
//
//    private void generateController(EntityConfig entityConfig) throws Exception {
//        //4. 加载模板，生成代码
//        Template template = configuration.getTemplate("controller.ftl");
//
//        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityConfig);
//
//        StringBuilder builder = new StringBuilder();
//
//        builder.append(entityConfig.getBasePath());
//
//        builder.append(getPackagePath(entityConfig));
//
//        String fileName = builder.toString() + "/controller/" + entityConfig.getEntity() + "Controller.java";
//
//        FileUtils.write(new File(fileName), text, "UTF-8");
//    }
//
//    private void generateServiceImpl(EntityConfig entityConfig) throws Exception {
//
//        //4. 加载模板，生成代码
//        Template template = configuration.getTemplate("serviceimpl.ftl");
//
//        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityConfig);
//
//        StringBuilder builder = new StringBuilder();
//
//        builder.append(entityConfig.getBasePath());
//
//        builder.append(getPackagePath(entityConfig));
//
//        String fileName = builder.toString() + "/service/impl/" + entityConfig.getEntity() + "ServiceImpl.java";
//
//        FileUtils.write(new File(fileName), text, "UTF-8");
//    }
//
//    private void generateEntity(EntityConfig entityConfig) throws Exception {
//
//    }
//
//    private void generateService(EntityConfig entityConfig) throws Exception {
//
//        //4. 加载模板，生成代码
//        Template template = configuration.getTemplate("service.ftl");
//
//        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityConfig);
//
//        StringBuilder builder = new StringBuilder();
//
//        builder.append(entityConfig.getBasePath());
//
//        builder.append(getPackagePath(entityConfig));
//
//        String fileName = builder.toString() + "/service/" + entityConfig.getEntity() + "Service.java";
//
//        FileUtils.write(new File(fileName), text, "UTF-8");
//    }
//
//    private void generateDTO(EntityConfig entityConfig) throws IOException, TemplateException {
//
//        //4. 加载模板，生成代码
//        Template template = configuration.getTemplate("dto.ftl");
//
//        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityConfig);
//
//        StringBuilder builder = new StringBuilder();
//
//        builder.append(entityConfig.getBasePath());
//
//        builder.append(getPackagePath(entityConfig));
//
//        String fileName = builder.toString() + "/dto/" + entityConfig.getEntity() + "DTO.java";
//
//        FileUtils.write(new File(fileName), text, "UTF-8");
//    }
//
//    private String getPackagePath(EntityConfig entityConfig) {
//
//        String basePackage = entityConfig.getBasePackage();
//
//        return basePackage.replace(".", "/");
//    }
//
//    private String getBasePath() throws IOException {
//
//        URL resource = Thread.currentThread().getContextClassLoader().getResource(".");
//
//        System.out.println("resource=" + resource);
//
//        File f = new File(resource.getPath());
//
//        String canonicalPath = f.getCanonicalPath().replaceAll("\\\\", "/");
//
//        return canonicalPath.substring(0, canonicalPath.indexOf("target")) + "src/main/java/";
//    }
//
//    private EntityConfig createEntityConfig(BizGeneratorConfig.Entity entity, List<MetaProperty> metaProperties) {
//
//        EntityConfig entityConfig = new EntityConfig();
//
//        entityConfig.setMetaProperties(metaProperties);
//
//        entityConfig.setControllerPath(entity.getControllerPath());
//
//        entityConfig.setEntity(entity.getEntityName());
//
//        entityConfig.setEntityDTO(entity.getEntityName() + "DTO");
//
//        entityConfig.setIdStrategy(entity.getIdStrategy());
//
//        entityConfig.setIdGenerator(entity.getIdGenerator());
//
//        String idGenerator = entityConfig.getIdGenerator();
//
//        entityConfig.setIdGeneratorPackage(idGenerator);
//
//        entityConfig.setIdGenerator(idGenerator.substring(idGenerator.lastIndexOf(".") + 1));
//
//        format(entityConfig);
//
//        return entityConfig;
//    }
//
//    /**
//     * 修正格式
//     *
//     * @param entityConfig
//     */
//    private void format(EntityConfig entityConfig) {
//
//        List<MetaProperty> metaProperties = entityConfig.getMetaProperties();
//        String columnName;
//        for (MetaProperty property : metaProperties) {
//            columnName = property.getColumnName();
//            if (columnName.contains("_")) {
//                String[] strings = columnName.split("_");
//                StringBuilder builder = new StringBuilder("");
//                for (String str : strings) {
//                    builder.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
//                }
//                builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
//                property.setColumnName(builder.toString());
//            }
//        }
//    }
//
//    private BizGeneratorConfig loadJsonConfig(String outputDirectoryPath) {
//
//        try {
//
//            String path = outputDirectoryPath + "/classes/eden.json";
//
//            System.out.println("loadJsonConfig:" + path);
//
//            File file = new File(path);
//
//            String configStr = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
//
//            return gson.fromJson(configStr, BizGeneratorConfig.class);
//        } catch (IOException e) {
//            throw new RuntimeException("Can't Load Eden.json");
//        }
//    }
//}
