package dit.crush.ditcrush;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("dit.crush.ditcrush.mapper")
public class DitCrushApplication {

    public static void main(String[] args) {
        SpringApplication.run(DitCrushApplication.class, args);
    }

}
