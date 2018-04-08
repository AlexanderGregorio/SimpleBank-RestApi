package com.simplebank.api;

import com.simplebank.model.Account;
import com.simplebank.model.ApiErrorMessage;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.math.BigDecimal;

import static com.simplebank.constant.ErrorMessages.ACCOUNT_ALREADY_EXISTS;
import static com.simplebank.constant.ErrorMessages.ACCOUNT_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountControllerTest extends AbstractTest {

    private Account getValidAccount() throws Exception {
        Account account = entityFactory.getRandomObject(Account.class);
        post(uriBuilder.userUri(), account.getOwner());

        MockHttpServletResponse response = post(uriBuilder.accountUri(), account);

        return parser.asObject(response.getContentAsString(), Account.class);
    }

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        // Given
        Account account = entityFactory.getRandomObject(Account.class);
        post(uriBuilder.userUri(), account.getOwner());

        // When
        MockHttpServletResponse response = post(uriBuilder.accountUri(), account);

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Account accountReturned = parser.asObject(response.getContentAsString(), Account.class);
        assertNotNull(accountReturned.getAccountCode());
        assertEquals(account.getBankCode(), accountReturned.getBankCode());
        assertEquals(account.getBalance(), accountReturned.getBalance());
        assertEquals(account.getOwner(), accountReturned.getOwner());
        assertEquals(account.getPassword(), accountReturned.getPassword());
    }
    @Test
    public void shouldReturnConflict_whenPost_ifAccountAlreadyRegistered() throws Exception{
        // Given
        Account account = entityFactory.getRandomObject(Account.class);
        post(uriBuilder.userUri(), account.getOwner());

        // When
        MockHttpServletResponse response = post(uriBuilder.accountUri(), account);
        account = parser.asObject(response.getContentAsString(), Account.class);
        response = post(uriBuilder.accountUri(), account);

        // Then
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(ACCOUNT_ALREADY_EXISTS, account.getBankCode(), account.getAccountCode()), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.accountUri(), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenGet() throws Exception{
        // Given
        Account account = getValidAccount();

        // When
        MockHttpServletResponse response = get(uriBuilder.accountElementUri(account.getBankCode(), account.getAccountCode()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        Account accountReturned = parser.asObject(response.getContentAsString(), Account.class);
        assertEquals(account, accountReturned);
    }

    @Test
    public void shouldReturnNotFound_whenGet_ifIdDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = get(uriBuilder.accountElementUri(Long.MAX_VALUE, Long.MAX_VALUE));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(ACCOUNT_NOT_FOUND, Long.MAX_VALUE, Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.accountElementUri(Long.MAX_VALUE, Long.MAX_VALUE), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        // Given
        Account account = getValidAccount();

        // When
        account.setBalance(new BigDecimal("25"));
        MockHttpServletResponse response = put(uriBuilder.accountUri(), account);

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        Account accountReturned = parser.asObject(response.getContentAsString(), Account.class);
        assertEquals(account, accountReturned);
    }

    @Test
    public void shouldReturnNotFound_whenPut_ifIdDoesntExist() throws Exception{
        // Given
        Account account = entityFactory.getRandomObject(Account.class);
        post(uriBuilder.userUri(), account.getOwner());
        account.setAccountCode(Long.MAX_VALUE);
        account.setBalance(new BigDecimal("25"));

        // When
        MockHttpServletResponse response = put(uriBuilder.accountUri(), account);

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(ACCOUNT_NOT_FOUND, account.getBankCode(), Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.accountUri(), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        // Given
        Account account = getValidAccount();

        // When
        MockHttpServletResponse response = delete(uriBuilder.accountElementUri(account.getBankCode(), account.getAccountCode()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void shouldReturnNotFound_whenDelete_ifIdDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = delete(uriBuilder.accountElementUri(Long.MAX_VALUE, Long.MAX_VALUE));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(ACCOUNT_NOT_FOUND, Long.MAX_VALUE, Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.accountElementUri(Long.MAX_VALUE, Long.MAX_VALUE), errorMessage.getPath());
    }

    @Test
    public void shouldReturnNotFound_whenGet_ifUserIsDeleted() throws Exception{
        // Given
        Account account = entityFactory.getRandomObject(Account.class);
        post(uriBuilder.userUri(), account.getOwner());
        account = parser.asObject(post(uriBuilder.accountUri(), account).getContentAsString(), Account.class);

        // When
        delete(uriBuilder.userElementUri(account.getOwner().getDocumentCode()));
        MockHttpServletResponse response = get(uriBuilder.accountElementUri(account.getBankCode(), account.getAccountCode()));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(ACCOUNT_NOT_FOUND, account.getBankCode(), account.getAccountCode()), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.accountElementUri(account.getBankCode(), account.getAccountCode()), errorMessage.getPath());
    }
}
