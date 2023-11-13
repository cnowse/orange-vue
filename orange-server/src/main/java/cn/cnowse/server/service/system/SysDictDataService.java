package cn.cnowse.server.service.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.cnowse.server.pojo.system.entity.SysDictData;

public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字段类型
     * @return 字段数据信息
     * @author Jeong Geol
     */
    List<SysDictData> listByType(String dictType);

}
