package cn.cnowse.controller.monitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cnowse.server.pojo.monitor.server.Server;

/**
 * 服务器监控
 *
 * @author Jeong Geol
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @GetMapping
    public Server getInfo() {
        Server server = new Server();
        server.copyTo();
        return server;
    }

}
