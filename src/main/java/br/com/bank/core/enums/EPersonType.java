package br.com.bank.core.enums;

public enum EPersonType {

    NATURAL("N"),
    COMPANY("C");

    private String personType;

    EPersonType(String personType){
        this.personType = personType;
    }

    public String getPersonType(){
        return this.personType;
    }

}
