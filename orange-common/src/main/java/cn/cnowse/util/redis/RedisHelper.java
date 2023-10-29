package cn.cnowse.util.redis;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * StringRedisTemplate 常用命令封装
 *
 * @author Jeong Geol
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisHelper {

    private final ObjectMapper om;
    private final StringRedisTemplate redis;

    /**
     * string 数据类型存储
     *
     * @param key key
     * @param value 数据
     * @author Jeong Geol
     */
    public void valueSet(String key, Object value) {
        if (value instanceof String) {
            redis.opsForValue().set(key, (String)value);
        } else {
            try {
                redis.opsForValue().set(key, om.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                log.info("opsForValue Set Json Exception. msg={}", e.getMessage());
            }
        }
    }

    /**
     * string 数据类型存储
     *
     * @param key key
     * @param value 数据
     * @param timeout 过期时间
     * @param unit 过期时间单位
     * @author Jeong Geol
     */
    public void valueSet(String key, Object value, long timeout, TimeUnit unit) {
        if (value instanceof String) {
            redis.opsForValue().set(key, (String)value, timeout, unit);
        } else {
            try {
                redis.opsForValue().set(key, om.writeValueAsString(value), timeout, unit);
            } catch (JsonProcessingException e) {
                log.info("opsForValue Set Json Exception. msg={}", e.getMessage());
            }
        }
    }

    /**
     * string 数据类型获取
     *
     * @param key key
     * @param clazz 期望获取的 Java 类型
     * @return clazz 指定的 Java 类型
     * @author Jeong Geol
     */
    public <T> T valueGet(String key, Class<T> clazz) {
        String valueJson = redis.opsForValue().get(key);
        if (StringUtils.hasText(valueJson)) {
            try {
                return om.readValue(valueJson, clazz);
            } catch (JsonProcessingException e) {
                log.info("opsForValue Get Json Exception. msg={}", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 删除指定 key 数据
     *
     * @param key key
     * @author Jeong Geol
     */
    public void delete(String key) {
        redis.delete(key);
    }

    /**
     * 获取 Redis 配置信息
     *
     * @param section 指定获取某个配置信息
     * @return java.util.Properties
     * @author Jeong Geol
     */
    public Properties getRedisInfo(String section) {
        if (StringUtils.hasText(section)) {
            return (Properties)redis.execute((RedisCallback<Object>)connection -> connection.info(section));
        } else {
            return (Properties)redis.execute((RedisCallback<Object>)RedisServerCommands::info);
        }
    }

    /**
     * 获取 key 数量
     *
     * @return 已使用的内存
     * @author Jeong Geol
     */
    public Object getRedisDbSize() {
        return redis.execute((RedisCallback<Object>)RedisServerCommands::dbSize);
    }

    /**
     * 获取指定前缀下的所有 key
     *
     * @param key 例如：sys_config:*
     * @return 指定前缀下的所有 key
     * @author Jeong Geol
     */
    public Set<String> keys(String key) {
        return redis.keys(key);
    }

}
