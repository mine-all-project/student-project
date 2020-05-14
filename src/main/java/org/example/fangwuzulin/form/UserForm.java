package org.example.fangwuzulin.form;

import org.example.fangwuzulin.entity.SysUser;
import lombok.Data;

@Data
public class UserForm {
    private String id;
    private String username;
    private String password;
    private String newPassword;
    private String name;
    private String phone;
    private String mail;
    private String randomCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public SysUser toEntity() {
        SysUser entity = new SysUser();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setPhone(this.phone);
        entity.setMail(this.mail);
        entity.setUsername(this.username);
        entity.setPassword(this.password);
        entity.setRandomCode(this.randomCode);
        return entity;
    }
}
