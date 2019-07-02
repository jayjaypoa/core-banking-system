package br.com.bank.core.entity;

import br.com.bank.core.enums.EPersonType;

import javax.validation.constraints.NotNull;
import java.util.Objects;


public class CompanyPerson extends Person {

    @NotNull
    private String employerIdentificationNumber;

    @NotNull
    private String name;

    public CompanyPerson(@NotNull String employerIdentificationNumber, @NotNull String name) {
        this.setPersonType(EPersonType.COMPANY);
        this.employerIdentificationNumber = employerIdentificationNumber;
        this.name = name;
    }

    public String getEmployerIdentificationNumber() {
        return employerIdentificationNumber;
    }

    public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
        this.employerIdentificationNumber = employerIdentificationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyPerson that = (CompanyPerson) o;
        return employerIdentificationNumber.equals(that.employerIdentificationNumber) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employerIdentificationNumber, name);
    }

}
