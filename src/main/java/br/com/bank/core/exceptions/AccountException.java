package br.com.bank.core.exceptions;

import br.com.bank.core.api.ApiErrorResponse;

public class AccountException extends RuntimeException {

    private ApiErrorResponse errorResponse;

    public AccountException(String message) {
        this.errorResponse = new ApiErrorResponse<>(message);
    }

    public AccountException(ApiErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ApiErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ApiErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}
