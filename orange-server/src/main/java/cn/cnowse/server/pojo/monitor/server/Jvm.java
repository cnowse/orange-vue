package cn.cnowse.server.pojo.monitor.server;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import cn.cnowse.util.ArithUtils;
import lombok.Data;

/**
 * JVM相关信息
 * 
 * @author Jeong Geol
 */
@Data
public class Jvm {

    /** 当前 JVM 占用的内存总数 (M) */
    private double total;

    /** JVM 最大可用内存总数 (M) */
    private double max;

    /** JVM 空闲内存 (M) */
    private double free;

    /** JDK 版本 */
    private String version;

    /** JDK 路径 */
    private String home;

    public double getTotal() {
        return ArithUtils.div(total, (1024 * 1024), 2);
    }

    public double getMax() {
        return ArithUtils.div(max, (1024 * 1024), 2);
    }

    public double getFree() {
        return ArithUtils.div(free, (1024 * 1024), 2);
    }

    public double getUsed() {
        return ArithUtils.div(total - free, (1024 * 1024), 2);
    }

    public double getUsage() {
        return ArithUtils.mul(ArithUtils.div(total - free, total, 4), 100);
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        long timestamp = ManagementFactory.getRuntimeMXBean().getStartTime();
        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        return this.timeDistance(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                ManagementFactory.getRuntimeMXBean().getStartTime());
    }

    /**
     * 运行参数
     */
    public String getInputArgs() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
    }

    /**
     * 计算时间差
     *
     * @param endDate 最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    private String timeDistance(long startTime, long endDate) {
        long nd = 1000L * 24 * 60 * 60;
        long nh = 1000L * 60 * 60;
        long nm = 1000L * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate - startTime;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        return day + "天" + hour + "小时" + min + "分钟";
    }

}
