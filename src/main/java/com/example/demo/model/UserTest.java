package com.example.demo.model;

import java.util.Date;

public class UserTest {
    private Integer id;

    private String username;

    private String loginname;

    private String role;

    private String sex;

    private String mobileno;

    private String department;

    private String departmentno;

    private Date lastupdatedat;

    private Date lastloginat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getDepartmentno() {
        return departmentno;
    }

    public void setDepartmentno(String departmentno) {
        this.departmentno = departmentno == null ? null : departmentno.trim();
    }

    public Date getLastupdatedat() {
        return lastupdatedat;
    }

    public void setLastupdatedat(Date lastupdatedat) {
        this.lastupdatedat = lastupdatedat;
    }

    public Date getLastloginat() {
        return lastloginat;
    }

    public void setLastloginat(Date lastloginat) {
        this.lastloginat = lastloginat;
    }
}