package br.com.bank.core.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum EValidationResponse {

    VALIDATION_ERROR_GENERIC("ERR-0001", "WS-CORE-BANKING-SYSTEM", "TEC", "Generic error"),
    VALIDATION_ERROR_REQUEST_PARAMS("ERR-0002", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDACAO", "Request with invalid parameters"),

    UNAUTHORIZED("ERR-1000", null, null, "Not authorized", null);

    @JsonProperty("codigo")
    private String code;

    @JsonProperty("origem")
    private String origin;

    @JsonProperty("tipo")
    private String type;

    @JsonProperty("subtipo")
    private String subType;

    private String msg;

    @JsonProperty("detalhes")
    private String detail;

    EValidationResponse() {
    }

    EValidationResponse(String code, String origin, String type, String subType) {
        this.code = code;
        this.origin = origin;
        this.type = type;
        this.subType = subType;
    }

    EValidationResponse(String code, String origin, String type, String subType, String msg) {
        this.code = code;
        this.origin = origin;
        this.type = type;
        this.subType = subType;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getOrigin() {
        return origin;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getMsg() {
        return msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
