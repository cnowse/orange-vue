package cn.cnowse.enums;

/**
 * 限流类型
 *
 * @author Jeong Geol
 */
public enum LimitTypeEnum {

    /** 默认策略全局限流 */
    DEFAULT,

    /** 根据请求者IP进行限流 */
    IP

}
