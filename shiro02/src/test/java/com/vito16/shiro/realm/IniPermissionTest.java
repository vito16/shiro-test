package com.vito16.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Version 1.0
 * @Author 木鱼
 * @Email muyu@yiji.com
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class IniPermissionTest {
    private static final Logger logger = LoggerFactory.getLogger(IniPermissionTest.class);

    public Subject login(String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            logger.info("登陆成功...");
        } catch (UnknownAccountException exception) {
            logger.info("账号不存在.....");
        } catch (IncorrectCredentialsException exception) {
            logger.info("密码错误...");
        }
        return subject;
    }

    @Test
    public void checkPermission1() {
        Subject subject = login("zhou", "1234");
        subject.checkPermission("edit");
    }

    @Test(expected = UnauthorizedException.class)
    public void checkPermission2() {
        Subject subject = login("zhou", "1234");
        subject.checkPermission("add");
    }

    @Test
    public void checkPermission3() {
        Subject subject = login("zhou", "1234");
        subject.checkPermissions("edit", "delete");
    }

}
