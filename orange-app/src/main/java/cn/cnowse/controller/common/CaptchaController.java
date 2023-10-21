package cn.cnowse.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;

import cn.cnowse.server.pojo.common.CaptchaVO;
import cn.cnowse.constant.CacheConstants;
import cn.cnowse.constant.Constants;
import cn.cnowse.framework.config.properties.OrangeProperties;
import cn.cnowse.framework.exception.ServiceException;
import cn.cnowse.server.service.system.SysConfigService;
import cn.cnowse.util.redis.RedisHelper;
import cn.cnowse.util.uuid.IdUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证码操作处理
 * 
 * @author Jeong Geol
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final Producer captchaProducer;
    private final Producer captchaProducerMath;
    private final RedisHelper redisHelper;
    private final SysConfigService configService;
    private final OrangeProperties orangeProperties;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public CaptchaVO getCode() {
        CaptchaVO captchaVO = new CaptchaVO();
        boolean captchaEnabled = configService.getCaptchaEnabled();
        captchaVO.setCaptchaEnabled(captchaEnabled);
        if (!captchaEnabled) {
            return captchaVO;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr;
        String code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = orangeProperties.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisHelper.valueSet(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            if (image != null) {
                ImageIO.write(image, "jpg", os);
            }
        } catch (IOException e) {
            log.info("captcha convert error", e);
            throw new ServiceException();
        }
        captchaVO.setUuid(uuid);
        captchaVO.setImg(Base64.getEncoder().encodeToString(os.toByteArray()));
        return captchaVO;
    }

}
