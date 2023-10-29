package cn.cnowse.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.entity.SysConfig;

public interface SysConfigService extends IService<SysConfig> {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    String getConfigValueByKey(String configKey);

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    boolean getCaptchaEnabled();

    /**
     * 根据 configKey 获取
     *
     * @param configKey configKey
     * @return cn.cnowse.server.pojo.system.entity.SysConfig
     * @author Jeong Geol
     */
    SysConfig getByConfigKey(String configKey);

}
