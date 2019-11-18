package gov.nara.um.spring.controller.web;

import gov.nara.um.persistence.dao.IBusinessUnitDao;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.spring.UmPersistenceJpaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {UmPersistenceJpaConfig.class})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BusinessUnitSpringIntegrationTest {
// we are going to modify this to do a dataJPA test for integration testing


    @Autowired
    private IBusinessUnitDao businessUnitDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public final void whenFindByName_thenReturnBusinessUnit() {
        //given
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("xxxx");
        businessUnit.setLdapName("xxxxxxxxx");
        businessUnit.setOrg_code("apex");
        entityManager.persist(businessUnit);
        entityManager.flush();

        //when
        BusinessUnit businessUnit1 = businessUnitDao.findByName(businessUnit.getName());

        // verify that we can find the entity that was just added
        assertThat(businessUnit1.getName()).isEqualTo(businessUnit.getName());
    }




}
