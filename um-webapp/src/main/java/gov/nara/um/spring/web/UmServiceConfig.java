package gov.nara.um.spring.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "gov.nara.um.service" })
public class UmServiceConfig {

    public UmServiceConfig() {
        super();
    }

    // beans

}
