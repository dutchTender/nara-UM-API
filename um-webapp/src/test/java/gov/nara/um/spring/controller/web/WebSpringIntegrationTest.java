package gov.nara.um.spring.controller.web;

import gov.nara.um.spring.web.UmContextConfig;
import gov.nara.um.spring.web.UmPersistenceJpaConfig;
import gov.nara.um.spring.web.UmServiceConfig;
import gov.nara.um.spring.web.UmWebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmPersistenceJpaConfig.class, UmContextConfig.class, UmServiceConfig.class, UmWebConfig.class })
@WebAppConfiguration
public class WebSpringIntegrationTest {

    @Test
    public final void whenContextIsBootstrapped_thenOk() {
        //
    }

}
