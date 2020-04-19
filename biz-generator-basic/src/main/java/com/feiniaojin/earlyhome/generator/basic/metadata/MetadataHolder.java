package com.feiniaojin.earlyhome.generator.basic.metadata;

import com.feiniaojin.earlyhome.generator.basic.bean.ConfigBean;
import lombok.Data;

@Data
public class MetadataHolder {
    private EntityMetadata entity;
    private TableMetadata table;
}
