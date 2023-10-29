package cn.cnowse.server.pojo.system.vo;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录用户信息，有权限相关信息
 *
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfoVO {

    /** 用户基本信息 */
    private UserVO user;

    /** 用户角色信息 */
    private Set<String> roles;

    /** 用户权限信息 */
    private Set<String> permissions;

}
