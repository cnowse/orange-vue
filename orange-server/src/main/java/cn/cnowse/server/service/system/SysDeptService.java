package cn.cnowse.server.service.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.entity.SysDept;
import cn.cnowse.server.pojo.system.spec.DeptSpec;
import cn.cnowse.server.pojo.system.vo.TreeSelectVO;

public interface SysDeptService extends IService<SysDept> {

    /**
     * 根据条件查询部门信息
     *
     * @param spec 条件
     * @return 部门信息
     * @author Jeong Geol
     */
    List<SysDept> listBySpec(DeptSpec spec);

    /**
     * 查询部门树结构信息
     *
     * @param spec 条件
     * @return 部门树信息集合
     * @author Jeong Geol
     */
    List<TreeSelectVO> listToTree(DeptSpec spec);

}
