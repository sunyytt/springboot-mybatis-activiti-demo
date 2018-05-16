package ${servicePackage};

import ${modelPackage}.${className};
import ${basePackage}.common.result.ResultPager;

import java.util.List;

/**
 * Created by ${author} on ${date}.
 */
public interface ${className}Service {
    /**
     * 持久化
     * @param ${entityName}
     */
    void save(${className} ${entityName});

    /**
     *批量持久化
     * @param ${entityName}s
     */
    void save(List<${className}> ${entityName}s);

    /**
    * 根据主鍵刪除
    * @param ${entityName} 主键不能为空
    */
    void deleteById(${className} ${entityName});

    /**
    * 根据条件删除
    *@param ${entityName}
    */
    void deleteByCondition(${className} ${entityName});

    /**
    * 根据ID更新
    * @param ${entityName} 主键不能为空
    */
    void updateById(${className} ${entityName});

    /**
    * 根据条件更新
    * @param ${entityName}
    */
    void updateByCondition(${className} ${entityName});

    /**
    * 根据ID查找
    * @param ${entityName} 主键不能为空
    * @return
    */
    ${className} findById(${className} ${entityName});

    /**
    * 分页根据条件查找
    * @param current 当前页
    * @param pageSize  显示多少条
    * @param ${entityName} 为空时查询所有
    * @return
    */
    ResultPager<${className}> selectByConditionPager(int current, int pageSize,${className} ${entityName});

}
