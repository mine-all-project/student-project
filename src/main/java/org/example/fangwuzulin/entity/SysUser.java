package org.example.fangwuzulin.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUser extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private Integer status;
    private boolean is_admin;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}