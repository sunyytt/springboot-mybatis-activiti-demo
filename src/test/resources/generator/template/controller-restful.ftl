package ${basePackage}.controller;

import ${basePackage}.common.result.*;
import ${basePackage}.utils.ResponseResult;
import ${modelPackage}.${className};
import ${servicePackage}.${className}Service;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Result add(@RequestBody ${className} ${entityName}) {
        ${entityName}Service.save(${entityName});
        return ResponseResult.SuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        ${className} ${entityName} = new ${className}();
        ${entityName}.setId(id);
        ${entityName}Service.deleteById(${entityName});
        return ResponseResult.SuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ${className} ${entityName}) {
        ${entityName}Service.updateById(${entityName});
        return ResponseResult.SuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        ${className} ${entityName} = new ${className}();
        ${entityName}.setId(id);
        ${className} result = ${entityName}Service.findById(${entityName});
        return ResponseResult.SuccessResult(result);
    }

    @GetMapping
    public Result list(@RequestParam(name = "current", required = false, defaultValue = "1")
                    int current,
            @RequestParam(name= "pageSize",required = false,defaultValue ="10" )
                    int pageSize,
            ${className} ${entityName}) {

        ResultPager<${className}> pager = ${entityName}Service.selectByConditionPager(current,pageSize,${entityName});
        return ResponseResult.SuccessResult(pager);
    }
}
