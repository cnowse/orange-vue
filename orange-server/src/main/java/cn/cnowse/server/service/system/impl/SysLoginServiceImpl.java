package cn.cnowse.server.service.system.impl;

import org.springframework.stereotype.Service;

import cn.cnowse.constant.CacheConstants;
import cn.cnowse.enums.UserStatus;
import cn.cnowse.exception.ServiceException;
import cn.cnowse.server.pojo.system.dto.LoginBodyDTO;
import cn.cnowse.server.pojo.system.eneity.SysUser;
import cn.cnowse.server.service.system.SysConfigService;
import cn.cnowse.server.service.system.SysLoginService;
import cn.cnowse.server.service.system.SysUserService;
import cn.cnowse.util.ip.IpUtils;
import cn.cnowse.util.redis.RedisHelper;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登录
 *
 * @author Jeong Geol
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLoginServiceImpl implements SysLoginService {

    private final SysUserService userService;
    private final SysConfigService configService;
    private final RedisHelper redisHelper;

    @Override
    public String login(LoginBodyDTO dto) {
        // 验证码校验
        this.validateCaptcha(dto.getCode(), dto.getUuid());
        // IP黑名单校验
        String blackStr = configService.getConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            throw new ServiceException();
        }
        SysUser user = userService.getUserByUserName(dto.getUsername());
        if (user == null) {
            log.info("登录用户：{} 不存在.", dto.getUsername());
            throw new ServiceException("登录用户不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", dto.getUsername());
            throw new ServiceException("登录用户已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", dto.getUsername());
            throw new ServiceException("登录用户已被停用");
        }
        StpUtil.login(user.getUserId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo.getTokenValue();
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     */
    private void validateCaptcha(String code, String uuid) {
        boolean captchaEnabled = configService.getCaptchaEnabled();
        if (captchaEnabled) {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
            String captcha = redisHelper.valueGet(verifyKey, String.class);
            redisHelper.delete(verifyKey);
            if (!code.equalsIgnoreCase(captcha)) {
                throw new ServiceException("验证码无效");
            }
        }
    }

}
