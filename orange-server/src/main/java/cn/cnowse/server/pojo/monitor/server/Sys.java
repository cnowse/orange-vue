package cn.cnowse.server.pojo.monitor.server;

import lombok.Data;

/**
 * 系统相关信息
 * 
 * @author Jeong Geol
 */
@Data
public class Sys {

    /** 服务器名称 */
    private String computerName;

    /** 服务器 IP */
    private String computerIp;

    /** 项目路径 */
    private String userDir;

    /** 操作系统 */
    private String osName;

    /** 系统架构 */
    private String osArch;

}
