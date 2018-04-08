package com.simplebank.test;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class UriBuilder {

    private static final String DOC_PATH = "/api/doc";
    private static final String DOC_ELEMENT_PATH = "/{docId}";

    private static final String USER_PATH = "/api/user";
    private static final String USER_ELEMENT_PATH = "/{documentId}";

    private static final String ACCOUNT_PATH = "/api/account";
    private static final String ACCOUNT_ELEMENT_PATH = "/{bankCode}/{accountCode}";

    private String buildPath(String baseUri, String path, Object ... uriVariables) {
        return UriComponentsBuilder
                .fromUriString(baseUri)
                .path(path)
                .buildAndExpand(uriVariables)
                .getPath();
    }

    public String docUri(){
        return DOC_PATH;
    }

    public String docElementUri(Long docId){
        return buildPath(DOC_PATH, DOC_ELEMENT_PATH, String.valueOf(docId));
    }

    public String userUri(){
        return USER_PATH;
    }

    public String userElementUri(String documentId){
        return buildPath(USER_PATH, USER_ELEMENT_PATH, documentId);
    }

    public String accountUri(){
        return ACCOUNT_PATH;
    }

    public String accountElementUri(Long bankCode, Long accountCode){
        return buildPath(ACCOUNT_PATH, ACCOUNT_ELEMENT_PATH, bankCode, accountCode);
    }

}
