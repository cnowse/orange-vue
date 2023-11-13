package cn.cnowse.server.service.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysDeptMapper;
import cn.cnowse.server.pojo.system.entity.SysDept;
import cn.cnowse.server.pojo.system.spec.DeptSpec;
import cn.cnowse.server.pojo.system.vo.TreeSelectVO;
import cn.cnowse.server.service.system.SysDeptService;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public List<SysDept> listBySpec(DeptSpec spec) {
        return this.lambdaQuery().eq(SysDept::getDelFlag, "0")
                .eq(spec.getDeptId() != null && spec.getDeptId() != 0, SysDept::getDeptId, spec.getDeptId())
                .eq(spec.getParentId() != null && spec.getParentId() != 0, SysDept::getParentId, spec.getParentId())
                .eq(StringUtils.isNotBlank(spec.getStatus()), SysDept::getStatus, spec.getStatus())
                .like(StringUtils.isNotBlank(spec.getDeptName()), SysDept::getDeptName, spec.getDeptName()).list();
    }

    @Override
    public List<TreeSelectVO> listToTree(DeptSpec spec) {
        List<SysDept> deptList = this.listBySpec(spec);
        return this.buildDeptTreeSelect(deptList);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptList 部门列表
     * @return 下拉树结构列表
     */
    private List<TreeSelectVO> buildDeptTreeSelect(List<SysDept> deptList) {
        List<SysDept> deptTree = new ArrayList<>();
        List<Long> tempList = deptList.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        for (SysDept dept : deptList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                this.recursionFn(deptList, dept);
                deptTree.add(dept);
            }
        }
        if (deptTree.isEmpty()) {
            deptTree = deptList;
        }
        return deptTree.stream().map(TreeSelectVO::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = this.getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (this.hasChild(list, tChild)) {
                this.recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<>();
        for (SysDept n : list) {
            if (n.getParentId() != null && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return !this.getChildList(list, t).isEmpty();
    }

}
