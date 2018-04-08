package com.simplebank.constant;

public final class ErrorMessages {

    public static final String DOC_NOT_FOUND = "There is no Doc for the Id: %d";
    public static final String USER_NOT_FOUND = "There is no User for the Document: %s";
    public static final String ACCOUNT_NOT_FOUND = "There is no Account in the bank %s with the Code: %s";
    public static final String USER_ALREADY_EXISTS = "There is already an User for the Document: %s";
    public static final String ACCOUNT_ALREADY_EXISTS = "There is already an Account in the bank %s with the Code: %s";

    private ErrorMessages() {}

}
