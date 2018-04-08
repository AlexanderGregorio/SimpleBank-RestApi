package com.simplebank.api;

import com.simplebank.model.Account;
import com.simplebank.model.ApiErrorMessage;
import com.simplebank.model.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.simplebank.constant.ErrorMessages.USER_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserControllerTest extends AbstractTest {

    private User getValidUser() throws Exception {
        User user = entityFactory.getRandomObject(User.class);

        MockHttpServletResponse response = post(uriBuilder.userUri(), user);

        return parser.asObject(response.getContentAsString(), User.class);
    }

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        // Given
        User user = entityFactory.getRandomObject(User.class);

        // When
        MockHttpServletResponse response = post(uriBuilder.userUri(), user);

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(user, userReturned);
    }

    @Test
    public void shouldReturnConflic_whenPost_ifIdAlreadyExists() throws Exception{
        // Given
        User user = getValidUser();

        // When
        MockHttpServletResponse response = post(uriBuilder.userUri(), user);

        // Then
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }

    @Test
    public void shouldReturnOk_whenGet() throws Exception{
        // Given
        User user = getValidUser();

        // When
        MockHttpServletResponse response = get(uriBuilder.userElementUri(user.getDocumentCode()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(user, userReturned);
    }

    @Test
    public void shouldReturnNotFound_whenGet_ifIdDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = get(uriBuilder.userElementUri("someRandomId"));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(USER_NOT_FOUND, "someRandomId"), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.userElementUri("someRandomId"), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        // Given
        User user = getValidUser();

        // When
        user.setName("Samantha");
        MockHttpServletResponse response = put(uriBuilder.userUri(), user);

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(user, userReturned);
    }

    @Test
    public void shouldReturnNotFound_whenPut_ifIdDoesntExist() throws Exception{
        // Given
        User user = entityFactory.getRandomObject(User.class);
        user.setDocumentCode(String.valueOf(Long.MAX_VALUE));

        // When
        user.setName("Sara");
        MockHttpServletResponse response = put(uriBuilder.userUri(), user);

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(USER_NOT_FOUND, Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.userUri(), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        // Given
        User user = getValidUser();

        // When
        MockHttpServletResponse response = delete(uriBuilder.userElementUri(user.getDocumentCode()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void shouldReturnNotFound_whenDelete_ifIdDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = delete(uriBuilder.userElementUri("someRandomId"));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(USER_NOT_FOUND, "someRandomId"), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.userElementUri("someRandomId"), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenGet_ifAccountIsDeleted() throws Exception{
        // Given
        Account account = entityFactory.getRandomObject(Account.class);
        post(uriBuilder.userUri(), account.getOwner());
        account = parser.asObject(post(uriBuilder.accountUri(), account).getContentAsString(), Account.class);

        // When
        delete(uriBuilder.accountElementUri(account.getBankCode(), account.getAccountCode()));
        MockHttpServletResponse response = get(uriBuilder.userElementUri(account.getOwner().getDocumentCode()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        User userReturned = parser.asObject(response.getContentAsString(), User.class);
        assertEquals(account.getOwner(), userReturned);
    }
}
