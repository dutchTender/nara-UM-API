package gov.nara.um.spring.controller.web;

import gov.nara.um.persistence.dao.IBusinessUnitDao;
import gov.nara.um.persistence.dao.IUserJpaDao;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.persistence.model.User;
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
public class UserBusinessUnitSpringIntegrationTest {
// we are going to modify this to do a dataJPA test for integration testing


    @Autowired
    private IUserJpaDao userJpaDao;
    @Autowired
    private TestEntityManager entityManager;



    // tests jpa add and jpa find one
    @Test
    public final void whenFindByName_thenReturnBusinessUnit() {
        //given
        User user = new User();
        user.setName("li");
        user.setUser_type("NARA");
        entityManager.persist(user);
        entityManager.flush();

        //when
        User user1 = userJpaDao.findByName(user.getName());

        // verify that we can find the entity that was just added
        assertThat(user1.getName()).isEqualTo(user.getName());
    }



    // test jpa delete
    // tests jpa add and jpa find one
    @Test
    public final void whenFindByName_thenReturnBusinessUnit_then_verifyDelete() {
        //given
        User user = new User();
        user.setName("li");
        user.setUser_type("NARA");
        entityManager.persist(user);
        entityManager.flush();

        //when
        User user1 = userJpaDao.findByName(user.getName());
        entityManager.remove(user1);
        entityManager.flush();

        User user2 = userJpaDao.findByName(user.getName());

        // verify that we can find the entity that was just added
        assertThat(user2).isEqualTo(null);
    }





}
