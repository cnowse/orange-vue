package cn.cnowse.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.constant.CacheConstants;
import cn.cnowse.mapper.system.SysConfigMapper;
import cn.cnowse.service.system.SysConfigService;
import cn.cnowse.system.eneity.SysConfig;
import cn.cnowse.util.redis.RedisHelper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final RedisHelper redisHelper;

    @Override
    public String findConfigByKey(String configKey) {
        String configValue = redisHelper.valueGet(this.getCacheKey(configKey), String.class);
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfig retConfig = baseMapper.selectOne(this.lambdaQuery().eq(SysConfig::getConfigKey, configKey));
        // SysConfig retConfig =
        // baseMapper.selectOne(Wrappers.lambdaQuery(SysConfig.class)
        // .eq(SysConfig::getConfigKey, configKey));
        // SysConfig retConfig = this.lambdaQuery().eq(SysConfig::getConfigKey,
        // configKey).one();
        if (retConfig != null) {
            redisHelper.valueSet(this.getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public boolean getCaptchaEnabled() {
        String captchaEnabled = this.findConfigByKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return "true".equals(captchaEnabled);
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
