package org.sau.devopsv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.sau.devopsv2.repository")  // Ensure this points to your repository package

public class DevopsV2Application {

    public static void main(String[] args) {
        SpringApplication.run(DevopsV2Application.class, args);
    }

}
