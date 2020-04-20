package ${basePackage}.service.impl;

import ${basePackage}.dao.${entity}Mapper;
import ${basePackage}.dto.${entityDTO};
import ${basePackage}.entity.${entity};
import ${basePackage}.entity.${entity}Example;
import ${basePackage}.service.${entity}Service;
<#if idStrategy==1>
import ${idGeneratorPackage};
</#if>
import lombok.extern.slf4j.Slf4j;
import cn.gingo.framework.exception.CommonException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;

/**
\l;     <
\g;     >
 * Created by yujie.
 */
@Service
@Slf4j
public class ${entity}ServiceImpl implements ${entity}Service {

    @Resource
    private ${entity}Mapper ${entity?uncap_first}Mapper;

    <#if idStrategy==1>
    private ${idGenerator} ${idGenerator?uncap_first};
    </#if>

    @Override
    public ${entityDTO}.Page pageList(${entityDTO}.Query query) {

        ${entityDTO}.Page page = new ${entityDTO}.Page();
        ${entity}Example example = new ${entity}Example();
        ${entity}Example.Criteria criteria = example.createCriteria();
        DateFormat dataFormat = null;

        <#list metaProperties as property>
    <#if property.columnClassName == "java.lang.Long">
                if(query.get${property.columnName?cap_first}()!=null){
                    criteria.and${property.columnName?cap_first}EqualTo(Long.valueOf(query.get${property.columnName?cap_first}()));
                }
                 <#elseif property.columnClassName  == "java.lang.Integer">
                if(query.get${property.columnName?cap_first}()!=null){
                    criteria.and${property.columnName?cap_first}EqualTo(Integer.valueOf(query.get${property.columnName?cap_first}()));
                }
                <#elseif property.columnClassName=="java.lang.String">
                if(query.get${property.columnName?cap_first}()!=null && !"".equals(query.get${property.columnName?cap_first}())){
                    criteria.and${property.columnName?cap_first}Like("%"+query.get${property.columnName?cap_first}()+"%");
                }
                <#elseif property.columnClassName=="java.sql.Timestamp">
                try{

                    if(dataFormat == null){
                        dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                    }

                    if(query.get${property.columnName?cap_first}Start()!=null ){
                        criteria.and${property.columnName?cap_first}GreaterThanOrEqualTo(dataFormat.parse(query.get${property.columnName?cap_first}Start()));
                    }

                    if(query.get${property.columnName?cap_first}End()!=null ){
                        criteria.and${property.columnName?cap_first}LessThanOrEqualTo(dataFormat.parse(query.get${property.columnName?cap_first}End()));
                    }
                }catch(Exception e){
                    throw new CommonException.IllegalArgumentException();
                }
                </#if>
</#list>
        //查询总数量
        int count = ${entity?uncap_first}Mapper.countByExample(example);
        page.setCount(count);
        if(count==0){
            page.setList(new ArrayList<#noparse><</#noparse><#noparse>></#noparse>(0));
            return page;
        }
        //封装分页
        example.setLimitStart((query.getPage()-1) * query.getPageSize());
        example.setLimitEnd(query.getPageSize());
        //查询数据
        List<#noparse><</#noparse>${entity}<#noparse>></#noparse> list=${entity?uncap_first}Mapper.selectByExample(example);
        //封装到Page中，返回
        List<#noparse><</#noparse>${entityDTO}.TableItem<#noparse>></#noparse> data=new ArrayList<#noparse><</#noparse><#noparse>></#noparse>(list.size());
        for(${entity} entity :list){
            data.add(entity2TableItem(entity));
        }
        page.setList(data);
        return page;
    }

    @Override
    public void add(${entityDTO}.Info info) {

        ${entity} entity = dto2Entity(info);
        <#if idStrategy=1>
        entity.setId(${idGenerator?uncap_first}.generatorId("${entity}"));
        <#else>
        //TODO set the id
        </#if>
        ${entity?uncap_first}Mapper.insertSelective(entity);
    }


    @Override
    public void updateById(${entityDTO}.Info info) {
        ${entity} entity = dto2Entity(info);
        ${entity?uncap_first}Mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteById(String id) {
        ${entity?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public ${entityDTO}.Info getById(String id) {
        ${entity} entity =${entity?uncap_first}Mapper.selectByPrimaryKey(id);
        if(entity == null){
            throw new CommonException.NotFoundException();
        }
        return entity2Info(entity);
    }

    private ${entity} dto2Entity(${entity}DTO.Info info){
        DateFormat dataFormat = null;
        new SimpleDateFormat("yyyy-MM-dd");
        ${entity} entity = new ${entity}();
        <#list metaProperties as property>
        <#if property.columnClassName == "java.lang.Long">
        if(StringUtils.isNoneBlank(info.get${property.columnName?cap_first}())){
            entity.set${property.columnName?cap_first}(Long.valueOf(info.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName  == "java.lang.Integer">
        if(info.get${property.columnName?cap_first}()!=null){
            entity.set${property.columnName?cap_first}(Integer.valueOf(info.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName  == "java.lang.String">
        if(info.get${property.columnName?cap_first}()!=null){
            entity.set${property.columnName?cap_first}(info.get${property.columnName?cap_first}());
        }
        <#elseif property.columnClassName=="java.sql.Timestamp">
        if(info.get${property.columnName?cap_first}()!=null){
            try{
                if(dataFormat==null){
                  dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                }
                entity.set${property.columnName?cap_first}(dataFormat.parse(info.get${property.columnName?cap_first}()));
            }catch(Exception e){
                throw new CommonException.IllegalArgumentException();
            }
        }
         <#elseif property.columnClassName=="java.math.BigDecimal">
        if(info.get${property.columnName?cap_first}()!=null){
            entity.set${property.columnName?cap_first}(new BigDecimal(info.get${property.columnName?cap_first}()));
        }
        </#if>
        </#list>
        return entity;
    }

    private ${entity}DTO.Info entity2Info(${entity} entity){

        ${entity}DTO.Info info = new ${entity}DTO.Info();
        DateFormat dataFormat = null;
        <#list metaProperties as property>
        <#if property.columnClassName == "java.lang.Long">
        if(entity.get${property.columnName?cap_first}()!=null){
            info.set${property.columnName?cap_first}(String.valueOf(entity.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName  == "java.lang.Integer">
        if(entity.get${property.columnName?cap_first}()!=null){
            info.set${property.columnName?cap_first}(String.valueOf(entity.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName=="java.lang.String">
        if(entity.get${property.columnName?cap_first}()!=null){
            info.set${property.columnName?cap_first}(entity.get${property.columnName?cap_first}());
        }
        <#elseif property.columnClassName=="java.sql.Timestamp">
        if(entity.get${property.columnName?cap_first}()!=null){
            if(dataFormat==null){
             dataFormat=new SimpleDateFormat("yyyy-MM-dd");
            }
            info.set${property.columnName?cap_first}(dataFormat.format(entity.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName=="java.math.BigDecimal">
        if(entity.get${property.columnName?cap_first}()!=null){
            info.set${property.columnName?cap_first}(entity.get${property.columnName?cap_first}().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
        </#if>
        </#list>
        return info;
    }

    private ${entity}DTO.TableItem entity2TableItem(${entity} entity){

        ${entity}DTO.TableItem tableItem = new ${entity}DTO.TableItem();
        DateFormat dataFormat = null;

        <#list metaProperties as property>
        <#if property.columnClassName == "java.lang.Long">
        if(entity.get${property.columnName?cap_first}()!=null){
            tableItem.set${property.columnName?cap_first}(String.valueOf(entity.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName  == "java.lang.Integer">
        if(entity.get${property.columnName?cap_first}()!=null){
            tableItem.set${property.columnName?cap_first}(String.valueOf(entity.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName=="java.lang.String">
        if(entity.get${property.columnName?cap_first}()!=null){
            tableItem.set${property.columnName?cap_first}(entity.get${property.columnName?cap_first}());
        }
        <#elseif property.columnClassName=="java.sql.Timestamp">
        if(entity.get${property.columnName?cap_first}()!=null){
            if(dataFormat==null){
                dataFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            tableItem.set${property.columnName?cap_first}(dataFormat.format(entity.get${property.columnName?cap_first}()));
        }
        <#elseif property.columnClassName=="java.math.BigDecimal">
        if(entity.get${property.columnName?cap_first}()!=null){
            tableItem.set${property.columnName?cap_first}(entity.get${property.columnName?cap_first}().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
        </#if>
        </#list>
        return tableItem;
    }
}