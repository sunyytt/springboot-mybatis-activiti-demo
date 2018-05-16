package ${basePackage}.controller;

import ${basePackage}.common.result.*;
import ${basePackage}.utils.ResponseResult;
import ${modelPackage}.${className};
import ${servicePackage}.${className}Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${className}Controller {
    @Autowired
    private ${className}Service ${entityName}Service;

    @RequestMapping("/add")
    public Result add(${className} ${entityName}) {
        ${entityName}Service.save(${entityName});
        return ResponseResult.SuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        ${className} ${entityName} = new ${className}();
        ${entityName}.setId(id);
        ${entityName}Service.deleteById(${entityName});
        return ResponseResult.SuccessResult();
    }

    @RequestMapping("/update")
    public Result update(${className} ${entityName}) {
        ${entityName}Service.updateById(${entityName});
        return ResponseResult.SuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        ${className} ${entityName} = new ${className}();
        ${entityName}.setId(id);
        ${className} result = ${entityName}Service.findById(${entityName});
        return ResponseResult.SuccessResult(result);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(name = "current", required = false, defaultValue = "1")
                    int current,
            @RequestParam(name= "pageSize",required = false,defaultValue ="10" )
                    int pageSize,
            ${className} ${entityName}) {
        ResultPager<${className}> pager = ${entityName}Service.selectByConditionPager(current,pageSize,${entityName});
        return ResponseResult.SuccessResult(pager);
    }
}
