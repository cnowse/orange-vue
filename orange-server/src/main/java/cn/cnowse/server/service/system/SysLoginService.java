package cn.cnowse.server.service.system;

import cn.cnowse.server.pojo.system.dto.LoginBodyDTO;

public interface SysLoginService {

    /**
     * 登录方法
     *
     * @param dto 登录信息
     * @return token
     */
    String login(LoginBodyDTO dto);

}
