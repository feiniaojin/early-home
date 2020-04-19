package ${basePackage}.service;

import ${basePackage}.entity.${entity};
import ${basePackage}.dto.${entityDTO};

import java.util.List;

/**
 * Created by eden.
 */
public interface ${entity}Service {

    ${entityDTO}.Page pageList(${entityDTO}.Query query);

    void add(${entityDTO}.Info info);

    void updateById(${entityDTO}.Info info);

    void deleteById(String id);

    ${entityDTO}.Info getById(String id);
}
