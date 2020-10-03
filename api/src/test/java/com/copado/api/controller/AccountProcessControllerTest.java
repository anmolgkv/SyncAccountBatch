package com.copado.api.controller;

import com.copado.api.model.ProcessRequest;
import com.copado.api.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.copado.api.controller.helper.Random.string;
import static com.copado.api.controller.helper.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountProcessController.class)
public class AccountProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountProcessController controller;

    @Test
    public void whenValidInput_thenReturns200() throws Exception {

        // Setup
        Response successResponse = new Response("success");
        when(controller.process(Mockito.any(ProcessRequest.class))).thenReturn(successResponse);
        ProcessRequest body = new ProcessRequest(string(18), string(18));


        // Exercise & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accountSync/process")
                .content(asJson(body))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(successResponse, Response.class))
                .andReturn();
    }


    @Test
    public void whenEmptyOrgId_thenReturns400() throws Exception {

        // Setup
        Response successResponse = new Response("success");
        when(controller.process(Mockito.any(ProcessRequest.class))).thenReturn(successResponse);
        ProcessRequest body = new ProcessRequest("", string(18));


        // Exercise & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accountSync/process")
                .content(asJson(body))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void whenEmptyUserId_thenReturns400() throws Exception {

        // Setup
        Response successResponse = new Response("success");
        when(controller.process(Mockito.any(ProcessRequest.class))).thenReturn(successResponse);
        ProcessRequest body = new ProcessRequest(string(18), "");


        // Exercise & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accountSync/process")
                .content(asJson(body))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    // Helper

    private String asJson(Object content) throws JsonProcessingException {
        return objectMapper.writeValueAsString(content);
    }
}