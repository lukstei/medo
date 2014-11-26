package lst.medo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Server {
    public static void main(String[] args) {
        if (args.length > 0 && (args[0].equals("--help") || args[0].equals("-h"))) {
            System.err.println("Usage: " + Server.class.getName() + " [--url=jdbc:postgresql://ip/dbname] [--user=postgres] [--port=5432] [--password=]");
            return;
        }

        SpringApplication.run(Server.class, args);
    }
}
