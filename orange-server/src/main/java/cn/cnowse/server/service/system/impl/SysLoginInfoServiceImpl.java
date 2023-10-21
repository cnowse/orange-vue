package cn.cnowse.server.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysLoginInfoMapper;
import cn.cnowse.server.service.system.SysLoginInfoService;
import cn.cnowse.server.pojo.system.eneity.SysLoginInfo;

@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo>
        implements SysLoginInfoService {}
