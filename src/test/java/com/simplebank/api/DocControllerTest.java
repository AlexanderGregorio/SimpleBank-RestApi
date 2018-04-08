package com.simplebank.api;

import com.simplebank.model.ApiErrorMessage;
import com.simplebank.model.Doc;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.simplebank.constant.ErrorMessages.DOC_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DocControllerTest extends AbstractTest {

    private Doc getValidDoc() throws Exception {
        Doc doc = entityFactory.getRandomObject(Doc.class);

        MockHttpServletResponse response = post(uriBuilder.docUri(), doc);

        return parser.asObject(response.getContentAsString(), Doc.class);
    }

    @Test
    public void shouldReturnOk_whenPost() throws Exception{
        // Given
        Doc doc = entityFactory.getRandomObject(Doc.class);

        // When
        MockHttpServletResponse response = post(uriBuilder.docUri(), doc);

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
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
        MockHttpServletResponse response = get(uriBuilder.docElementUri(doc.getId()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnNotFound_whenGet_ifIdDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = get(uriBuilder.docElementUri(Long.MAX_VALUE));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(DOC_NOT_FOUND, Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.docElementUri(Long.MAX_VALUE), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenPut() throws Exception{
        // Given
        Doc doc = getValidDoc();

        // When
        doc.setToUser("Sara");
        MockHttpServletResponse response = put(uriBuilder.docUri(), doc);

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        Doc docReturned = parser.asObject(response.getContentAsString(), Doc.class);
        assertEquals(doc, docReturned);
    }

    @Test
    public void shouldReturnNotFound_whenPut_ifIdDoesntExist() throws Exception{
        // Given
        Doc doc = entityFactory.getRandomObject(Doc.class);
        doc.setId(Long.MAX_VALUE);
        doc.setToUser("Sara");

        // When
        MockHttpServletResponse response = put(uriBuilder.docUri(), doc);

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(DOC_NOT_FOUND, Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.docUri(), errorMessage.getPath());
    }

    @Test
    public void shouldReturnOk_whenDelete() throws Exception{
        // Given
        Doc doc = getValidDoc();

        // When
        MockHttpServletResponse response = delete(uriBuilder.docElementUri(doc.getId()));

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void shouldReturnNotFound_whenDelete_ifIdDoesntExist() throws Exception{
        // When
        MockHttpServletResponse response = delete(uriBuilder.docElementUri(Long.MAX_VALUE));

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        ApiErrorMessage errorMessage = parser.asObject(response.getContentAsString(), ApiErrorMessage.class);
        assertEquals(String.format(DOC_NOT_FOUND, Long.MAX_VALUE), errorMessage.getMessage());
        assertNotNull(errorMessage.getTime());
        assertEquals(uriBuilder.docElementUri(Long.MAX_VALUE), errorMessage.getPath());
    }
}
