package com.tst.mobileappws.ui.model.response;


public enum ErrorMessage {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALLREDY_EXISTS("Record alredy exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record is provided id is not found"),
    AUTHENTICATION_FAIELD("Authentication field"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

    private String errorMessages;

    ErrorMessage(String errorMessages){
        this.errorMessages=errorMessages;
    }

    public String getErrorMessages() {
        return errorMessages;
    }



    //Throw an Exception is not best way to  use best way is to create our custome exception of each senario
}
