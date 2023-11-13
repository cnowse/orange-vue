package cn.cnowse.server.service.system.impl;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.base.PageResult;
import cn.cnowse.server.mapper.system.SysUserMapper;
import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.pojo.system.spec.UserSpec;
import cn.cnowse.server.service.system.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public PageResult<SysUser> listBySpec(Integer pageNum, Integer pageSize, UserSpec spec) {
        Page<SysUser> model = this.lambdaQuery()
                .eq(SysUser::getDelFlag, "0")
                .eq(spec.getUserId() != null && spec.getUserId() != 0, SysUser::getUserId, spec.getUserId())
                .eq(StringUtils.isNotBlank(spec.getUserName()), SysUser::getUserName, spec.getUserName())
                .eq(StringUtils.isNotBlank(spec.getStatus()), SysUser::getStatus, spec.getStatus())
                .eq(StringUtils.isNotBlank(spec.getPhoneNumber()), SysUser::getPhoneNumber, spec.getPhoneNumber())
                .ge(spec.getStartTime() != null, SysUser::getCreateTime, spec.getStartTime())
                .le(spec.getEndTime() != null, SysUser::getCreateTime, spec.getEndTime())
                .page(new Page<>(pageNum, pageSize));
        return new PageResult<>(model.getCurrent(), model.getSize(), model.getTotal(), model.getRecords());
    }

    @Override
    public SysUser getUserByUserName(String userName) {
        return baseMapper.selectOne(this.lambdaQuery().eq(SysUser::getUserName, userName).getWrapper());
    }

}
