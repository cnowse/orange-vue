package cn.cnowse.server.pojo.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 缓存信息
 * 
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
public class SysCache {

    /** 缓存名称 */
    private String cacheName = "";

    /** 缓存键名 */
    private String cacheKey = "";

    /** 缓存内容 */
    private String cacheValue = "";

    /** 备注 */
    private String remark = "";

}
