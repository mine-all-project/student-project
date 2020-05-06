package org.example.fangwuzulin.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.example.fangwuzulin.config.ApplicationConfigure;
import org.example.fangwuzulin.config.ApplicationException;
import org.example.fangwuzulin.dto.ResponseDTO;
import org.example.fangwuzulin.entity.SysUser;
import org.example.fangwuzulin.form.UserForm;
import org.example.fangwuzulin.mapping.SysUserMapping;
import org.example.fangwuzulin.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysServiceImpl implements SysService {
    //    private static final String CODE_KEY = "CODE:";
//    private static final String CODE_TEMP = "CODE:TEMP:";
    private static final Logger logger = LoggerFactory.getLogger(SysServiceImpl.class);
    private final String salt;

    //    private final StringRedisTemplate redisTemplate;
    private final SysUserMapping sysUserMapping;

    //
    public SysServiceImpl(ApplicationConfigure applicationConfigure,
//                          StringRedisTemplate redisTemplate
                          SysUserMapping sysUserMapping
    ) {
        this.salt = applicationConfigure.SALT;
//        this.redisTemplate = redisTemplate;
        this.sysUserMapping = sysUserMapping;
    }


    @Override
    public ResponseDTO login(UserForm form) {
        try {
            String username = form.getUsername();
            String password = new Md5Hash(form.getPassword(), salt).toString();
            logger.info("开始登录->用户名:[{}],密码:[{}]", username, password);
            SysUser user = shiroCheckLogin(username, password);
            if (user == null) {
                return ResponseDTO.returnError("用户名或密码错");
            }
            return ResponseDTO.returnSuccess("登录成功", user);
        } catch (Exception e) {
            logger.warn("登录失败:[{}]", e.getMessage(), e);
            return ResponseDTO.returnError(e.getMessage());
        }
    }

    @Override
    public SysUser registry(UserForm form) {
        SysUser exist = sysUserMapping.findByUsername(form.getUsername());
        if (exist != null) {
            throw new ApplicationException("用户名已经存在");
        }
        String username = form.getUsername();
        String password = new Md5Hash(form.getPassword(), salt).toString();
        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setName(form.getName());
        sysUser.setPhone(form.getPhone());
        sysUser.setMail(form.getMail());
        sysUser.set_admin(false);
        sysUser.setStatus(0);
        Integer count = sysUserMapping.insertUser(sysUser);
        if (count <= 0) {
            throw new ApplicationException("操作失败");
        }
        return sysUser;
    }

    @Override
    public void saveUserInfo(UserForm userForm) {
        SysUser sysUser = userForm.toEntity();
        Integer count = sysUserMapping.updateUserInfo(sysUser);
        if (count <= 0) {
            throw new ApplicationException("操作失败");
        }
    }

    /**
     * shiro认证
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证成功后的用户对象
     */
    private SysUser shiroCheckLogin(String username, String password) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            return (SysUser) subject.getPrincipal();
        } catch (IncorrectCredentialsException e) {
            logger.warn("shiro认证失败,密码错误:[{}]", e.getMessage());
        } catch (UnknownAccountException e) {
            logger.warn("shiro认证失败,用户不存在:[{}]", e.getMessage());
        } catch (Exception e) {
            logger.warn("shiro认证失败", e);
        }
        return null;
    }


//
//    private String createCheckCode(String userKey) {
//        String redisKey = CODE_KEY + userKey;
//        String code = redisTemplate.opsForValue().get(redisKey);
//        if (StringUtils.isEmpty(code)) {
//            StringBuilder newCode = new StringBuilder();
//            Random random = new Random();
//            for (int i = 0; i < 6; i++) {
//                newCode.append(random.nextInt(10));
//            }
//            code = newCode.toString();
//            redisTemplate.opsForValue().set(redisKey, code, 10, TimeUnit.MINUTES);
//        }
//        return code;
//    }
//
//    @Override
//    public List<SysUser> getUserList() {
//        return sysUserRepository.findAll();
//    }
//
//    @Override
////    @RequiresPermissions("manage")
//    public void changeStatus(String id) {
//        SysUser sysUser = sysUserRepository.findById(id).orElse(null);
//        assert sysUser != null;
//        sysUser.setStatus(Math.abs(sysUser.getStatus() - 1));
//        sysUserRepository.save(sysUser);
//    }
//
//    @Override
////    @RequiresPermissions("manage")
//    public void removeUserById(String id) {
//        SysUser sysUser = sysUserRepository.findById(id).orElse(null);
//        assert sysUser != null;
//        if(sysUser.isAdmin()){
//            throw new ApplicationException("不能删除管理员");
//        }
//        sysUserRepository.delete(sysUser);
//    }
//
//
//    @Override
//    public void savePassword(Map map) {
//        Subject subject = SecurityUtils.getSubject();
//        SysUser sysUser = (SysUser) subject.getPrincipal();
//        String password = sysUser.getPassword();
//        String oldPassword = new Md5Hash(map.get("password"), salt).toString();
//        String newPassword = new Md5Hash(map.get("newPassword"), salt).toString();
//        String rePassword = new Md5Hash(map.get("rePassword"), salt).toString();
//        System.err.println(password);
//        System.err.println(newPassword);
//        System.err.println(rePassword);
//        if(!newPassword.equals(rePassword)){
//            throw new ApplicationException("两次密码不相同");
//        }
//        if(!password.equals(oldPassword)){
//            throw new ApplicationException("原密码错误");
//        }
//        sysUser.setPassword(newPassword);
//        sysUserRepository.save(sysUser);
//    }
}
