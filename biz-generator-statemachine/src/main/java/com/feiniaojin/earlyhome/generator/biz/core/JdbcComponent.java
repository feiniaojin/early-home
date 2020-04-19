//package com.feiniaojin.earlyhome.generator.biz.core;
//
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Slf4j
//public class JdbcComponent {
//
//    private String sqlTemplate = "select * from {0} limit 0";
//
//    @Resource
//    private JdbcTemplate jdbcTemplate;
//
//    Gson gson = new Gson();
//
//    public List<MetaProperty> tableMeta(String tableName) {
//
//        String sql = MessageFormat.format(sqlTemplate, tableName);
//
//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
//
//        SqlRowSetMetaData metaData = rowSet.getMetaData();
//
//        int columnCount = metaData.getColumnCount();
//        List<MetaProperty> metaProperties = new ArrayList<MetaProperty>(columnCount);
//
//        for (int i = 1; i <= columnCount; i++) {
//
//            MetaProperty metaProperty = new MetaProperty();
//
//            metaProperty.setColumnName(metaData.getColumnName(i));
//            metaProperty.setColumnType(String.valueOf(metaData.getColumnType(i)));
//            metaProperty.setColumnTypeName(metaData.getColumnTypeName(i));
//            metaProperty.setCatalogName(metaData.getCatalogName(i));
//            metaProperty.setColumnClassName(metaData.getColumnClassName(i));
//            metaProperty.setColumnLabel(metaData.getColumnLabel(i));
//            metaProperty.setPrecision(String.valueOf(metaData.getPrecision(i)));
//            metaProperty.setScale(String.valueOf(metaData.getScale(i)));
//            metaProperty.setSchemaName(metaData.getSchemaName(i));
//            metaProperty.setTableName(metaData.getTableName(i));
//
//            metaProperties.add(metaProperty);
//        }
//
//        log.debug(gson.toJson(metaProperties));
//
//        return metaProperties;
//    }
//}
