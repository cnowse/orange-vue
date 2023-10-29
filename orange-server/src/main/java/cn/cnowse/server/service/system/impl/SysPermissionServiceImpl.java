package cn.cnowse.server.service.system.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.cnowse.server.pojo.system.entity.SysRole;
import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.service.system.SysMenuService;
import cn.cnowse.server.service.system.SysPermissionService;
import cn.cnowse.server.service.system.SysRoleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl implements SysPermissionService {

    private static final String ADMIN = "admin";
    private final SysRoleService roleService;
    private final SysMenuService menuService;

    @Override
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        Set<String> rolePermissionByUserId = roleService.getRolePermissionByUserId(user.getUserId());
        // 管理员拥有所有权限
        if (rolePermissionByUserId.contains(ADMIN)) {
            roles.add(ADMIN);
        } else {
            roles.addAll(rolePermissionByUserId);
        }
        return roles;
    }

    @Override
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<>();
        Set<String> rolePermissionByUserId = roleService.getRolePermissionByUserId(user.getUserId());
        // 管理员拥有所有权限
        if (rolePermissionByUserId.contains(ADMIN)) {
            perms.add("*:*:*");
        } else {
            List<SysRole> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles) {
                    Set<String> rolePerms = menuService.getMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(menuService.getMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }

}
