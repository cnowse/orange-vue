package cn.cnowse.controller.system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.server.pojo.system.dto.LoginBodyDTO;
import cn.cnowse.server.service.system.SysLoginService;
import lombok.RequiredArgsConstructor;

/**
 * 登录验证
 *
 * @author Jeong Geol
 */
@RestController
@RequiredArgsConstructor
public class SysLoginController {

    private final SysLoginService loginService;

    @PostMapping("/login")
    public void login(@RequestBody LoginBodyDTO dto) {
        loginService.login(dto);
    }
}
