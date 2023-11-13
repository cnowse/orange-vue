package cn.cnowse.controller.system;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.base.PageResult;
import cn.cnowse.server.pojo.system.entity.SysUser;
import cn.cnowse.server.pojo.system.spec.DeptSpec;
import cn.cnowse.server.pojo.system.spec.UserSpec;
import cn.cnowse.server.pojo.system.vo.TreeSelectVO;
import cn.cnowse.server.service.system.SysDeptService;
import cn.cnowse.server.service.system.SysUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class SysUserController {

    private final SysDeptService deptService;
    private final SysUserService userService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public PageResult<SysUser> list(@RequestParam Integer pageNum, @RequestParam Integer pageSize, UserSpec spec) {
        return userService.listBySpec(pageNum, pageSize, spec);
    }

    /**
     * 获取部门树列表
     */
    @GetMapping("/deptTree")
    public List<TreeSelectVO> deptTree(DeptSpec spec) {
        return deptService.listToTree(spec);
    }

}
