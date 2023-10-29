package cn.cnowse.server.pojo.monitor.server;

import cn.cnowse.util.ArithUtils;
import lombok.Data;

/**
 * CPU 相关信息
 * 
 * @author Jeong Geol
 */
@Data
public class Cpu {

    /** 核心数 */
    private int cpuNum;

    /** CPU 总的使用率 */
    private double total;

    /** CPU 系统使用率 */
    private double sys;

    /** CPU 用户使用率 */
    private double used;

    /** CPU 当前等待率 */
    private double wait;

    /** CPU 当前空闲率 */
    private double free;

    public double getTotal() {
        return ArithUtils.round(ArithUtils.mul(total, 100), 2);
    }

    public double getSys() {
        return ArithUtils.round(ArithUtils.mul(sys / total, 100), 2);
    }

    public double getUsed() {
        return ArithUtils.round(ArithUtils.mul(used / total, 100), 2);
    }

    public double getWait() {
        return ArithUtils.round(ArithUtils.mul(wait / total, 100), 2);
    }

    public double getFree() {
        return ArithUtils.round(ArithUtils.mul(free / total, 100), 2);
    }

}
