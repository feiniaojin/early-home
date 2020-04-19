package com.feiniaojin.earlyhome.generator.biz.bean;

import lombok.Data;

import java.util.List;

@Data
public class ConfigBean {
    private String basePackage;
    private String basePath;
    private List<Entity> entities;

    @Data
    public static class Entity {

        private String entityName;
        private String tableName;
        private String idStrategy;//0自动，1手动
        private String idGenerator;
        private String controllerPath;
    }
}
