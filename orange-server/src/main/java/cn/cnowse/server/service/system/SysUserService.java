package cn.cnowse.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.base.PageResult;
import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.pojo.system.spec.UserSpec;

public interface SysUserService extends IService<SysUser> {

    /**
     * 根据条件分页查询用户信息
     *
     * @param pageNum 当前页
     * @param pageSize 当前页数据量
     * @param spec 条件
     * @return 分页数据
     * @author Jeong Geol
     */
    PageResult<SysUser> listBySpec(Integer pageNum, Integer pageSize, UserSpec spec);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser getUserByUserName(String userName);

}
