package cn.cnowse.controller.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.cnowse.server.pojo.common.CaptchaVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CaptchaTest {

    @Autowired
    private CaptchaController captchaController;

    /**
     * 获取验证码
     *
     * @author Jeong Geol
     */
    @Test
    void getCaptchaTest() {
        CaptchaVO code = captchaController.getCode();
        Assertions.assertNotNull(code);
        log.info("captcha is [{}]", code);
    }

}
