package cn.cnowse.server.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysOperateLogMapper;
import cn.cnowse.server.service.system.SysOperateLogService;
import cn.cnowse.server.pojo.system.eneity.SysOperateLog;

@Service
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLog>
        implements SysOperateLogService {}
