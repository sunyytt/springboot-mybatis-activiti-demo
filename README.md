# springboot-mybatis-activiti-demo
## 本项配置如下：
1.springboot 1.5.2.RELEASE<br>
2.activiti.version 5.21.0<br>
3.mybatis.spring.boot.version 1.2.0<br>
4.druid-spring-boot-starter 1.1.0 ---阿里druid数据库连接池<br>
5.pagehelper 5.1.3  ---分页插件<br>
6.RESTful API ---风格<br>
## 总结问题如下
### 1.@Controller和@RestController的区别
1.1使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面。若返回json等内容到页面，则需要加@ResponseBody注解。<br>
1.2@RestController注解，相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了，但使用@RestController这个注解，就不能返回jsp,html页面，视图解析器无法解析jsp,html页面。
### 2.activiti版本问题
1、注意activiti-spring-boot-starter-basic的版本号，推荐使用5.21.0。如果使用的5.17的版本，在启动流程的时候，不会自动帮你部署，因此在这之前需要自己手动的部署一次。5.21.0版本的话就不会出现这个问题。
### 3.RESTful API具体设计如下：
请求类型	    URL	          功能说明<br>
GET	          /users:	     查询用户列表<br>
POST	        /users:	     创建一个用户<br>
GET	          /users/id:	   根据id查询一个用户<br>
PUT	          /users/id:	   根据id更新一个用户<br>
DELETE	      /users/id	:   根据id删除一个用户<br>
具体见：https://www.cnblogs.com/ilinuxer/p/6444804.html
#### ----------------------------------------------------------------------------------------------------------update 20180502
