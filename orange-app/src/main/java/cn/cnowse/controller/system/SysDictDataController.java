package cn.cnowse.controller.system;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.server.pojo.system.entity.SysDictData;
import cn.cnowse.server.service.system.SysDictDataService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    private final SysDictDataService dictDataService;

    @GetMapping(value = "/type/{dictType}")
    public List<SysDictData> dictType(@PathVariable String dictType) {
        return dictDataService.listByType(dictType);
    }

}
