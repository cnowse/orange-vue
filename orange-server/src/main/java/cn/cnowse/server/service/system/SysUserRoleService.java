package cn.cnowse.server.service.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.entity.SysUserRole;

public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据 userId 获取 roleId
     *
     * @param userId userId
     * @return roleIds
     * @author Jeong Geol
     */
    List<Long> getRoleIdsByUserId(Long userId);

}
