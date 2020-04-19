package com.feiniaojin.earlyhome.generator.basic.core;

import com.feiniaojin.earlyhome.generator.basic.bean.ConfigBean;
import com.feiniaojin.earlyhome.generator.basic.metadata.EntityMetadata;
import com.feiniaojin.earlyhome.generator.basic.metadata.TableMetadata;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@Component
@Slf4j
public class CoreGenerator {

  @Resource
  private JdbcComponent jdbcComponent;

  private Gson gson = new Gson();

  @Resource
  private Configuration configuration;

  public void generate() {

    try {

      String outputDirectoryPath = this.getClass().getResource("/").getPath();

      String basePath = outputDirectoryPath.substring(0, outputDirectoryPath.indexOf("target")) + "src/main/java/";

      //1. 加载eden.json
      if (log.isDebugEnabled()) {
        log.debug("Start to load earlyhome.json");
      }
      ConfigBean configBean = loadJsonConfig(outputDirectoryPath);
      configBean.setBasePath(basePath);
      if (log.isDebugEnabled()) {
        log.debug("finished loading earlyhome.json, earlyhome.json=[{}]", gson.toJson(configBean));
      }


      doGenerate(configBean);
      //5. 创建文件写磁盘
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void doGenerate(ConfigBean configBean) throws Exception {

    //遍历每个entity配置
    List<ConfigBean.Entity> entities = configBean.getEntities();

    for (ConfigBean.Entity entity : entities) {
      generateEachEntity(configBean, entity);
    }


  }

  private void generateEachEntity(ConfigBean edenConfig, ConfigBean.Entity entity) throws Exception {

    //2. 加载数据库表的元信息
    if (log.isDebugEnabled()) {
      log.debug("Start to load table meta");
    }
    List<TableMetadata> metaProperties = jdbcComponent.tableMeta(entity.getTableName());
    if (log.isDebugEnabled()) {
      log.debug("finished loading table meta, metaProperties=[{}]", gson.toJson(metaProperties));
    }

    //3. 创建实体的配置
    if (log.isDebugEnabled()) {
      log.debug("Start to create entityMetadata");
    }
    EntityMetadata entityMetadata = createEntityMetadata(entity, metaProperties);
    entityMetadata.setBasePath(edenConfig.getBasePath());
    entityMetadata.setBasePackage(edenConfig.getBasePackage());

    if (log.isDebugEnabled()) {
      log.debug("Finished  creating entityMetadata, entityMetadata=[{}]", gson.toJson(entityMetadata));
    }

//            generateEntity(entityConfig);

    generateDTO(entityMetadata);

    generateService(entityMetadata);

    generateServiceImpl(entityMetadata);

    generateController(entityMetadata);

    log.info("Generate complete!");
  }

  private void generateController(EntityMetadata entityMetadata) throws IOException, TemplateException {
    //4. 加载模板，生成代码
    Template template = configuration.getTemplate("controller.ftl");

    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityMetadata);

    StringBuilder builder = new StringBuilder();

    builder.append(entityMetadata.getBasePath());

    builder.append(getPackagePath(entityMetadata));

    String fileName = builder.toString() + "/controller/" + entityMetadata.getEntity() + "Controller.java";

    FileUtils.write(new File(fileName), text, "UTF-8");
  }

  private void generateServiceImpl(EntityMetadata entityMetadata) throws IOException, TemplateException {

//    4. 加载模板，生成代码
    Template template = configuration.getTemplate("serviceimpl.ftl");

    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityMetadata);

    StringBuilder builder = new StringBuilder();

    builder.append(entityMetadata.getBasePath());

    builder.append(getPackagePath(entityMetadata));

    String fileName = builder.toString() + "/service/impl/" + entityMetadata.getEntity() + "ServiceImpl.java";

    FileUtils.write(new File(fileName), text, "UTF-8");
  }

  private void generateService(EntityMetadata entityMetadata) throws IOException, TemplateException {

    //4. 加载模板，生成代码
    Template template = configuration.getTemplate("service.ftl");

    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityMetadata);

    StringBuilder builder = new StringBuilder();

    builder.append(entityMetadata.getBasePath());

    builder.append(getPackagePath(entityMetadata));

    String fileName = builder.toString() + "/service/" + entityMetadata.getEntity() + "Service.java";

    forceDeleteOldFile(fileName);

    FileUtils.write(new File(fileName), text, "UTF-8");

  }

  private void forceDeleteOldFile(String fileName) {
    File file = new File(fileName);
    if (file.exists()) {
      file.delete();
    }
  }

  private void generateDTO(EntityMetadata entityMetadata) throws IOException, TemplateException {

//    4. 加载模板，生成代码
    Template template = configuration.getTemplate("dto.ftl");

    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, entityMetadata);

    StringBuilder builder = new StringBuilder();

    builder.append(entityMetadata.getBasePath());

    builder.append(getPackagePath(entityMetadata));

    String fileName = builder.toString() + "/dto/" + entityMetadata.getEntity() + "DTO.java";

    forceDeleteOldFile(fileName);

    FileUtils.write(new File(fileName), text, "UTF-8");

  }

  private String getPackagePath(EntityMetadata entityMetadata) {
    String basePackage = entityMetadata.getBasePackage();

    return basePackage.replace(".", "/");
  }

  private EntityMetadata createEntityMetadata(ConfigBean.Entity entity, List<TableMetadata> metaProperties) {

    EntityMetadata entityMetadata = new EntityMetadata();

    entityMetadata.setMetaProperties(metaProperties);

    entityMetadata.setControllerPath(entity.getControllerPath());

    entityMetadata.setEntity(entity.getEntityName());

    entityMetadata.setEntityDTO(entity.getEntityName() + "DTO");

    entityMetadata.setIdStrategy(entity.getIdStrategy());

    entityMetadata.setIdGenerator(entity.getIdGenerator());

    String idGenerator = entityMetadata.getIdGenerator();

    entityMetadata.setIdGeneratorPackage(idGenerator);

    entityMetadata.setIdGenerator(idGenerator.substring(idGenerator.lastIndexOf(".") + 1));

    format(entityMetadata);

    return entityMetadata;
  }


  /**
   * 修正格式，有下划线的列转成驼峰法
   *
   * @param entityMetadata
   */
  private void format(EntityMetadata entityMetadata) {

    List<TableMetadata> metaProperties = entityMetadata.getMetaProperties();
    String columnName;
    for (TableMetadata property : metaProperties) {
      columnName = property.getColumnName();
      if (columnName.contains("_")) {
        String[] strings = columnName.split("_");
        StringBuilder builder = new StringBuilder("");
        for (String str : strings) {
          builder.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
        }
        builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        property.setColumnName(builder.toString());
      }
    }
  }

  private ConfigBean loadJsonConfig(String outputDirectoryPath) {

    try {

      String path = outputDirectoryPath + "earlyhome.json";

      log.info("loadJsonConfig,path=[{}]", path);

      File file = new File(path);

      String configStr = FileUtils.readFileToString(file, Charset.forName("UTF-8"));

      return gson.fromJson(configStr, ConfigBean.class);
    } catch (IOException e) {
      throw new RuntimeException("Can't Load earlyhome.json");
    }
  }
}
