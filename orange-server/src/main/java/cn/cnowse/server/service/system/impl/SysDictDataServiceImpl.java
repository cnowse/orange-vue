package cn.cnowse.server.service.system.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;

import cn.cnowse.constant.CacheConstants;
import cn.cnowse.server.mapper.system.SysDictDataMapper;
import cn.cnowse.server.pojo.system.entity.SysDictData;
import cn.cnowse.server.service.system.SysDictDataService;
import cn.cnowse.util.redis.RedisHelper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final RedisHelper redis;

    @Override
    public List<SysDictData> listByType(String dictType) {
        List<SysDictData> dictDataList =
                redis.valueGet(CacheConstants.SYS_DICT_KEY + dictType, new TypeReference<List<SysDictData>>() {});
        if (dictDataList != null) {
            return dictDataList;
        }
        List<SysDictData> dictDataByType = this.lambdaQuery().eq(SysDictData::getStatus, "0")
                .eq(SysDictData::getDictType, dictType).orderByAsc(SysDictData::getDictSort).list();
        if (dictDataByType != null) {
            redis.valueSet(CacheConstants.SYS_DICT_KEY + dictType, dictDataByType);
            return dictDataByType;
        }
        return Collections.emptyList();
    }

}
