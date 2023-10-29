package cn.cnowse.server.service.system;

import java.util.Set;

import cn.cnowse.server.pojo.system.entity.SysUser;

/**
 * 用户权限处理
 *
 * @author Jeong Geol
 */
public interface SysPermissionService {

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    Set<String> getRolePermission(SysUser user);

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(SysUser user);

}
