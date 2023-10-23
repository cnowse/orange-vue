package cn.cnowse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.cnowse.server.mapper")
public class OrangeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrangeAppApplication.class, args);
    }

}
