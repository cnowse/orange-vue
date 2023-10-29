package cn.cnowse.server.convert.system;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.pojo.system.vo.UserVO;

/**
 * SysUser 实体转换
 *
 * @author Jeong Geol
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConvert {

    /**
     * SysUser to UserVO
     *
     * @param user cn.cnowse.server.pojo.system.entity.SysUser
     * @return cn.cnowse.server.pojo.system.vo.UserVO
     */
    UserVO toVO(SysUser user);

}
