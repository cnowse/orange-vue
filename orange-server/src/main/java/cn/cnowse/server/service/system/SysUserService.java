package cn.cnowse.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.eneity.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser getUserByUserName(String userName);

}
