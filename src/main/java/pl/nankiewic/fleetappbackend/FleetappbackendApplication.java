package pl.nankiewic.fleetappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FleetappbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetappbackendApplication.class, args);
    }

}
