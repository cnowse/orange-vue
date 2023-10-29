package cn.cnowse.server.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.cnowse.server.mapper.system.SysUserRoleMapper;
import cn.cnowse.server.pojo.system.entity.SysUserRole;
import cn.cnowse.server.service.system.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return baseMapper.selectRoleIdByUserId(userId);
    }

}
