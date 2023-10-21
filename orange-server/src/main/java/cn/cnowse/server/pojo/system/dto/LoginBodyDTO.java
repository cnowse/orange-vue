package cn.cnowse.server.pojo.system.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 用户登录对象
 * 
 * @author Jeong Geol
 */
@Data
public class LoginBodyDTO {

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度大于等于2，小于等于20")
    private String username;

    /** 用户密码 */
    @NotBlank(message = "密码不能为空")
    @Size(min = 5, max = 20, message = "密码长度大于等于5，小于等于20")
    private String password;

    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /** 唯一标识 */
    private String uuid;

}
