package gov.nara.um.spring.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nara.um.persistence.model.BusinessUnit;
import gov.nara.um.service.IBusinessUnitService;

import gov.nara.um.spring.controller.BusinessUnitController;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BusinessUnitController.class)
@AutoConfigureMockMvc

public class BusinessUnitControllerUnitTest {

    @MockBean
    private IBusinessUnitService service;


    @Autowired
    private MockMvc mvc;



    // List all unit test
    @Test
    public final void check_Business_unit_controller_ListAll_200_status_OK() throws Exception {

        try {
            mvc.perform(get("/businessunits")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200)).andDo(print());
                    // A 200 is needed to verify that th eurl handling works

        }
        catch (Exception ex){
            System.out.println("exception occurred: "+ex);
        }
    }

    //list 1 unit test
    @Test
    public final void check_Business_unit_controller_ListOne_200_status_OK() throws Exception {

        try {
            mvc.perform(get("/businessunits/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(200)).andDo(print());
                    // A 200 is needed to verify that the url handling works
        }
        catch (Exception ex){
            System.out.println("exception occurred: "+ex);
        }
    }


    // add 1 unit test
    @Test
    public final void check_Business_unit_controller_AddOne_201_status_OK() throws Exception {

        BusinessUnit test_unit = new BusinessUnit();
        test_unit.setName("apex");
        test_unit.setLdapName("grant thornton");
        test_unit.setOrg_code("NARA a2");

        ObjectMapper objectMapper = new ObjectMapper();
        String json_payLoad = objectMapper.writeValueAsString(test_unit);
        try {
            mvc.perform(post("/businessunits/")
                    .contentType(MediaType.APPLICATION_JSON).content(json_payLoad))
                    .andExpect(status().is(201)).andDo(print());
            // A 201 is needed to verify post request that the url handling works

        }
        catch (Exception ex){
            System.out.println("exception occurred: "+ex);
        }
    }

    // update 1 unit test
    @Test
    public final void check_Business_unit_controller_updateOne_201_status_OK() throws Exception {

        BusinessUnit test_unit = new BusinessUnit();
        test_unit.setName("apex");
        test_unit.setLdapName("grant thornton");
        test_unit.setOrg_code("NARA a2");

        ObjectMapper objectMapper = new ObjectMapper();
        String json_payLoad = objectMapper.writeValueAsString(test_unit);
        try {
            mvc.perform(put("/businessunits/2")
                    .contentType(MediaType.APPLICATION_JSON).content(json_payLoad))
                    .andExpect(status().is(201)).andDo(print());
            // A 201 is needed to verify post request that the url handling works

        }
        catch (Exception ex){
            System.out.println("exception occurred: "+ex);
        }
    }


    // delete unit test
    @Test
    public final void check_Business_unit_controller_deleteOne_204_status_OK() throws Exception {

        try {
            mvc.perform(delete("/businessunits/2")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(204)).andDo(print());
                    // A 202 is needed to verify delete request that the url handling works

        }
        catch (Exception ex){
            System.out.println("exception occurred: "+ex);
        }
    }


}
