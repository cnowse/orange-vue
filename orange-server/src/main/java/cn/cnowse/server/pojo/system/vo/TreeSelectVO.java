package cn.cnowse.server.pojo.system.vo;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.cnowse.server.pojo.system.entity.SysDept;
import cn.cnowse.server.pojo.system.entity.SysMenu;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TreeSelect 树结构实体类
 * 
 * @author Jeong Geol
 */
@Data
@NoArgsConstructor
public class TreeSelectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVO> children;

    public TreeSelectVO(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelectVO::new).collect(Collectors.toList());
    }

    public TreeSelectVO(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelectVO::new).collect(Collectors.toList());
    }

}
