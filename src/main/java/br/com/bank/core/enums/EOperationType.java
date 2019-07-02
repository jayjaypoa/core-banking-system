package br.com.bank.core.enums;

public enum EOperationType {

    DEPOSIT("D"),
    WITHDRAW("W");

    private String operationType;

    EOperationType(String operationType){
        this.operationType = operationType;
    }

    public String getOperationType(){
        return this.operationType;
    }

}
