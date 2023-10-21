package cn.cnowse.server.service.system.impl;

import cn.cnowse.server.service.system.SysUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysUserMapper;
import cn.cnowse.server.pojo.system.eneity.SysUser;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getUserByUserName(String userName) {
        return baseMapper.selectOne(this.lambdaQuery().eq(SysUser::getUserName, userName));
    }

}
