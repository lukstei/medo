package lst.medo;

import lst.medo.config.Config;
import lst.medo.config.WebConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
public class Server {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Server.class)
                .run(args);
    }
}
