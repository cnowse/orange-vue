package cn.cnowse.controller.system;

import java.util.List;
import java.util.Set;

import cn.cnowse.server.pojo.system.eneity.SysMenu;
import cn.cnowse.server.pojo.system.eneity.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.server.service.system.SysLoginService;
import cn.cnowse.server.pojo.system.dto.LoginBodyDTO;
import cn.cnowse.server.pojo.system.vo.TokenVO;
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
    private final ISysMenuService menuService;
    private final SysPermissionService permissionService;

    @PostMapping("/login")
    public TokenVO login(@RequestBody LoginBodyDTO dto) {
        // 生成令牌
        String token = loginService.login(dto);
        return new TokenVO(token);
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

}
