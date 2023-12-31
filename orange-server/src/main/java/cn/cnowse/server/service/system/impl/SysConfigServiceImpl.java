package cn.cnowse.server.service.system.impl;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.constant.CacheConstants;
import cn.cnowse.server.mapper.system.SysConfigMapper;
import cn.cnowse.server.pojo.system.entity.SysConfig;
import cn.cnowse.server.service.system.SysConfigService;
import cn.cnowse.util.redis.RedisHelper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final RedisHelper redisHelper;

    @Override
    public String getConfigValueByKey(String configKey) {
        String configValue = redisHelper.valueGet(this.getCacheKey(configKey), String.class);
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfig retConfig = this.getByConfigKey(configKey);
        if (retConfig != null) {
            redisHelper.valueSet(this.getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public boolean getCaptchaEnabled() {
        String captchaEnabled = this.getConfigValueByKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return "true".equals(captchaEnabled);
    }

    @Override
    public SysConfig getByConfigKey(String configKey) {
        return this.lambdaQuery().eq(SysConfig::getConfigKey, configKey).one();
    }

    /**
     * 获取 cache key
     *
     * @param configKey 参数键
     * @return 缓存键 key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }

}
