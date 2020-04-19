package ${basePackage}.controller;

import ${basePackage}.service.${entity}Service;
import ${basePackage}.dto.${entityDTO};
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.Gson;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eden.
 */

@RestController
@RequestMapping("${controllerPath}")
@Slf4j
@Validated
public class ${entity}Controller {

    @Resource
    private ${entity}Service ${entity}Service;

    private Gson gson = new Gson();

    @PostMapping
    public void add(${entityDTO}.Info info) {
        if(log.isDebugEnabled()){
            log.debug("${entityDTO}.Info=[{}]",gson.toJson(info));
        }
        ${entity}Service.add(info);
    }

    @GetMapping
    public ${entityDTO}.Page pageList(${entityDTO}.Query query) {

        if(log.isDebugEnabled()){
            log.debug("${entityDTO}.Query=[{}]",gson.toJson(query));
        }

       ${entityDTO}.Page page= ${entity}Service.pageList(query);

       if(log.isDebugEnabled()){
            log.debug("${entityDTO}.Page=[{}]",gson.toJson(page));
        }

       return   page;
    }

    @GetMapping("/{id}")
    public Map<#noparse><</#noparse>String,Object<#noparse>></#noparse>  detail(@PathVariable String id) {

        if(log.isDebugEnabled()){
            log.debug("Getting ${entity} detail,id=[{}]",id);
        }

        Map<#noparse><</#noparse>String,Object<#noparse>></#noparse> map=new HashMap<#noparse><</#noparse><#noparse>></#noparse>();

       ${entityDTO}.Info info = ${entity}Service.getById(id);

        if(log.isDebugEnabled()){
            log.debug("${entity} detail=[{}]",gson.toJson(info));
        }

        if(info != null){
            map.put("info",info);
        }else{
            map.put("info",new HashMap<#noparse><</#noparse>String,Object<#noparse>></#noparse>(1));
        }

        return map;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, ${entityDTO}.Info info) {
        if(log.isDebugEnabled()){
            log.debug("${entityDTO}.Info=[{}]",gson.toJson(info));
        }
        ${entity}Service.updateById(info);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if(log.isDebugEnabled()){
            log.debug("Deleting ${entity} ,id=[{}]",id);
        }
        ${entity}Service.deleteById(id);
    }
}
