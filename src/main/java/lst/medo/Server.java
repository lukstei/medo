package lst.medo;

import lst.medo.config.DatabaseEnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
