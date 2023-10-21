package cn.cnowse.util.redis;

import java.util.concurrent.TimeUnit;

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
     * hash 数据类型存储
     *
     * @param key key
     * @param hashKey hashKey
     * @param value 数据
     * @author Jeong Geol
     */
    public void hashPut(String key, String hashKey, Object value) {
        try {
            redis.opsForHash().put(key, hashKey, om.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            log.info("opsForHash Put Json Exception. msg={}", e.getMessage());
        }
    }

    /**
     * hash 数据类型获取
     *
     * @param key key
     * @param hashKey hashKey
     * @param clazz 期望获取的 Java 类型
     * @return clazz 指定的 Java 类型
     * @author Jeong Geol
     */
    public <T> T hashGet(String key, String hashKey, Class<T> clazz) {
        String value = (String)redis.opsForHash().get(key, hashKey);
        if (value != null) {
            try {
                return om.readValue(value, clazz);
            } catch (JsonProcessingException e) {
                log.info("opsForHash Get Json Exception. msg={}", e.getMessage());
            }
        }
        return null;
    }

    /**
     * hash 数据类型删除指定若干个 hashKey 数据
     *
     * @param key key
     * @param hashKeys hashKey...
     * @author Jeong Geol
     */
    public void hashDelete(String key, Object... hashKeys) {
        redis.opsForHash().delete(key, hashKeys);
    }

}
