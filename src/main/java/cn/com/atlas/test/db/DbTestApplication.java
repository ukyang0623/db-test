package cn.com.atlas.test.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = {"cn.com.atlas.test.db"})
public class DbTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbTestApplication.class, args);
    }
}
