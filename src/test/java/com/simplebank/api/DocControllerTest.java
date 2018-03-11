package com.simplebank.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplebank.model.Doc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(DocController.class)
@Slf4j
public abstract class DocControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(new Doc())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Ok")));
    }

    @Test
    public void shouldReturnOk_whenGet() throws Exception{
        mockMvc.perform(get("/")).andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Ok")));
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        mockMvc.perform(put("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(new Doc())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Ok")));
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        mockMvc.perform(delete("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(new Doc())))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Ok")));
    }

    private String asJsonString(Doc doc) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(doc);
        }
        catch (JsonProcessingException e) {
            log.error("There was a problem parsing the object.", e);
        }

        return null;
    }
}
