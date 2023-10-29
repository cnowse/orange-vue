package cn.cnowse.controller.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.constant.CacheConstants;
import cn.cnowse.server.pojo.monitor.vo.RedisInfoVO;
import cn.cnowse.server.pojo.system.entity.SysCache;
import cn.cnowse.util.redis.RedisHelper;
import lombok.RequiredArgsConstructor;

/**
 * 缓存监控
 *
 * @author Jeong Geol
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor/cache")
public class CacheController {

    private static final List<SysCache> caches = new ArrayList<>();

    static {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "密码错误次数"));
    }

    private final RedisHelper redisHelper;

    @GetMapping
    public RedisInfoVO getInfo() {
        Properties info = redisHelper.getRedisInfo(null);
        Properties commandStats = redisHelper.getRedisInfo("commandstats");
        Object dbSize = redisHelper.getRedisDbSize();
        RedisInfoVO redisInfoVO = new RedisInfoVO();
        redisInfoVO.setInfo(info);
        redisInfoVO.setDbSize(dbSize);
        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        redisInfoVO.setCommandStats(pieList);
        return redisInfoVO;
    }

    @GetMapping("/getNames")
    public List<SysCache> cache() {
        return caches;
    }

    @GetMapping("/getKeys/{cacheName}")
    public Set<String> getCacheKeys(@PathVariable String cacheName) {
        return redisHelper.keys(cacheName + "*");
    }

    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public SysCache getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        String cacheValue = redisHelper.valueGet(cacheKey, String.class);
        return new SysCache(cacheName, cacheKey, cacheValue);
    }

}
