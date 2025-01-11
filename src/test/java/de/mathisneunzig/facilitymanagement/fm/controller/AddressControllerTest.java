package de.mathisneunzig.facilitymanagement.fm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.mathisneunzig.facilitymanagement.fm.entity.Address;
import de.mathisneunzig.facilitymanagement.fm.repo.AddressRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	AddressRepository repo;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Address> jt;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
	}

    @BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    Address getAddress() {
    	Address a = new Address();
    	a.setId(uuid);
    	return a;
    }
    
    Optional<Address> getOptionalAddress() {
    	Address a = getAddress();
    	return Optional.of(a);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Address>());
        mvc.perform(get("/addresses"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalAddress());
        MockHttpServletResponse response = mvc.perform(get("/addresses/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getAddress()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/addresses/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPost() throws Exception{
        mvc.perform(
            post("/addresses").contentType(MediaType.APPLICATION_JSON)
            	.content(jt.write(getAddress()).getJson()))
        		.andExpect(status().isCreated());
    }

    @Test
    void testDelete() throws Exception{
    	when(repo.findById(uuid)).thenReturn(getOptionalAddress());
        mvc.perform(
            delete("/addresses/"+uuid))
        		.andExpect(status().isOk());
    }

    @Test
    void testDeleteException() throws Exception{
        mvc.perform(
            delete("/addresses/"+uuid))
        		.andExpect(status().isNotFound());
    }

}
