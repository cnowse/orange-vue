package cn.cnowse.server.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysUserMapper;
import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.service.system.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getUserByUserName(String userName) {
        return baseMapper.selectOne(this.lambdaQuery().eq(SysUser::getUserName, userName).getWrapper());
    }

}
