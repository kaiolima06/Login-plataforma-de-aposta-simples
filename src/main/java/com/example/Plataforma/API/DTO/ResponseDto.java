package com.example.Plataforma.API.DTO;

public class ResponseDto {

    private String successMessage;
    private String errorMessage;
    private String numAposta;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getNumAposta() {
        return numAposta;
    }

    public void setNumAposta(String numAposta) {
        this.numAposta = numAposta;
    }
}
