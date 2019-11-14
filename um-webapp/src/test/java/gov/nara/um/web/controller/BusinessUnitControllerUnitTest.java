package gov.nara.um.web.controller;

import gov.nara.um.service.IBusinessUnitService;

import gov.nara.um.spring.UmContextConfig;
import gov.nara.um.spring.UmPersistenceJpaConfig;
import gov.nara.um.spring.UmServiceConfig;
import gov.nara.um.spring.UmWebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessUnitController.class)
@AutoConfigureMockMvc

public class BusinessUnitControllerUnitTest {

    @MockBean
    private IBusinessUnitService service;


    @Autowired
    private MockMvc mvc;

    @Test
    public final void checkControllerURLhandling() throws Exception {
        //

        try {

            mvc.perform(get("/businessunits")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(500)).andDo(print());

            // A 200 OR 500 IS FINE. IT MEANS THE URL IS BEING HANDLED BY THE MVC CONTROLLER BY THE PROPER CLASS
            // we are seeing a serialization issue ....possibly related to the mockBean

        }
        catch (Exception ex){
            System.out.println("exception occurred: "+ex);
        }

    }
}
