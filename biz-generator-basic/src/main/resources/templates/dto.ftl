package ${basePackage}.dto;

import lombok.Data;

import java.util.List;

public class ${entityDTO} {

    @Data
    public static class Query {
        private Integer page = 1;
        private Integer pageSize = 10;
        <#list metaProperties as property>
    <#if property.columnClassName != "java.sql.Timestamp">
        private String ${property.columnName};
              <#else>
        private String ${property.columnName}Start;
        private String ${property.columnName}End;
            </#if>
</#list>
    }

    @Data
    public static class TableItem {
<#list metaProperties as property>
        private String ${property.columnName};
        </#list>
    }

    @Data
    public static class Info {
<#list metaProperties as property>
        private String ${property.columnName};
        </#list>
    }

    @Data
    public static class Page{
        private Integer count;
        private List <#noparse><</#noparse>TableItem<#noparse>></#noparse> list;
    }

}