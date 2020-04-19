package com.feiniaojin.earlyhome.generator.basic.bean;

import java.util.List;
import lombok.Data;

@Data
public class ConfigBean {
  private String basePackage;
  private String basePath;
  private List<Entity> entities;

  @Data
  public static class Entity {

    private String entityName;
    private String tableName;
    private Integer idStrategy;//0自动，1手动
    private String idGenerator;
    private String controllerPath;
  }
}
