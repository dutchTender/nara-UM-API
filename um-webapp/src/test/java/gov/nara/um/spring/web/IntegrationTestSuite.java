package gov.nara.um.spring.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    BusinessUnitSpringIntegrationTest.class,
    ServiceSpringIntegrationTest.class,
    WebSpringIntegrationTest.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}