package cn.cnowse.server.mapper.system;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.cnowse.server.pojo.system.entity.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户 ID 查询角色
     *
     * @param userId 用户 ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(Long userId);

}
