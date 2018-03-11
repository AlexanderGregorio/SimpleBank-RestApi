package com.simplebank.api;

import com.simplebank.model.ApiErrorMessage;
import com.simplebank.model.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.simplebank.constant.ErrorMessages.USER_NOT_FOUND;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UserControllerTest extends AbstractTest {

    private User getValidUser() throws Exception {
        User user = entityFactory.getRandomObject(User.class);

        MockHttpServletResponse response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(user)))
                .andReturn().getResponse();

        return parser.asObject(response.getContentAsString(), User.class);
    }

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        // Given
        User user = entityFactory.getRandomObject(User.class);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(user)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(user, userReturned);
    }

    @Test
    public void shouldReturnConflic_whenPost_idAlreadyExists() throws Exception{
        // Given
        User user = getValidUser();

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(user)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.CONFLICT.value()));
    }

    @Test
    public void shouldReturnOk_whenGet() throws Exception{
        // Given
        User user = getValidUser();

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/user/" + user.getDocumentCode()))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(user, userReturned);
    }

    @Test
    public void shouldReturnNotFound_whenGet_idDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/user/" + Long.MAX_VALUE))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertThat(errorMessage.getMessage(), is(String.format(USER_NOT_FOUND, Long.MAX_VALUE)));
        assertNotNull(errorMessage.getTime());
        assertThat(errorMessage.getPath(), is("/api/user/" + Long.MAX_VALUE));
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        // Given
        User user = getValidUser();

        // When
        user.setName("Samantha");
        MockHttpServletResponse response = mockMvc.perform(put("/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(user)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(user, userReturned);
    }

    @Test
    public void shouldReturnNotFound_whenPut_idDoesntExist() throws Exception{
        // Given
        User user = entityFactory.getRandomObject(User.class);
        user.setDocumentCode(String.valueOf(Long.MAX_VALUE));

        // When
        user.setName("Sara");
        MockHttpServletResponse response = mockMvc.perform(put("/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(parser.asString(user)))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertThat(errorMessage.getMessage(), is(String.format(USER_NOT_FOUND, Long.MAX_VALUE)));
        assertNotNull(errorMessage.getTime());
        assertThat(errorMessage.getPath(), is("/api/user"));
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        // Given
        User user = getValidUser();

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/user/" + user.getDocumentCode()))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnNotFound_whenDelete_idDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/user/" + Long.MAX_VALUE))
                .andDo(print())
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertThat(errorMessage.getMessage(), is(String.format(USER_NOT_FOUND, Long.MAX_VALUE)));
        assertNotNull(errorMessage.getTime());
        assertThat(errorMessage.getPath(), is("/api/user/" + Long.MAX_VALUE));
    }
}
