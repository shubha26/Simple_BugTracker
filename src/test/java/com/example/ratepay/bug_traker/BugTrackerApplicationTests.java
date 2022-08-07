package com.example.ratepay.bug_traker;

import com.example.ratepay.bug_traker.controller.BugController;
import com.example.ratepay.bug_traker.controller.BugRequest;
import com.example.ratepay.bug_traker.model.Bug;
import com.example.ratepay.bug_traker.model.Status;
import com.example.ratepay.bug_traker.model.User;
import com.example.ratepay.bug_traker.repository.BugRepository;
import com.example.ratepay.bug_traker.repository.UserRepository;
import com.example.ratepay.bug_traker.service.BugService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = BugTrackerApplication.class)
@AutoConfigureMockMvc
class BugTrackerApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    BugRepository bugRepositoryMock;

    @MockBean
    BugService bugService;

    @Autowired
    UserRepository userRepository;

    @Test
    void addBug() throws Exception {

        BugRequest bugRequest = new BugRequest("Sample Test", "Sample Test Bug", "Shubham");
        when(bugService.createUpdateBug(Mockito.any())).thenReturn(true);

        String uri = "/ratepay/tracker/bugs/";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).content(mapToJson(bugRequest))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Test
    void getBug() throws Exception {
        User user = userRepository.findByName("Shubham").get();

        Bug test = new Bug(1L, "Sample Test", Status.IN_PROGRESS, user, "Sample Test Bug", LocalDateTime.now(), LocalDateTime.now());
        List<Bug> bugList = Collections.singletonList(test);

        String uri = "/ratepay/tracker/bugs/Shubham";
        when(bugRepositoryMock.findByUser(Mockito.any())).thenReturn(Optional.of(bugList));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void updateBug() throws Exception {

        BugRequest bugRequest = new BugRequest("Sample Test", "Sample Test Bug", "Shubham");
        when(bugService.createUpdateBug(Mockito.any())).thenReturn(true);

        String uri = "/ratepay/tracker/bugs/";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).content(mapToJson(bugRequest))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Before
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Before
    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
    @Before
    public void initMocks() {
        //MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
    }

}

