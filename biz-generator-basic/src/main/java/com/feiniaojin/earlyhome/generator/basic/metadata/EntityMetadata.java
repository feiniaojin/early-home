package com.feiniaojin.earlyhome.generator.basic.metadata;

import java.util.List;
import lombok.Data;

/**
 * @author qinyujie
 */
@Data
public class EntityMetadata {

  private String controllerPath;

  /**
   * 0自动，1手动
   */
  private Integer idStrategy;
  private String idGenerator;
  private String idGeneratorPackage;
  private String basePackage;
  private String basePath;
  private String entity;
  private String entityDTO;

  private List<TableMetadata> metaProperties;

}
