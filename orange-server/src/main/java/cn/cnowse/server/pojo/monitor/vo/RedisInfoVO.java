package cn.cnowse.server.pojo.monitor.vo;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.Data;

@Data
public class RedisInfoVO {

    private Properties info;
    private Object dbSize;
    private List<Map<String, String>> commandStats;

}
