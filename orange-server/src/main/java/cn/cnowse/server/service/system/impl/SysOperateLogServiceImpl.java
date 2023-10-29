package cn.cnowse.server.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysOperateLogMapper;
import cn.cnowse.server.pojo.system.entity.SysOperateLog;
import cn.cnowse.server.service.system.SysOperateLogService;

@Service
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLog>
        implements SysOperateLogService {}
