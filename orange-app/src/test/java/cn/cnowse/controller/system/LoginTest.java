package cn.cnowse.controller.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.cnowse.constant.CacheConstants;
import cn.cnowse.controller.common.CaptchaController;
import cn.cnowse.exception.ServiceException;
import cn.cnowse.server.pojo.common.CaptchaVO;
import cn.cnowse.server.pojo.system.dto.LoginBodyDTO;
import cn.cnowse.util.redis.RedisHelper;

@SpringBootTest
class LoginTest {

    @Autowired
    private SysLoginController loginController;
    @Autowired
    private CaptchaController captchaController;
    @Autowired
    private RedisHelper redis;

    /**
     * 登录成功测试
     *
     * @author Jeong Geol
     */
    @Test
    void loginSuccessTest() {
        CaptchaVO code = captchaController.getCode();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + code.getUuid();
        String captcha = redis.valueGet(verifyKey, String.class);
        LoginBodyDTO dto = new LoginBodyDTO();
        dto.setUsername("admin");
        dto.setPassword("admin");
        dto.setCode(captcha);
        dto.setUuid(code.getUuid());
        Assertions.assertDoesNotThrow(() -> loginController.login(dto));
    }

    @Test
    void loginTest() {
        LoginBodyDTO dto = new LoginBodyDTO();
        dto.setUsername("admin");
        dto.setPassword("admin");
        dto.setCode("123");
        dto.setUuid("123");
        Assertions.assertThrows(ServiceException.class, () -> loginController.login(dto));
    }

}
