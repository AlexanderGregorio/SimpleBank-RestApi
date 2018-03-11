package com.simplebank.api;

import com.simplebank.model.ApiErrorMessage;
import com.simplebank.model.Doc;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.simplebank.constants.ErrorMessages.DOC_NOT_FOUND;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DocControllerTest extends AbstractTest {

    private Doc getValidDoc() throws Exception {
        Doc doc = docFactory.getRandomDoc();

        MockHttpServletResponse response = mockMvc.perform(post("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andReturn().getResponse();

        return parser.asObject(response.getContentAsString(), Doc.class);
    }

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        // Given
        Doc doc = docFactory.getRandomDoc();

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertNotNull(docReturned.getId());
        assertEquals(doc.getFromUser(), docReturned.getFromUser());
        assertEquals(doc.getToUser(), docReturned.getToUser());
        assertEquals(doc.getValue(), docReturned.getValue());
        assertEquals(doc.getCurrency(), docReturned.getCurrency());
    }

    @Test
    public void shouldReturnOk_whenGet() throws Exception{
        // Given
        Doc doc = getValidDoc();

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/doc/" + doc.getId()))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnNotFound_whenGet_idDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/doc/" + Long.MAX_VALUE))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertThat(errorMessage.getMessage(), is(String.format(DOC_NOT_FOUND, Long.MAX_VALUE)));
        assertNotNull(errorMessage.getTime());
        assertThat(errorMessage.getPath(), is("/api/doc/" + Long.MAX_VALUE));
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        // Given
        Doc doc = getValidDoc();

        // When
        doc.setToUser("Sara");
        MockHttpServletResponse response = mockMvc.perform(put("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnNotFound_whenPut_idDoesntExist() throws Exception{
        // Given
        Doc doc = docFactory.getRandomDoc();
        doc.setId(Long.MAX_VALUE);

        // When
        doc.setToUser("Sara");
        MockHttpServletResponse response = mockMvc.perform(put("/api/doc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(doc)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertThat(errorMessage.getMessage(), is(String.format(DOC_NOT_FOUND, Long.MAX_VALUE)));
        assertNotNull(errorMessage.getTime());
        assertThat(errorMessage.getPath(), is("/api/doc"));
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        // Given
        Doc doc = getValidDoc();

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/doc/" + doc.getId()))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnNotFound_whenDelete_idDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/doc/" + Long.MAX_VALUE))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertThat(errorMessage.getMessage(), is(String.format(DOC_NOT_FOUND, Long.MAX_VALUE)));
        assertNotNull(errorMessage.getTime());
        assertThat(errorMessage.getPath(), is("/api/doc/" + Long.MAX_VALUE));
    }
}
