package com.simplebank.api;

import com.simplebank.model.Doc;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

public class DocControllerTest extends AbstractTest {

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        // Given
        Doc doc = docFactory.getRandomDoc();

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(log())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnOk_whenGet() throws Exception{
        // Given
        Doc doc = docFactory.getRandomDoc();

        mockMvc.perform(post("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(log());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/doc?id=" + doc.getId()))
                .andDo(log())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        // Given
        Doc doc = docFactory.getRandomDoc();

        mockMvc.perform(post("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(log());

        // When
        doc.setToUser("Sara");
        MockHttpServletResponse response = mockMvc.perform(put("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(log())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        // Given
        Doc doc = docFactory.getRandomDoc();

        mockMvc.perform(post("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(log());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/doc?id=" + doc.getId()))
                .andDo(log())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }
}
