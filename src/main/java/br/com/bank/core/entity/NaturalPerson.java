package br.com.bank.core.entity;

import br.com.bank.core.enums.EPersonType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Objects;


public class NaturalPerson extends Person {

    @NotNull
    private String socialSecurityNumber;

    @NotNull
    private String fullName;

    public NaturalPerson(@NotNull String socialSecurityNumber, @NotNull String fullName) {
        this.setPersonType(EPersonType.NATURAL);
        this.socialSecurityNumber = socialSecurityNumber;
        this.fullName = fullName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NaturalPerson that = (NaturalPerson) o;
        return socialSecurityNumber.equals(that.socialSecurityNumber) &&
                fullName.equals(that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialSecurityNumber, fullName);
    }

}
