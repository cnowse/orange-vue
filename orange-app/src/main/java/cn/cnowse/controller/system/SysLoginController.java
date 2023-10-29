package cn.cnowse.controller.system;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.server.convert.system.SysUserConvert;
import cn.cnowse.server.pojo.system.dto.LoginBodyDTO;
import cn.cnowse.server.pojo.system.entity.SysMenu;
import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.pojo.system.vo.LoginUserInfoVO;
import cn.cnowse.server.pojo.system.vo.RouterVO;
import cn.cnowse.server.service.system.SysLoginService;
import cn.cnowse.server.service.system.SysMenuService;
import cn.cnowse.server.service.system.SysPermissionService;
import cn.cnowse.server.service.system.SysUserService;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;

/**
 * 登录验证
 *
 * @author Jeong Geol
 */
@RestController
@RequiredArgsConstructor
public class SysLoginController {

    private final SysLoginService loginService;
    private final SysUserService userService;
    private final SysMenuService menuService;
    private final SysPermissionService permissionService;
    private final SysUserConvert userConvert;

    @PostMapping("/login")
    public void login(@RequestBody LoginBodyDTO dto) {
        loginService.login(dto);
    }

    @GetMapping("/getInfo")
    public LoginUserInfoVO getInfo() {
        long userId = StpUtil.getLoginIdAsLong();
        SysUser user = userService.getById(userId);
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        return new LoginUserInfoVO(userConvert.toVO(user), roles, permissions);
    }

    @GetMapping("/getRouters")
    public List<RouterVO> getRouters() {
        long userId = StpUtil.getLoginIdAsLong();
        List<SysMenu> menus = menuService.getMenuTreeByUserId(userId);
        return menuService.buildMenus(menus);
    }

    @PostMapping("/logout")
    public void logout() {
        StpUtil.logout();
    }

}
