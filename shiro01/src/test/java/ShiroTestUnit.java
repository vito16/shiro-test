import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
 * @Filename
 * @Description
 * @Version 1.0
 * @Author 木鱼
 * @Email muyu@yiji.com
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ShiroTestUnit {
    private static final Logger logger = LoggerFactory.getLogger(ShiroTestUnit.class);

    public Subject login(String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
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
    public void correctUser() {
        Subject subject = login("zhou", "1234");
        logger.info(String.valueOf(subject.isAuthenticated()));
    }

    @Test
    public void noneUsername() {
        Subject subject = login("zhou1", "1234");
        logger.info(String.valueOf(subject.isAuthenticated()));
    }

    @Test
    public void errorPassword() {
        Subject subject = login("zhou", "11234");
        logger.info(String.valueOf(subject.isAuthenticated()));
    }

}
