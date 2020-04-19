package com.feiniaojin.earlyhome.generator.biz.metadata;

import lombok.Data;

@Data
public class Entity {

    private String controllerPath;
    private Integer idStrategy;//0自动，1手动
    private String idGenerator;
    private String idGeneratorPackage;
    private String basePackage;
    private String basePath;
    private String entity;
    private String entityDTO;
}
