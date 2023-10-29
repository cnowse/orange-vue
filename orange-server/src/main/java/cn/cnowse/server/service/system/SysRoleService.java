package cn.cnowse.server.service.system;

import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.entity.SysRole;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户 ID 查询角色权限
     *
     * @param userId 用户 ID
     * @return 权限列表
     */
    Set<String> getRolePermissionByUserId(Long userId);

}
