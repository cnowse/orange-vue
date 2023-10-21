package cn.cnowse.server.pojo.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录返回 token
 *
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    private String token;

}
