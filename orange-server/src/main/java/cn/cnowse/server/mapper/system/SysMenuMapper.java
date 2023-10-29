package cn.cnowse.server.mapper.system;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.cnowse.server.pojo.system.entity.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户 ID 查询菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据用户 ID 查询权限
     *
     * @param userId 用户 ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据角色 ID 查询权限
     *
     * @param roleId 角色 ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByRoleId(Long roleId);

}
