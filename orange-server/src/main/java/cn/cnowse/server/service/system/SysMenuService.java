package cn.cnowse.server.service.system;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.entity.SysMenu;
import cn.cnowse.server.pojo.system.vo.RouterVO;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户 ID 查询菜单树信息
     *
     * @param userId 用户 ID
     * @return 菜单列表
     */
    List<SysMenu> getMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVO> buildMenus(List<SysMenu> menus);

    /**
     * 根据角色 ID 查询权限
     *
     * @param roleId 角色 ID
     * @return 权限列表
     */
    Set<String> getMenuPermsByRoleId(Long roleId);

    /**
     * 根据用户 ID 查询权限
     *
     * @param userId 用户 ID
     * @return 权限列表
     */
    Set<String> getMenuPermsByUserId(Long userId);

}
