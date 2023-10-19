package cn.cnowse.common;

import lombok.Data;

/**
 * 验证码数据
 *
 * @author Jeong Geol
 */
@Data
public class CaptchaVO {

    private Boolean captchaEnabled;
    private String uuid;
    private String img;

}
