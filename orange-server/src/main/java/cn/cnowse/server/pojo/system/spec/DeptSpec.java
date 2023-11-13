package cn.cnowse.server.pojo.system.spec;

import lombok.Data;

@Data
public class DeptSpec {

    /** 部门ID */
    private Long deptId;

    /** 父部门ID */
    private Long parentId;

    /** 部门名称 */
    private String deptName;

    /** 部门状态:0正常,1停用 */
    private String status;

}
