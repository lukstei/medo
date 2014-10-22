package lst.medo;

import lst.medo.config.Config;
import lst.medo.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
