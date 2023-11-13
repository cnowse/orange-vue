package cn.cnowse.server.pojo.system.spec;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserSpec {

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 用户ID */
    private Long userId;

    /** 用户账号 */
    private String userName;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 手机号码 */
    private String phoneNumber;

    /** 创建时间开始 */
    private LocalDateTime startTime;

    /** 创建时间结束 */
    private LocalDateTime endTime;

}
