package cn.cnowse.server.mapper.system;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.cnowse.server.pojo.system.entity.SysUserRole;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据 userId 获取 roleId
     *
     * @param userId userId
     * @return roleIds
     * @author Jeong Geol
     */
    List<Long> selectRoleIdByUserId(Long userId);

}
