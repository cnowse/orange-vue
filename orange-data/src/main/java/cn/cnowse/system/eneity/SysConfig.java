package cn.cnowse.system.eneity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.cnowse.annotation.Excel;
import cn.cnowse.base.BaseEntity;

/**
 * 参数配置表 sys_config
 * 
 * @author Jeong Geol
 */
@Data
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    @TableId
    private Long configId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    @Size(max = 100, message = "参数名称不能超过100个字符")
    private String configName;

    /** 参数键名 */
    @Excel(name = "参数键名")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    /** 参数键值 */
    @Excel(name = "参数键值")
    @NotBlank(message = "参数键值不能为空")
    @Size(max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("configId", getConfigId()).append("configName", getConfigName())
                .append("configKey", getConfigKey()).append("configValue", getConfigValue())
                .append("configType", getConfigType()).append("createBy", getCreateBy())
                .append("createTime", getCreateTime()).append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
    }

}
