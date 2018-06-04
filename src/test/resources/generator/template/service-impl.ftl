package ${servicePackage}.impl;

import ${mapperPackage}.${className}Mapper;
import ${modelPackage}.${className};
import ${modelPackage}.${className}Example;
import ${servicePackage}.${className}Service;
import ${basePackage}.common.result.ResultPager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Mapper ${entityName}Mapper;


    public void save(${className} ${entityName}){
        ${entityName}Mapper.insertSelective(${entityName});
    }

    public void save(List<${className}> ${entityName}s){}

    public void deleteById(${className} ${entityName}){
        ${entityName}Mapper.deleteByPrimaryKey(${entityName}.getId());
    }

    public void deleteByCondition(${className} ${entityName}){
        ${className}Example ${entityName}Example = new ${className}Example();
        //like ${entityName}Example.createCriteria().andIdEqualTo("1");
        //example.setOrderByClause("字段名 ASC"); //升序排列，desc为降序排列。
        //example.setDistinct(false)//去除重复，boolean型，true为选择不重复的记录。
        ${entityName}Mapper.deleteByExample(${entityName}Example);
    }

    public void updateById(${className} ${entityName}){
        ${entityName}Mapper.updateByPrimaryKeySelective(${entityName});
    }

    public void updateByCondition(${className} ${entityName}){
        //与deleteByCondition 类似
    }

    public ${className} findById(${className} ${entityName}){
            return ${entityName}Mapper.selectByPrimaryKey(${entityName}.getId());
    }

    public ResultPager<${className}> selectByConditionPager(int current, int pageSize,${className} ${entityName}){
        ResultPager.setPager(current,pageSize);
        ${className}Example ${entityName}Example = new ${className}Example();
        //like ${entityName}Example.createCriteria().andIdEqualTo("1");
        List<${className}> list = ${entityName}Mapper.selectByExample(${entityName}Example);
        ResultPager<${className}> pager = new ResultPager<${className}>(list);
        return pager;
    }




}