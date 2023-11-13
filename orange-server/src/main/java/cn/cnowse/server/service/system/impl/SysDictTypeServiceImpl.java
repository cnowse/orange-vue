package cn.cnowse.server.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.cnowse.server.mapper.system.SysDictTypeMapper;
import cn.cnowse.server.pojo.system.entity.SysDictType;
import cn.cnowse.server.service.system.SysDictTypeService;

@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {}
